package com.eazybytes.springsecuritysection2.controller;

import com.eazybytes.springsecuritysection2.entity.Cards;
import com.eazybytes.springsecuritysection2.entity.Customer;
import com.eazybytes.springsecuritysection2.repository.CardsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardsController {

  private final CardsRepository cardsRepository;

  // This is not best practice concerning api design -> the course used it like this
  @PostMapping("/myCards")
  public List<Cards> getCardDetails(@RequestBody Customer customer) {

    return this.cardsRepository.findByCustomerId(customer.getId());
  }

}
