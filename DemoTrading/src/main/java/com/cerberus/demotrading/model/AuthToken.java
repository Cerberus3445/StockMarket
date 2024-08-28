package com.cerberus.demotrading.model;

import com.cerberus.demotrading.filter.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("tokens")
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    private Long id;

    private Long userId;

    private String value;

    private Category category;
}
