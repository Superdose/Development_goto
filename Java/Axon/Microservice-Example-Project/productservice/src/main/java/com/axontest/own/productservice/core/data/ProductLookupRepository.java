package com.axontest.own.productservice.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository provided by Spring JPA
public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String> {

    // new method defined in the interface to retrieve information
    // Spring will detect it automatically and add the necessary logic
    // structure: >return value< findBy[Field] [Or/And] [OptionallyMoreFields] ([args])
    ProductLookupEntity findByProductIdOrTitle(String productId, String title);
}
