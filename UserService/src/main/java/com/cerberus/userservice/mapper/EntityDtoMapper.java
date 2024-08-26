package com.cerberus.userservice.mapper;

import com.cerberus.userservice.dto.AuthTokenDto;
import com.cerberus.userservice.dto.UserDto;
import com.cerberus.userservice.model.AuthToken;
import com.cerberus.userservice.model.User;

public class EntityDtoMapper {

    public static UserDto toDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getPassword()
        );
    }

    public static User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static AuthTokenDto toDto(AuthToken authToken){
        return new AuthTokenDto(
                authToken.getId(),
                authToken.getUserId(),
                authToken.getValue(),
                authToken.getCategory()
        );
    }

    public static AuthToken toEntity(AuthTokenDto authTokenDto){
        return new AuthToken(
                authTokenDto.getId(),
                authTokenDto.getUserId(),
                authTokenDto.getValue(),
                authTokenDto.getCategory()
        );
    }
}
