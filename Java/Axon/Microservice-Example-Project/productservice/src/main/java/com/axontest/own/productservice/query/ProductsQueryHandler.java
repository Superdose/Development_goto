package com.axontest.own.productservice.query;

import com.axontest.own.productservice.core.data.ProductEntity;
import com.axontest.own.productservice.core.data.ProductsRepository;
import com.axontest.own.productservice.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Component annotation, to add the ProductsQueryHandler to the application context
// Query-methods will only be triggered, if the corresponding handler-class is in the application context
@Component
public class ProductsQueryHandler {

    // orderRepository to retrieve OrderEntities
    private final ProductsRepository productsRepository;

    // Constructor-based injection
    @Autowired
    public ProductsQueryHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    // QueryHandler to find all Products
    // we will return a List<ProductRestModel>
    // -> we dont return the ProductEntity (List) directly
    @QueryHandler
    public List<ProductRestModel> findProductsQuery(FindProductsQuery findProductsQuery) {

        List<ProductRestModel> productsRest = new ArrayList<>();

        List<ProductEntity> storedProducts = this.productsRepository.findAll();

        for (ProductEntity productEntity: storedProducts) {
            ProductRestModel productRestModel = new ProductRestModel();

            BeanUtils.copyProperties(productEntity, productRestModel);

            productsRest.add(productRestModel);
        }

        return productsRest;
    }

}
