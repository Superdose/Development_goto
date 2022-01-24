package com.eazybytes.springsecuritysection2.controller;

import com.eazybytes.springsecuritysection2.entity.Contact;
import com.eazybytes.springsecuritysection2.repository.ContactRepository;
import java.time.LocalDate;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactController {

  private final ContactRepository contactRepository;

  // @PreFilter -> method filtering expression which will be evaluated before a
  // method has been invoked
  // Here -> if contactName == Test -> method will not be invoked
  /*@PreFilter("filterObject.contactName == 'Test'")*/
  @PostMapping("/contact")
  public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
    contact.setContactId(this.getServiceReqNumber());
    contact.setCreateDt(LocalDate.now());

    return this.contactRepository.save(contact);
  }

  public String getServiceReqNumber() {
    Random random = new Random();
    int ranNum = random.nextInt(999999999 - 9999) + 9999;

    return "SR"+ranNum;
  }

}
