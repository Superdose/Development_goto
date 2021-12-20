package com.amigoscode.course.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
  private String country;
  private String postCode;
  private String city;
}
