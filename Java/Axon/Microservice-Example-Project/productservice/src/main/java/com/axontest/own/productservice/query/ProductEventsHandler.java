package com.axontest.own.productservice.query;

import com.axontest.own.core.events.ProductReservationCancelledEvent;
import com.axontest.own.core.events.ProductReservedEvent;
import com.axontest.own.productservice.core.data.ProductEntity;
import com.axontest.own.productservice.core.data.ProductsRepository;
import com.axontest.own.productservice.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// This eventhandler handles all the events regarding products
// but there can be multiple eventhandlers
// in SAGA these events can also be processed additionally
// every eventshandler must be a component -> has to be in the application context to work

/**
 * Event Processors are the components that take care of the technical aspects of that processing (eventhandlers).
 * They start a unit of work and possibly a transaction.
 * However, they also ensure that correlation data can be correctly attached to all
 * messages created during event processing, among other non-functional requirements.
 */

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);

    // repository to update states of entities
    private final ProductsRepository productsRepository;

    // constructor based injection
    @Autowired
    public ProductEventsHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    // ExceptionHandler to simply catch Errors thrown in the EventHandlers
    // We will (re-)throw the exceptions further out
    // Here we catch all general exceptions, we dont want to catch specifically
    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception ex) throws Exception {
        // log error message

        // because we want to deal with the exception further out in the application, we will rethrow the exception
        throw ex;
    }

    // ExceptionHandler to simply catch Errors thrown in the EventHandlers
    // We will (re-)throw the exceptions further out
    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException ex) throws IllegalArgumentException {
        // log error message

        // because we want to deal with the exception further out in the application, we will rethrow the exception
        throw ex;
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is used to create a new ProductEntity
    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        ProductEntity productEntity = new ProductEntity();

        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        this.productsRepository.save(productEntity);
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is used to reserve a product (product.quantity - 1)
    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        ProductEntity productEntity = this.productsRepository.findByProductId(productReservedEvent.getProductId());

        LOGGER.debug("ProductReservedEvent: Current product quantity: " + productEntity.getQuantity());

        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());

        this.productsRepository.save(productEntity);

        LOGGER.debug("ProductReservedEvent: New product quantity: " + productEntity.getQuantity());

        ProductEventsHandler.LOGGER.info("ProductReservedEvent is called for productId: "
                + productReservedEvent.getProductId()
                + " and orderId: " + productReservedEvent.getOrderId());
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is used to compensate an earlier event
    // -> reset the product.quantity
    // this event resulted out of a compensating transaction command
    @EventHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {
        ProductEntity productEntity = this.productsRepository.findByProductId(
                productReservationCancelledEvent.getProductId());

        LOGGER.debug("ProductReservationCancelledEvent: Current product quantity: "
                + productEntity.getQuantity());

        productEntity.setQuantity(productEntity.getQuantity() + productReservationCancelledEvent.getQuantity());

        this.productsRepository.save(productEntity);

        LOGGER.debug("ProductReservationCancelledEvent: New product quantity: "
                + productEntity.getQuantity());
    }

    // This method will execute right before an events replay will be executed
    // here we prepare the db by deleting all existing values
    @ResetHandler
    public void reset() {
        this.productsRepository.deleteAll();
    }
}
