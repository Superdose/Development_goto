package com.axontest.own.ordersservice2.command;

import com.axontest.own.ordersservice2.command.commands.ApproveOrderCommand;
import com.axontest.own.ordersservice2.command.commands.CreateOrderCommand;
import com.axontest.own.ordersservice2.command.commands.RejectOrderCommand;
import com.axontest.own.ordersservice2.core.enums.OrderStatus;
import com.axontest.own.ordersservice2.core.event.OrderApprovedEvent;
import com.axontest.own.ordersservice2.core.event.OrderCreatedEvent;
import com.axontest.own.ordersservice2.core.event.OrderRejectedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

// Aggregate to communicate the state of aggregates with axon-server
@Aggregate
public class OrderAggregate {

    // Identifier for the Aggregate, so the correct information can be retrieved
    @AggregateIdentifier
    private String orderId;

    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    // aggregates need a default constructor
    protected OrderAggregate() {}

    // only the creation of the aggregate uses the constructor
    // all command handlers after that (no creation) are basic methods
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        // you can place some sort of validation here

        // create an event, fill it with the necessary information und apply it to the AggregateLifecycle
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(createOrderCommand.getOrderId())
                .productId(createOrderCommand.getProductId())
                .userId(createOrderCommand.getUserId())
                .quantity(createOrderCommand.getQuantity())
                .addressId(createOrderCommand.getAddressId())
                .orderStatus(createOrderCommand.getOrderStatus())
                .build();

        AggregateLifecycle.apply(orderCreatedEvent);
    }

    // only the creation of the aggregate uses the constructor
    // all command handlers after that (no creation) are basic methods
    // they have the method-name "handle" by styleguide
    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand) {
        // you can place some sort of validation here

        // create an event, fill it with the necessary information und apply it to the AggregateLifecycle
        OrderApprovedEvent approvedEvent
                = new OrderApprovedEvent(approveOrderCommand.getOrderId());

        AggregateLifecycle.apply(approvedEvent);
    }

    // the EventSourcingHandler will "catch" the event before its send out
    // in the EventSourcingHandler, the aggregate state will be updated
    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.userId = orderCreatedEvent.getUserId();
        this.productId = orderCreatedEvent.getProductId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.addressId = orderCreatedEvent.getAddressId();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    // the EventSourcingHandler will "catch" the event before its send out
    // in the EventSourcingHandler, the aggregate state will be updated
    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }

    // only the creation of the aggregate uses the constructor
    // all command handlers after that (no creation) are basic methods
    // they have the method-name "handle" by styleguide
    @CommandHandler
    public void handle(RejectOrderCommand rejectOrderCommand) {
        // you can place some sort of validation here

        // create an event, fill it with the necessary information und apply it to the AggregateLifecycle
        OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent(
                rejectOrderCommand.getOrderId(),
                rejectOrderCommand.getReason()
        );

        AggregateLifecycle.apply(orderRejectedEvent);
    }

    // the EventSourcingHandler will "catch" the event before its send out
    // in the EventSourcingHandler, the aggregate state will be updated
    @EventSourcingHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        this.orderStatus = orderRejectedEvent.getOrderStatus();
    }

}
