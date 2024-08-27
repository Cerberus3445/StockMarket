package com.cerberus.stockmarket.dto;

import com.cerberus.stockmarket.filter.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthTokenDto {

    private Long id;

    private Long userId;

    private String value;

    private Category category;
}
