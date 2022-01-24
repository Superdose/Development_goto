package com.eazybytes.springsecuritysection2.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "account_transactions")
@Data
public class AccountTransactions {

  @Id
  @Column(name = "transaction_id", length = 200, nullable = false)
  private String transactionId;

  @Column(name = "account_number", nullable = false)
  private long accountNumber;

  @Column(name = "customer_id", nullable = false)
  private int customerId;

  @Column(name = "transaction_dt", nullable = false)
  private LocalDate transactionDt;

  @Column(name = "transaction_summary", length = 200, nullable = false)
  private String transactionSummary;

  @Column(name = "transaction_type", length = 100, nullable = false)
  private String transactionType;

  @Column(name = "transaction_amt", nullable = false)
  private int transactionAmt;

  @Column(name = "closing_balance", nullable = false)
  private int closingBalance;

  @Column(name = "create_dt")
  private LocalDate createDt;
}
