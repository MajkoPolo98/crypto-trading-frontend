package com.cryptogame.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {

    private Long id;

    private Long user_id;

    private LocalDate transaction_date;

    private String crypto_symbol;

    private BigDecimal crypto_amount;

    private BigDecimal money;

    private BigDecimal worth_now;

}
