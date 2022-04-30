package guru.springframework.mapstruct.mapper;

import guru.springframework.mapstruct.model.User;
import guru.springframework.mapstruct.model.UserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCommandMapper {

  UserCommandMapper INSTANCE = Mappers.getMapper(UserCommandMapper.class);

  User toUser(UserCommand userCommand);
}
