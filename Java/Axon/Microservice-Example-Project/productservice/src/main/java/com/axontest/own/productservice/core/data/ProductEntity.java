package com.axontest.own.productservice.core.data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

// Hibernate Entity
@Entity
@Table(name = "products")
@Data
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 8229116651731326642L;

    @Id
    @Column(unique = true)
    private String productId;

    @Column(unique = true)
    private String title;

    private BigDecimal price;
    private int quantity;
}
