package com.axontest.own.ordersservice2.command.commands;

import com.axontest.own.ordersservice2.core.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

// Command
@Data
@Builder
public class CreateOrderCommand {

    // Identifier for the Aggregate, so it can retrieve information
    @TargetAggregateIdentifier
    private final String orderId;

    private final String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
    private final String productId;
    private final int quantity;
    private final String addressId;
    private final OrderStatus orderStatus;

}
