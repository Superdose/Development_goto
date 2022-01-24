package com.eazybytes.springsecuritysection2.repository;

import com.eazybytes.springsecuritysection2.entity.Cards;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CardsRepository extends CrudRepository<Cards, Integer> {

  List<Cards> findByCustomerId(int customerId);

}
