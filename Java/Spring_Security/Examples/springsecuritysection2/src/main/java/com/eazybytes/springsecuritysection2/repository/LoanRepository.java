package com.eazybytes.springsecuritysection2.repository;

import com.eazybytes.springsecuritysection2.entity.Loans;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LoanRepository extends CrudRepository<Loans, Integer> {

  // Enforce method-level-security -> before executing the method, check whether
  // the given User has the Role 'Root' and is therefore allowed to execute the method
  // This Annotation has to be enabled via @EnableGlobalMethodSecurity(prePostEnabled = true)
  /*@PreAuthorize("hasRole('ROOT')")*/
  List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
