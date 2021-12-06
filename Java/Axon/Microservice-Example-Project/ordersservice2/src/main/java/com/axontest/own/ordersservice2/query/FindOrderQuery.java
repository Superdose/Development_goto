package com.axontest.own.ordersservice2.query;

import lombok.Value;

// query for sending
@Value
public class FindOrderQuery {
    private final String orderId;
}
