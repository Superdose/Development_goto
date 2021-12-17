package com.bharath.springcloud.couponservice.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "coupon")
@Data
public class Coupon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "code", length = 20)
  private String code;

  @Column(name = "discount", precision = 8, scale = 3)
  private BigDecimal discount;

  @Column(name = "exp_date", length = 100)
  private String expDate;

}
