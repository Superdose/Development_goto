package com.axontest.own.productservice.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository provided by Spring JPA
public interface ProductsRepository extends JpaRepository<ProductEntity, String> {

    // new method defined in the interface to retrieve information
    // Spring will detect it automatically and add the necessary logic
    // structure: >return value< findBy[Field] [Or/And] [OptionallyMoreFields] ([args]
    ProductEntity findByProductId(String productId);

    // new method defined in the interface to retrieve information
    // Spring will detect it automatically and add the necessary logic
    // structure: >return value< findBy[Field] [Or/And] [OptionallyMoreFields] ([args]
    ProductEntity findByProductIdOrTitle(String productId, String title);
}
