package com.axontest.own.ordersservice2.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository provided by Spring JPA
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    // new method defined in the interface to retrieve information
    // Spring will detect it automatically and add the necessary logic
    // structure: >return value< findBy[Field] [Or/And] [OptionallyMoreFields] ([args])
    OrderEntity findByOrderId(String orderId);
}
