package com.eazybytes.springsecuritysection2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "authority_name", length = 50, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Customer customer;
}
