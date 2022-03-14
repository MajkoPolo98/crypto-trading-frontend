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

    private LocalDate transactionDate;

    private String cryptoSymbol;

    private BigDecimal cryptoAmount;

    private BigDecimal money;

    private BigDecimal worthNow;

    public OrganisationTransaction(String organisation_name, String cryptoSymbol, BigDecimal cryptoAmount, BigDecimal money) {
        this.organisation_name = organisation_name;
        this.cryptoSymbol = cryptoSymbol;
        this.cryptoAmount = cryptoAmount;
        this.money = money;
    }
}
