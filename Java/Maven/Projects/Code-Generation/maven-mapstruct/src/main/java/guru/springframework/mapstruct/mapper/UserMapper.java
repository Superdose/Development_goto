package guru.springframework.mapstruct.mapper;

import guru.springframework.mapstruct.model.User;
import guru.springframework.mapstruct.model.UserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserCommand toUserCommand(User user);
}
