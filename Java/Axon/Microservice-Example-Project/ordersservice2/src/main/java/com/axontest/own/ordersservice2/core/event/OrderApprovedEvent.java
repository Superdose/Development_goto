package com.axontest.own.ordersservice2.core.event;

import com.axontest.own.ordersservice2.core.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

// event
@Value
public class OrderApprovedEvent {
    private final String orderId;
    private final OrderStatus orderStatus = OrderStatus.APPROVED;
}
