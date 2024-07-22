package com.cerberus.userservice.dto;

public record UserDto(
        Integer id,
        String name,
        String email
) {
}
