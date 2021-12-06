package com.axontest.own.paymentservice.data;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Hibernate Entity
@Entity
@Table(name = "payments")
@Data
public class PaymentEntity {

    @Id
    private String paymentId;

    @Column
    private String orderId;

}
