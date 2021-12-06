package com.axontest.own.ordersservice2.command.model;

import lombok.Data;

@Data
public class CreateOrderRestModel {
    private String productId;
    private int quantity;
    private String addressId;
}
