package com.axontest.own.core.events;

import lombok.Builder;
import lombok.Data;

// event
@Data
@Builder
public class ProductReservedEvent {

    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;

}
