package com.axontest.own.paymentservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository provided by Spring JPA
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
}
