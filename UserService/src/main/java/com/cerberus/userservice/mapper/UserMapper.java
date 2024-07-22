package com.cerberus.userservice.mapper;

import com.cerberus.userservice.dto.UserDto;
import com.cerberus.userservice.model.User;

public class UserMapper {

    public static UserDto toDto(User user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.id());
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        return user;
    }
}
