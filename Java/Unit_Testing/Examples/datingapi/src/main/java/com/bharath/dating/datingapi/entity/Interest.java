package com.bharath.dating.datingapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Interest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String likes;

  private String dislikes;

  private String hobbies;

  private String profileUrl;

  private String about;

  @Transient
  private int userAccountId;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @JsonIgnore
  private UserAccount userAccount;
}
