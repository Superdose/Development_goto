package com.axontest.own.core.model;

import lombok.Builder;
import lombok.Data;

// Model-class
@Data
@Builder
public class User {

    private final String firstName;
    private final String lastName;
    private final String userId;
    private final PaymentDetails paymentDetails;

}
