package com.axontest.own.core.model;

import lombok.Builder;
import lombok.Data;

// Model-class
@Data
@Builder
public class PaymentDetails {

    private final String name;
    private final String cardNumber;
    private final int validUntilMonth;
    private final int validUntilYear;
    private final String cvv;

}
