package com.axontest.own.productservice.core.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

// Error-Model
@Data
@AllArgsConstructor
public class ErrorMessage {
    private final Date timestamp;
    private final String message;
}
