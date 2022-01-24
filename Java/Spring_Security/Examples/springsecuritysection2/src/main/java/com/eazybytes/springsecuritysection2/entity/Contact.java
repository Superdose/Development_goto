package com.eazybytes.springsecuritysection2.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "contact_messages")
@Data
public class Contact {

  @Id
  @Column(name = "contact_id", length = 50, nullable = false)
  private String contactId;

  @Column(name = "contact_name", length = 50, nullable = false)
  private String contactName;

  @Column(name = "contact_email", length = 100, nullable = false)
  private String contactEmail;

  @Column(name = "subject_xyz", length = 100, nullable = false)
  private String subject;

  @Column(name = "message_xyz", length = 2000, nullable = false)
  private String message;

  @Column(name = "create_dt")
  private LocalDate createDt;
}
