package com.cerberus.webservice.dto;

import com.cerberus.webservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String name;

    private String email;

    private Role role;

    private String password;
}
