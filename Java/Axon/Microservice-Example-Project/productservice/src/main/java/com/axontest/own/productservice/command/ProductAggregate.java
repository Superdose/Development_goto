package com.axontest.own.productservice.command;

import com.axontest.own.core.commands.CancelProductReservationCommand;
import com.axontest.own.core.commands.ReserveProductCommand;
import com.axontest.own.core.events.ProductReservationCancelledEvent;
import com.axontest.own.core.events.ProductReservedEvent;
import com.axontest.own.productservice.core.events.ProductCreatedEvent;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

// Aggregate to communicate the state of aggregates with axon-server
// Aggregate for "Product" in this application
// we use snapshotting on this Aggregate, so we have to reference it via the property snapshotTriggerDefinition
@Aggregate(snapshotTriggerDefinition = "productSnapshotTriggerDefinition")
public class ProductAggregate {

    // Identifier for the Aggregate, so the correct information can be retrieved
    @AggregateIdentifier
    private String productId;

    private String title;
    private BigDecimal price;
    private int quantity;

    // aggregates need a default constructor
    protected ProductAggregate() {

    }

    // only the creation of the aggregate uses the constructor
    // all command handlers after that (no creation) are basic methods
    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) throws Exception {
        // Validate Create Product Command
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less or equal than zero");
        }

        if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // create an event, fill it with the necessary information und apply it to the AggregateLifecycle
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        //BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        productCreatedEvent.setProductId(createProductCommand.getProductId());
        productCreatedEvent.setTitle(createProductCommand.getTitle());
        productCreatedEvent.setQuantity(createProductCommand.getQuantity());
        productCreatedEvent.setPrice(createProductCommand.getPrice());

        AggregateLifecycle.apply(productCreatedEvent);

        // throw an exception for testing purposes
        // if (true) throw new Exception("An error took place in the CreateProductCommand @CommandHandler method");
    }

    // only the creation of the aggregate uses the constructor
    // all command handlers after that (no creation) are basic methods
    // they have the method-name "handle" by styleguide
    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {
        if (this.quantity < reserveProductCommand.getQuantity()) {
            throw new IllegalArgumentException("Insufficient number of items in stock");
        }

        // create an event, fill it with the necessary information und apply it to the AggregateLifecycle
        ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
                .productId(reserveProductCommand.getProductId())
                .quantity(reserveProductCommand.getQuantity())
                .orderId(reserveProductCommand.getOrderId())
                .userId(reserveProductCommand.getUserId())
                .build();

        AggregateLifecycle.apply(productReservedEvent);
    }

    // only the creation of the aggregate uses the constructor
    // all command handlers after that (no creation) are basic methods
    // they have the method-name "handle" by styleguide
    @CommandHandler
    public void handle(CancelProductReservationCommand cancelProductReservationCommand) {

        // you can place some sort of validation here

        // create an event, fill it with the necessary information und apply it to the AggregateLifecycle
        ProductReservationCancelledEvent productReservationCancelledEvent
                = ProductReservationCancelledEvent.builder()
                .productId(cancelProductReservationCommand.getProductId())
                .quantity(cancelProductReservationCommand.getQuantity())
                .orderId(cancelProductReservationCommand.getOrderId())
                .userId(cancelProductReservationCommand.getUserId())
                .reason(cancelProductReservationCommand.getReason())
                .build();

        AggregateLifecycle.apply(productReservationCancelledEvent);
    }

    // the EventSourcingHandler will "catch" the event before its send out
    // in the EventSourcingHandler, the aggregate state will be updated
    @EventSourcingHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {
        this.quantity += productReservationCancelledEvent.getQuantity();
    }

    // the EventSourcingHandler will "catch" the event before its send out
    // in the EventSourcingHandler, the aggregate state will be updated
    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.title = productCreatedEvent.getTitle();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    // the EventSourcingHandler will "catch" the event before its send out
    // in the EventSourcingHandler, the aggregate state will be updated
    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        this.quantity -= productReservedEvent.getQuantity();
    }
}
