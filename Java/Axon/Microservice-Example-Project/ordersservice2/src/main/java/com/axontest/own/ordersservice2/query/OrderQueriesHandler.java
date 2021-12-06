package com.axontest.own.ordersservice2.query;

import com.axontest.own.ordersservice2.core.data.OrderEntity;
import com.axontest.own.ordersservice2.core.data.OrderRepository;
import com.axontest.own.ordersservice2.core.model.OrderSummary;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Component annotation, to add the OrderQueriesHandler to the application context
// Query-methods will only be triggered, if the corresponding handler-class is in the application context
@Component
public class OrderQueriesHandler {

    // orderRepository to retrieve OrderEntities
    private final OrderRepository orderRepository;

    // Constructor-based injection
    @Autowired
    public OrderQueriesHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // QueryHandler to find an OrderEntity by orderId
    // we will return an OrderSummary object -> we dont return the OrderEntity directly
    @QueryHandler
    public OrderSummary findOrder(FindOrderQuery findOrderQuery) {
        OrderEntity orderEntity = this.orderRepository.findByOrderId(findOrderQuery.getOrderId());

        return new OrderSummary(orderEntity.getOrderId(), orderEntity.getOrderStatus(), "");
    }

}
