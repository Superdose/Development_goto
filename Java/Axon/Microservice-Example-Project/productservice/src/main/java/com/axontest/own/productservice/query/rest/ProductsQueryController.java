package com.axontest.own.productservice.query.rest;

import com.axontest.own.productservice.query.FindProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controller to retrieve product information
// RestController with predefined RequestMapping
@RestController
@RequestMapping(path = "/products")
public class ProductsQueryController {

    // QueryGateway to send queries
    private final QueryGateway queryGateway;

    // Constructor-based injection
    @Autowired
    public ProductsQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }


    @GetMapping
    public List<ProductRestModel> getProducts() {

        // create a new query
        FindProductsQuery findProductsQuery = new FindProductsQuery();

        // send the query and specify the response-type
        List<ProductRestModel> products = this.queryGateway.query(
                findProductsQuery,
                ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();

        return products;
    }
}
