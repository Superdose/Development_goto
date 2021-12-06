package com.axontest.own.productservice.command.rest;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

// Rest-Model
@Data
public class CreateProductRestModel {

    // Hibernate-Validation
    // message -> send, when the performed validation is not applicable
    @NotBlank(message = "Product title is a required field")
    private String title;

    // Hibernate-Validation
    // message -> send, when the performed validation is not applicable
    @Min(value = 1, message = "Price cannot be lower than 1")
    private BigDecimal price;

    // Hibernate-Validation
    // message -> send, when the performed validation is not applicable
    @Min(value = 1, message = "Quantity cannot be lower than 1")
    @Max(value = 5, message = "Quantity cannot be larger than 5")
    private int quantity;
}
