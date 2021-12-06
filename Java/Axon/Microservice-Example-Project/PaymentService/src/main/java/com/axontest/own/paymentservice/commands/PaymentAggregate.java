package com.axontest.own.paymentservice.commands;

import com.axontest.own.core.commands.ProcessPaymentCommand;
import com.axontest.own.core.events.PaymentProcessedEvent;
import com.axontest.own.core.model.PaymentDetails;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

// Aggregate to communicate the state of aggregates with axon-server
@Aggregate
public class PaymentAggregate {

    // Identifier for the Aggregate, so the correct information can be retrieved
    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    // aggregates need a default constructor
    protected PaymentAggregate() {}

    // only the creation of the aggregate uses the constructor
    // all command handlers after that (no creation) are basic methods
    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand) {

        // you can place some sort of validation here

        // create an event, fill it with the necessary information und apply it to the AggregateLifecycle
        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                .paymentId(processPaymentCommand.getPaymentId())
                .orderId(processPaymentCommand.getOrderId())
                .build();

        AggregateLifecycle.apply(paymentProcessedEvent);

    }

    // the EventSourcingHandler will "catch" the event before its send out
    // in the EventSourcingHandler, the aggregate state will be updated
    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }

}
