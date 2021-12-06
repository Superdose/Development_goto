package com.axontest.own.core.query;

import lombok.AllArgsConstructor;
import lombok.Data;

// query for sending
@Data
@AllArgsConstructor
public class FetchUserPaymentDetailsQuery {
    private String userid;
}
