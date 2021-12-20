package com.amigoscode.course.mongodb;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
// @Document marks class as a domain object, that needs to be persisted to the db
// "it is kind of the collection"
public class Student {

  // @Id marks this field as an id-field -> at least one field has to be an id-field
  // -> each entry needs a unique-identifier
  @Id
  private String id;

  private String firstName;
  private String lastName;

  // @Indexed(unique = true) enables auto-indexing on the specified field
  // -> unique = true -> field has to be unique for each entry
  @Indexed(unique = true)
  private String email;

  private Gender gender;
  private Address address;
  private List<String> favouriteSubjects;
  private BigDecimal totalSpentInBooks;
  private LocalDateTime created;

  public Student(
      String firstName,
      String lastName,
      String email,
      Gender gender,
      Address address,
      List<String> favouriteSubjects,
      BigDecimal totalSpentInBooks,
      LocalDateTime created
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.gender = gender;
    this.address = address;
    this.favouriteSubjects = favouriteSubjects;
    this.totalSpentInBooks = totalSpentInBooks;
    this.created = created;
  }
}
