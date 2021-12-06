package com.axontest.own.productservice.command.rest;

import com.axontest.own.productservice.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

// RestController with predefined RequestMapping
@RestController
@RequestMapping(path = "/products") // http://localhost:8080/products
public class ProductsCommandController {

    // CommandGateway to send commands
    private final CommandGateway commandGateway;

    // Constructor-based injection
    @Autowired
    public ProductsCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

//    @GetMapping
//    public String getProduct() {
//        return "dont know what was here, before deletion, maybe the port: "
//                + this.environment.getProperty("local.server.port");
//    }

    // PostMapping
    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel) {

        // create a new command
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .title(createProductRestModel.getTitle())
                .quantity(createProductRestModel.getQuantity())
                .productId(UUID.randomUUID().toString())
                .build();

        String returnValue = null;

//        try {
//            returnValue = this.commandGateway.sendAndWait(createProductCommand);
//        } catch (Exception exc) {
//            returnValue = exc.getLocalizedMessage();
//        }

        // send the command
        returnValue = this.commandGateway.sendAndWait(createProductCommand);

        return returnValue;
    }

//    @PutMapping
//    public String updateProduct() {
//        return "HTTP PUT is handled";
//    }
//
//    @DeleteMapping
//    public String deleteProduct() {
//        return "HTTP DELETE handled";
//    }

}
