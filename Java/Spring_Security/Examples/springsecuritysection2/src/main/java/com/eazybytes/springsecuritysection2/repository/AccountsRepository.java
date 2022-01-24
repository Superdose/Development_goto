package com.eazybytes.springsecuritysection2.repository;

import com.eazybytes.springsecuritysection2.entity.Accounts;
import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Accounts, Long> {

  Accounts findByCustomerId(int customerId);

}
