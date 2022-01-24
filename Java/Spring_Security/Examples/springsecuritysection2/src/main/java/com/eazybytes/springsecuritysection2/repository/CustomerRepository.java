package com.eazybytes.springsecuritysection2.repository;

import com.eazybytes.springsecuritysection2.entity.Customer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

  List<Customer> findByEmail(String email);

}
