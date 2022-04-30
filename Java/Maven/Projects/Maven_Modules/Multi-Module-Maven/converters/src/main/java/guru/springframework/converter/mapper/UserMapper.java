package guru.springframework.converter.mapper;

import guru.springframework.entities.User;
import guru.springframework.model.UserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserCommand userToUserCommand(User user);

  User userCommandToUser(UserCommand userCommand);
}
