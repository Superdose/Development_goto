package com.axontest.own.ordersservice2.saga;

import com.axontest.own.core.commands.CancelProductReservationCommand;
import com.axontest.own.core.commands.ProcessPaymentCommand;
import com.axontest.own.core.commands.ReserveProductCommand;
import com.axontest.own.core.events.PaymentProcessedEvent;
import com.axontest.own.core.events.ProductReservationCancelledEvent;
import com.axontest.own.core.events.ProductReservedEvent;
import com.axontest.own.core.model.User;
import com.axontest.own.core.query.FetchUserPaymentDetailsQuery;
import com.axontest.own.ordersservice2.command.commands.ApproveOrderCommand;
import com.axontest.own.ordersservice2.command.commands.RejectOrderCommand;
import com.axontest.own.ordersservice2.core.event.OrderApprovedEvent;
import com.axontest.own.ordersservice2.core.event.OrderCreatedEvent;
import com.axontest.own.ordersservice2.core.event.OrderRejectedEvent;
import com.axontest.own.ordersservice2.core.model.OrderSummary;
import com.axontest.own.ordersservice2.query.FindOrderQuery;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

// SAGA will be serialized!
// @Saga annotation to mark this class as a SAGA
@Saga
public class OrderSaga {

    // Logger for logging
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

    // constant
    private static final String PAYMENT_PROCESSING_TIMEOUT_DEADLINE = "payment-processing-deadline";

    // Saga is serialized -> mark CommandGateway as transient,
    // so it doesn't get serialized
    // need to do field-injection, because OrderSaga needs a default constructor
    // we will use the CommandGateway to publish Commands to axon-server
    @Autowired
    private transient CommandGateway commandGateway;

    // same for QueryGateway
    // we will use the QueryGateway to publish queries to axon-server
    @Autowired
    private transient QueryGateway queryGateway;

    // Saga is serialized -> mark DeadLineManager as transient,
    // so it doesn't get serialized
    // DeadLineManager is used to trigger an event when an awaited event doesnt trigger
    // The DeadLineManager-Bean is created in the Spring.run class
    @Autowired
    private transient DeadlineManager deadlineManager;

    // Saga is serialized -> mark QueryUpdateEmitter as transient,
    // so it doesn't get serialized
    // QueryUpdateEmitter informs subscription queries about updates, errors
    // and when there are no more updates
    @Autowired
    private transient QueryUpdateEmitter queryUpdateEmitter;

    // field used to save the id of the scheduled deadline (to be exact: the schedule id)
    private String scheduleId;

    // @StartSaga annotation marks this as the start of the whole SAGA
    // or rather, if this event takes place, the SAGA-flow will be started
    // @SagaEventHandler is simply an eventhandler for events, just for SAGA-flows
    // it needs an associationProperty
    // -> associationProperty is the name of the property on the
    // incoming Event that should be used to find associated Sagas.
    // The key of the association value is the name of the property.
    // The value is the value returned by property's getter method.
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        LOGGER.info("Saga -> handling orderCreatedEvent");

        // create a new command to send as a response to the (saga) event
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .orderId(orderCreatedEvent.getOrderId())
                .userId(orderCreatedEvent.getUserId())
                .build();

        // send the command
        // use an anonymous class/object to deal with the result
        this.commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

