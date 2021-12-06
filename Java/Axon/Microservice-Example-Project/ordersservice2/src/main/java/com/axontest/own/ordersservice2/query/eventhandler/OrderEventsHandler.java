package com.axontest.own.ordersservice2.query.eventhandler;

import com.axontest.own.ordersservice2.core.data.OrderEntity;
import com.axontest.own.ordersservice2.core.data.OrderRepository;
import com.axontest.own.ordersservice2.core.event.OrderApprovedEvent;
import com.axontest.own.ordersservice2.core.event.OrderCreatedEvent;
import com.axontest.own.ordersservice2.core.event.OrderRejectedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// This eventhandler handles all the events regarding orders
// but there can be multiple eventhandlers
// in SAGA these events can also be processed additionally
// every eventshandler must be a component -> has to be in the application context to work
@Component
public class OrderEventsHandler {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventsHandler.class);

    // repository to update states of entities
    private final OrderRepository orderRepository;

    // constructor based injection
    @Autowired
    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is used to create a new OrderEntity
    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderId(orderCreatedEvent.getOrderId());
        orderEntity.setProductId(orderCreatedEvent.getProductId());
        orderEntity.setUserId(orderCreatedEvent.getUserId());
        orderEntity.setQuantity(orderCreatedEvent.getQuantity());
        orderEntity.setAddressId(orderCreatedEvent.getAddressId());
        orderEntity.setOrderStatus(orderCreatedEvent.getOrderStatus());

        LOGGER.info("OrderEntity will be saved");

        this.orderRepository.save(orderEntity);
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is used to update the OrderStatus
    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        OrderEntity orderEntity
                = this.orderRepository.findByOrderId(orderApprovedEvent.getOrderId());

        if(orderEntity == null) {
            // TODO: Do something about it
            return;
        }

        orderEntity.setOrderStatus(orderApprovedEvent.getOrderStatus());

        this.orderRepository.save(orderEntity);
    }

    // this is an eventhandler
    // by styleguide, eventhandler-methods have the name "on"
    // the method-argument is the event to handle / to catch
    // this eventhandler is also used to update the OrderStatus
    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        OrderEntity orderEntity
                = this.orderRepository.findByOrderId(orderRejectedEvent.getOrderId());

        orderEntity.setOrderStatus(orderRejectedEvent.getOrderStatus());

        this.orderRepository.save(orderEntity);
    }

}
