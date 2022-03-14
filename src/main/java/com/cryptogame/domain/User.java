package com.cryptogame.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String userName;

    private String email;

    private String password;

    private boolean adminStatus;

    private BigDecimal money;

    private String group_name;

    private Map<String, BigDecimal> crypto;
}
