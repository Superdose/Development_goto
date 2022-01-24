package com.eazybytes.springsecuritysection2.repository;

import com.eazybytes.springsecuritysection2.entity.AccountTransactions;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AccountsTransactionsRepository extends CrudRepository<AccountTransactions, String> {

  List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);

}
