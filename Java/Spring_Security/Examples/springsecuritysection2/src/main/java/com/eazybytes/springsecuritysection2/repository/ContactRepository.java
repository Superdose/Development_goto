package com.eazybytes.springsecuritysection2.repository;

import com.eazybytes.springsecuritysection2.entity.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, String> {

}
