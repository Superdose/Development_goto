package guru.springframework.controllers;

import guru.springframework.converter.mapper.UserMapper;
import guru.springframework.entities.User;
import guru.springframework.model.UserCommand;

public class UserController {

  public User saveUser(UserCommand command) {
    // fake implementation
    return UserMapper.INSTANCE.userCommandToUser(command);
  }

}
