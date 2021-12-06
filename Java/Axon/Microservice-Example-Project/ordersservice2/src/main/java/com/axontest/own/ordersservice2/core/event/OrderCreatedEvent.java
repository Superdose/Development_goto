package com.axontest.own.ordersservice2.core.event;

import com.axontest.own.ordersservice2.core.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

// event
@Data
@Builder
public class OrderCreatedEvent {

    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

}
