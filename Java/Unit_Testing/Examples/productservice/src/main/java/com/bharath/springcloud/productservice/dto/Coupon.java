package com.bharath.springcloud.productservice.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Coupon {
  private int id;
  private String code;
  private BigDecimal discount;
  private String expDate;
}
