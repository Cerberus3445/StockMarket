package com.cerberus.demotrading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user_balance")
public class UserBalance {

    @Id
    private Integer id;

    private Integer userId;

    private Double balance;
}
