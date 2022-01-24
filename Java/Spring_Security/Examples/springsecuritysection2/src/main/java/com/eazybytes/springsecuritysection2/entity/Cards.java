package com.eazybytes.springsecuritysection2.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cards")
@Data
public class Cards {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "card_id")
  private int cardId;

  @Column(name = "customer_id")
  private int customerId;

  @Column(name = "card_number", length = 100, nullable = false)
  private String cardNumber;

  @Column(name = "card_type", length = 100, nullable = false)
  private String cardType;

  @Column(name = "total_limit", nullable = false)
  private int totalLimit;

  @Column(name = "amount_used", nullable = false)
  private int amountUsed;

  @Column(name = "available_amount", nullable = false)
  private int availableAmount;

  @Column(name = "create_dt")
  private LocalDate createDt;
}
