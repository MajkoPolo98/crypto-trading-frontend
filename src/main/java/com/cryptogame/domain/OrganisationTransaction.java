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
public class OrganisationTransaction {

    private Long id;

    private Long user_id;

    private String organisation_name;

    private LocalDate transaction_date;

    private String crypto_symbol;

    private BigDecimal crypto_amount;

    private BigDecimal money;

    private BigDecimal worth_now;

    public OrganisationTransaction(Long user_id, String organisation_name, String crypto_symbol, BigDecimal crypto_amount, BigDecimal money) {
        this.user_id = user_id;
        this.organisation_name = organisation_name;
        this.crypto_symbol = crypto_symbol;
        this.crypto_amount = crypto_amount;
        this.money = money;
    }
}
