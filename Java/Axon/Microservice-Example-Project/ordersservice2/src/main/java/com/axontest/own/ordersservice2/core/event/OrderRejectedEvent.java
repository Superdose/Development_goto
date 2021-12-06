package com.axontest.own.ordersservice2.core.event;

import com.axontest.own.ordersservice2.core.enums.OrderStatus;
import lombok.Value;

// event
@Value
public class OrderRejectedEvent {

    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus = OrderStatus.REJECTED;

}
