package guru.springframework.mapstruct;

import guru.springframework.mapstruct.mapper.UserMapper;
import guru.springframework.mapstruct.model.User;
import guru.springframework.mapstruct.model.UserCommand;

public class Application {

  public static void main(String[] args) {

    User user = new User(
        "Guenther",
        "Shabowski",
        "guenther.shabowski@gmail.com"
    );

    UserCommand userCommand = UserMapper.INSTANCE.toUserCommand(user);

    System.out.printf(
        "FirstName: '%s', Lastname: '%s', Email: '%s'%n",
        userCommand.getFirstName(),
        userCommand.getLastName(),
        userCommand.getEmail()
    );
  }

}
