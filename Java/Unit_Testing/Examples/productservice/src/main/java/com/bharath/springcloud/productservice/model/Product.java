package com.bharath.springcloud.productservice.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name", length = 20)
  private String name;

  @Column(name = "description", length = 100)
  private String description;

  @Column(name = "price", precision = 8, scale = 3)
  private BigDecimal price;

  @Transient
  private String couponCode;
}
