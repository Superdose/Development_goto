package com.axontest.own.productservice.core.errorhandling;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

// ErrorHandler -> @ControllerAdvice -> global error handling component
@ControllerAdvice
public class ProductsServiceErrorHandler {

    // ExceptionHandler will catch the specified thrown exceptions in the application
    // in an ExceptionHandler you usually use a ResponseEntity<> to
    // return an answer in combination with an Error-Model
    // As method args you use the caught Exception and the WebRequest
    @ExceptionHandler(value = {IllegalStateException.class} )
    public ResponseEntity<ErrorMessage> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ExceptionHandler will catch the specified thrown exceptions in the application
    // in an ExceptionHandler you usually use a ResponseEntity<> to
    // return an answer in combination with an Error-Model
    // As method args you use the caught Exception and the WebRequest
    // here we catch Exceptions in general, so we catch all Exceptions, also these, we didnt create a
    // specific ExceptionHandler for
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handleException(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ExceptionHandler will catch the specified thrown exceptions in the application
    // in an ExceptionHandler you usually use a ResponseEntity<> to
    // return an answer in combination with an Error-Model
    // As method args you use the caught Exception and the WebRequest
    // This ExceptionHandler deals with Command Exceptions (CommandExecutionException)
    // Axon Framework will wrap any exception thrown inside a Command handler into a CommandExecutionException
    @ExceptionHandler(value = {CommandExecutionException.class})
    public ResponseEntity<ErrorMessage> handleCommandExecutionException(CommandExecutionException ex,
                                                                        WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
