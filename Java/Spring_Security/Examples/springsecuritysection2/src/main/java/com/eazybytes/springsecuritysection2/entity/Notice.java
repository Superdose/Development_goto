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
@Table(name = "notice_details")
@Data
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_id")
  private int noticeId;

  @Column(name = "notice_summary", length = 200, nullable = false)
  private String noticeSummary;

  @Column(name = "notice_details", length = 500, nullable = false)
  private String noticeDetails;

  @Column(name = "notic_beg_dt", nullable = false)
  private LocalDate noticBegDt;

  @Column(name = "notic_end_dt")
  private LocalDate noticEndDt;

  @Column(name = "create_dt")
  private LocalDate createDt;

  @Column(name = "update_dt")
  private LocalDate updateDt;
}
