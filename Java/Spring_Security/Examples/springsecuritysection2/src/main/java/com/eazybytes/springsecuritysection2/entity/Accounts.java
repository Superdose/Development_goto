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
@Table(name = "accounts")
@Data
public class Accounts {

  @Column(name = "customer_id", nullable = false)
  private int customerId;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_number")
  private long accountNumber;

  @Column(name = "account_type", length = 100, nullable = false)
  private String accountType;

  @Column(name = "branch_address", length = 200, nullable = false)
  private String branchAddress;

  @Column(name = "create_dt")
  private LocalDate createDt;
}
