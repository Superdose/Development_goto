package com.axontest.own.ordersservice2.core.data;

import com.axontest.own.ordersservice2.core.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

// Hibernate Entity
@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = -1048490331964959682L;

    @Id
    @Column(unique = true)
    private String orderId;

    private String productId;
    private String userId;
    private int quantity;
    private String addressId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