            @Override
            public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {

                // check, if the send command resulted in an exception
                if (commandResultMessage.isExceptional()) {
                    LOGGER.info("Saga -> Error in orderCreatedEvent while sending reserveProductCommand: "
                            + commandResultMessage.exceptionResult().getMessage());
                    commandResultMessage.exceptionResult().printStackTrace();

                    // if the send command resulted in an exception
                    // start a compensating transaction
                    RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(
                            orderCreatedEvent.getOrderId(),
                            commandResultMessage.exceptionResult().getMessage()
                    );

                    commandGateway.send(rejectOrderCommand);
                }
            }
        });
    }

    // @SagaEventHandler is simply an eventhandler for events, just for SAGA-flows
    // it needs an associationProperty
    // -> associationProperty is the name of the property on the
    // incoming Event that should be used to find associated Sagas.
    // The key of the association value is the name of the property.
    // The value is the value returned by property's getter method.
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        LOGGER.info("Saga -> handling productReservedEvent");

        // create a query to fetch data, that is necessary for the upcoming next command
        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery
                = new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());

        User userPaymentDetails = null;

        try {
            // if the fetching results in an error, we will call a private method, which in the end will start
            // a compensating transaction
            userPaymentDetails = this.queryGateway.query(fetchUserPaymentDetailsQuery,
                    ResponseTypes.instanceOf(User.class))
                    .join();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            this.cancelProductReservation(productReservedEvent, ex.getMessage());
            return;
        }

        // if the fetching failed, we will call a private method, which in the end will start
        // a compensating transaction
        if (userPaymentDetails == null) {
            this.cancelProductReservation(productReservedEvent, "Couldnt fetch user payment details");
        }

        // schedule a deadline
        // Args: Duration till deadline is triggered, name of the deadline and an additional payload object
        // the payload object in this case is the event, that we are trying to process in this method
        // returns the id of the scheduled deadline (to be exact: the schedule id)
        this.scheduleId = this.deadlineManager.schedule(Duration.of(10, ChronoUnit.SECONDS),
                OrderSaga.PAYMENT_PROCESSING_TIMEOUT_DEADLINE,
                productReservedEvent);

        // create a new command to send
        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .paymentId(UUID.randomUUID().toString())
                .paymentDetails(userPaymentDetails.getPaymentDetails())
                .orderId(productReservedEvent.getOrderId())
                .build();

        String result = null;

        // send the command
        // if for some reason the command failed, trigger a compensating transaction
        try {
            result = this.commandGateway.sendAndWait(processPaymentCommand);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            // Start a compensating transaction
            this.cancelProductReservation(productReservedEvent, ex.getMessage());
            return;
        }

        // if the result of the command is empty (which indicates an error in the command-handling)
        // send a compensating transaction
        if (result == null) {
            LOGGER.info("Saga -> processPaymentCommand is NULL. Instantiating a compensating transaction");
            // Start a compensating transaction
            this.cancelProductReservation(productReservedEvent,
                    "Couldnt proccess user payment with provided payment details");
        }
    }

    // private method for canceling ProductReservation, because it can be called in several places
    private void cancelProductReservation(ProductReservedEvent productReservedEvent, String reason) {
        this.cancelDeadLine();

        CancelProductReservationCommand cancelProductReservationCommand
                = CancelProductReservationCommand.builder()
                .productId(productReservedEvent.getProductId())
                .quantity(productReservedEvent.getQuantity())
                .orderId(productReservedEvent.getOrderId())
                .userId(productReservedEvent.getUserId())
                .reason(reason)
                .build();

        this.commandGateway.send(cancelProductReservationCommand);
    }

    // @SagaEventHandler is simply an eventhandler for events, just for SAGA-flows
    // it needs an associationProperty
    // -> associationProperty is the name of the property on the
    // incoming Event that should be used to find associated Sagas.
    // The key of the association value is the name of the property.
    // The value is the value returned by property's getter method.
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent) {
        LOGGER.info("Saga -> handling paymentProcessedEvent");

        // the ProductReservedEvent was successful
        // we can cancel the created deadline for this event
        this.cancelDeadLine();

        // create a new command to send
        ApproveOrderCommand approveOrderCommand
                = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());

        // send the newly created command
        this.commandGateway.sendAndWait(approveOrderCommand);
    }

    // private method for canceling the scheduled deadline
    private void cancelDeadLine() {

        if(this.scheduleId != null) {
            // cancel the scheduled deadline corresponding to the given deadline name and schedule id
            this.deadlineManager.cancelSchedule(OrderSaga.PAYMENT_PROCESSING_TIMEOUT_DEADLINE, this.scheduleId);
            this.scheduleId = null;
        }

        // cancel all scheduled deadlines corresponding to the given deadline name
        ///this.deadlineManager.cancelAll(OrderSaga.PAYMENT_PROCESSING_TIMEOUT_DEADLINE);
    }
    // @EndSaga marks this eventhandler as the last eventhandler to "activate" in the SAGA-flow
    // the SAGA-flow will be stopped after that -> nore more @SagaEventHandlers will trigger
    // @SagaEventHandler is simply an eventhandler for events, just for SAGA-flows
    // it needs an associationProperty
    // -> associationProperty is the name of the property on the
    // incoming Event that should be used to find associated Sagas.
    // The key of the association value is the name of the property.
    // The value is the value returned by property's getter method.
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        LOGGER.info("Saga -> handling orderApprovedEvent");

        LOGGER.info("Order is approved. Order saga is complete for orderId: "
                + orderApprovedEvent.getOrderId());

        // emit to the given Query the most recent result
        this.queryUpdateEmitter.emit(
                FindOrderQuery.class, // Query to emit to
                query -> true, // predicate used to filter the query -> we dont want to filter anything here
                new OrderSummary(orderApprovedEvent.getOrderId(), orderApprovedEvent.getOrderStatus(), "") // updated result
        );

        // SagaLifecycle.end(); can also end a complete Saga
    }

    // @SagaEventHandler is simply an eventhandler for events, just for SAGA-flows
    // it needs an associationProperty
    // -> associationProperty is the name of the property on the
    // incoming Event that should be used to find associated Sagas.
    // The key of the association value is the name of the property.
    // The value is the value returned by property's getter method.
    // this is an SagaEventHandler for a compensating transaction
    // -> in itself these are identical to normal SagaEventHandler
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent productReservationCancelledEvent) {

        // create a new command to send
        RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(
                productReservationCancelledEvent.getOrderId(),
                productReservationCancelledEvent.getReason()
        );

        // send the newly created command
        this.commandGateway.send(rejectOrderCommand);
    }

    // @EndSaga marks this eventhandler as the last eventhandler to "activate" in the SAGA-flow
    // the SAGA-flow will be stopped after that -> nore more @SagaEventHandlers will trigger
    // @SagaEventHandler is simply an eventhandler for events, just for SAGA-flows
    // it needs an associationProperty
    // -> associationProperty is the name of the property on the
    // incoming Event that should be used to find associated Sagas.
    // The key of the association value is the name of the property.
    // The value is the value returned by property's getter method.
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRejectedEvent orderRejectedEvent) {
        LOGGER.info("Successfully rejected order with id: " + orderRejectedEvent.getOrderId());

        // emit to the given Query the most recent result, even if the SAGA itself "failed" / rollbacked
        this.queryUpdateEmitter.emit(
                FindOrderQuery.class, // Query to emit to
                query -> true, // predicate used to filter the query -> we dont want to filter anything here
                new OrderSummary(
                        orderRejectedEvent.getOrderId(),
                        orderRejectedEvent.getOrderStatus(),
                        orderRejectedEvent.getReason()
                ) // updated result
        );
    }

    // DeadLineHandler
    // triggers, if a given deadline with the corresponding deadline name runs out
    @DeadlineHandler(deadlineName = OrderSaga.PAYMENT_PROCESSING_TIMEOUT_DEADLINE)
    public void handlePaymentDeadline(ProductReservedEvent productReservedEvent) {
        LOGGER.info(
                "Payment processing deadline took place. Sending a compensating command to cancel the product"
        );
        this.cancelProductReservation(productReservedEvent, "Payment timeout");
    }

}
