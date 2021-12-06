package com.axontest.own.productservice.core.events;

import lombok.Data;

import java.math.BigDecimal;

// event
@Data
public class ProductCreatedEvent {

    private String productId;
    private String title;
    private BigDecimal price;
    private int quantity;
}
