package com.axontest.own.core.events;

import lombok.Builder;
import lombok.Data;

// event
@Data
@Builder
public class PaymentProcessedEvent {

    private final String orderId;
    private final String paymentId;

}
