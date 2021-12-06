package com.axontest.own.ordersservice2.command.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

// Command
@Data
@AllArgsConstructor
public class ApproveOrderCommand {

    // Identifier for the Aggregate, so it can retrieve information
    @TargetAggregateIdentifier
    private final String orderId;
}
