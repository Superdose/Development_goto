package com.axontest.own.ordersservice2.command.controller;

import com.axontest.own.ordersservice2.command.commands.CreateOrderCommand;
import com.axontest.own.ordersservice2.command.model.CreateOrderRestModel;
import com.axontest.own.ordersservice2.core.model.OrderSummary;
import com.axontest.own.ordersservice2.query.FindOrderQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

// RestController with predefined RequestMapping
@RestController
@RequestMapping(path = "/orders")
public class OrdersCommandController {

    // CommandGateway to send commands
    private final CommandGateway commandGateway;

    // QueryGateway to send queries
    private final QueryGateway queryGateway;

    // Constructor-based injection
    @Autowired
    public OrdersCommandController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    // PostMapping
    @PostMapping
    public OrderSummary createOrder(@RequestBody CreateOrderRestModel createOrderRestModel) {

        // ignore user id
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .productId(createOrderRestModel.getProductId())
                .quantity(createOrderRestModel.getQuantity())
                .addressId(createOrderRestModel.getAddressId())
                .build();

        // subscriptionQuery -> SubscriptionQueryResult
        // use a subscription query to get the updated result instead of the old one, that may be
        // still persisted in the DB
        // this method differentiates between the initial Response and the updated Response
        SubscriptionQueryResult<OrderSummary, OrderSummary> queryResult =
                this.queryGateway.subscriptionQuery(
                    new FindOrderQuery(createOrderCommand.getOrderId()), // Arg: Query
                    ResponseTypes.instanceOf(OrderSummary.class), // Arg: initial Response
                    ResponseTypes.instanceOf(OrderSummary.class) // Arg: updated Response
                );

        try {
            // send the command
            this.commandGateway.sendAndWait(createOrderCommand);

            // return the updated result
            /// updates()
            // -> When there is an update to the subscription query, it will be emitted to this flux.
            /// blockFirst()
            // -> Subscribe to this Flux and block indefinitely until the upstream signals its first value or completes.
            return queryResult.updates().blockFirst();
        } finally {
            // always close the SubscriptionQueryResult at the end
            queryResult.close();
        }

    }
}
