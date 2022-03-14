package com.cryptogame.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {

    private Long id;

    private Long user_id;

    private LocalDate transactionDate;

    private String cryptoSymbol;

    private String cryptoAmount;

    private String money;

    private String worthNow;
}
