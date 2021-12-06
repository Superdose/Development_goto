package com.axontest.own.ordersservice2.command.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

// Command
@Value
public class RejectOrderCommand {

    // Identifier for the Aggregate, so it can retrieve information
    @TargetAggregateIdentifier
    private final String orderId;

    private final String reason;

}
