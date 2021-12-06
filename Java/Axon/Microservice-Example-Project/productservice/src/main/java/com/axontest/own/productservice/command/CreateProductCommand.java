package com.axontest.own.productservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

// Command
@Builder
@Data
public class CreateProductCommand {

    // Identifier for the Aggregate, so it can retrieve information
    @TargetAggregateIdentifier
    private final String productId;

    private final String title;
    private final BigDecimal price;
    private final int quantity;
}
