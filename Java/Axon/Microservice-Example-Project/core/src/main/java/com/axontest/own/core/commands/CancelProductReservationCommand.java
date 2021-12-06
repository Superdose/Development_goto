package com.axontest.own.core.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

// Command
@Data
@Builder
public class CancelProductReservationCommand {

    // Identifier for the Aggregate, so it can retrieve information
    @TargetAggregateIdentifier
    private final String productId;

    private final int quantity;
    private final String orderId;
    private final String userId;
    private final String reason;

}
