package com.otsa.useridentificator.mapper;

import com.otsa.useridentificator.dto.UserDto;
import com.otsa.useridentificator.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto userToUserDto(User user);
    List<UserDto> usersToUserDtos(List<User> users);
}
