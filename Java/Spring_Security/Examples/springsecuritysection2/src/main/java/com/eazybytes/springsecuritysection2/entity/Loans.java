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
@Table(name = "loans")
@Data
public class Loans {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "loan_number")
  private int loanNumber;

  @Column(name = "customer_id", nullable = false)
  private int customerId;

  @Column(name = "start_dt", nullable = false)
  private LocalDate startDt;

  @Column(name = "loan_type", length = 100, nullable = false)
  private String loanType;

  @Column(name = "total_loan", nullable = false)
  private int totalLoan;

  @Column(name = "amount_paid", nullable = false)
  private int amountPaid;

  @Column(name = "outstanding_amount", nullable = false)
  private int outstandingAmount;

  @Column(name = "create_dt")
  private LocalDate createDt;
}
