package com.bharath.dating.datingapi.controller;

import com.bharath.dating.datingapi.entity.Interest;
import com.bharath.dating.datingapi.entity.UserAccount;
import com.bharath.dating.datingapi.repository.InterestRepository;
import com.bharath.dating.datingapi.repository.UserAccountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserAccountController {

  private final UserAccountRepository userRepo;

  private final InterestRepository interestRepo;

  @Autowired
  public UserAccountController(UserAccountRepository userRepo, InterestRepository interestRepo) {
    this.userRepo = userRepo;
    this.interestRepo = interestRepo;
  }

  @PostMapping(path = "/users/register-user")
  public UserAccount registerUser(@RequestBody UserAccount userAccount) {
      return this.userRepo.save(userAccount);
  }

  @PostMapping(path = "/interests/update")
  public Interest updateInterest(@RequestBody Interest interest) {
    UserAccount userAccount = this.userRepo.findById(interest.getUserAccountId()).get();
    interest.setUserAccount(userAccount);

    return this.interestRepo.save(interest);
  }

  @GetMapping(path = "/users/get/all")
  public List<UserAccount> getUser() {
    return this.userRepo.findAll();
  }

  @DeleteMapping(path = "/users/delete/{interestId}")
  public void deleteInterest(@PathVariable("interestId") int id) {
    this.interestRepo.deleteById(id);
  }

  @GetMapping(path = "/users/matches/{id}")
  public List<UserAccount> findMatches(@PathVariable("id") int id) {
    UserAccount userAccount = this.userRepo.findById(id).get();

    return this.userRepo.findMatches(
        userAccount.getAge(),
        userAccount.getCity(),
        userAccount.getCountry(),
        userAccount.getId()
    );
  }

}
