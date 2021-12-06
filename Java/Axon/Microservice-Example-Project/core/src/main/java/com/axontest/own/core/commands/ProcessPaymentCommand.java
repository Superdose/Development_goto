package com.axontest.own.core.commands;


import com.axontest.own.core.model.PaymentDetails;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

// Command
@Data
@Builder
public class ProcessPaymentCommand {

    // Identifier for the Aggregate, so it can retrieve information
    @TargetAggregateIdentifier
    private final String paymentId;
    private final String orderId;
    private final PaymentDetails paymentDetails;

}
