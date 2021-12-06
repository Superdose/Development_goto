package com.axontest.own.productservice.query.rest;

import lombok.Data;

import java.math.BigDecimal;

// Rest-Model
@Data
public class ProductRestModel {

    private String productId;
    private String title;
    private BigDecimal price;
    private int quantity;
}
