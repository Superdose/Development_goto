package com.eazybytes.springsecuritysection2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private int id;

  @Column(name = "name_xyz", length = 100, nullable = false)
  private String name;

  @Column(name = "email", length = 100, nullable = false)
  private String email;

  @Column(name = "mobile_number", length = 20, nullable = false)
  private String mobileNumber;

  @Column(name = "pwd", length = 500, nullable = false)
  @JsonIgnore
  private String pwd;

  @Column(name = "role_xyz", length = 100, nullable = false)
  private String role;

  @Column(name = "create_dt")
  private LocalDate createDt;

  @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
  private Set<Authority> authorities;
}
