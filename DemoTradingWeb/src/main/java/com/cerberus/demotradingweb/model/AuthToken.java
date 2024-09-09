package com.cerberus.demotradingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "tokens")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "value")
    private String value;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;
}
