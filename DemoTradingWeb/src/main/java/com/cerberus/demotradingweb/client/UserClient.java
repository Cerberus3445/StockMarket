package com.cerberus.demotradingweb.client;

import com.cerberus.demotradingweb.dto.UserDto;

public interface UserClient {

    UserDto getById(Integer id, String token);

    UserDto getByUsernameOrEmail(String email);

    UserDto register(UserDto dto);

    UserDto update(Integer id, UserDto dto, String token);

    void delete(Integer id, String token);
}
