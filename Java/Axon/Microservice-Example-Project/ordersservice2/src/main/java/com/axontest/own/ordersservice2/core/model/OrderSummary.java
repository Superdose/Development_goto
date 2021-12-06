package com.axontest.own.ordersservice2.core.model;

import com.axontest.own.ordersservice2.core.enums.OrderStatus;
import lombok.Value;

// Model-class
@Value
public class OrderSummary {
    private final String orderId;
    private final OrderStatus orderStatus;
    private final String message;
}
