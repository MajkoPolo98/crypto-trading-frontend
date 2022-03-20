package com.cryptogame.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CryptoInWallet {

    private String symbol;

    private BigDecimal amount;

    public CryptoInWallet(String symbol, BigDecimal amount) {
        this.symbol = symbol;
        this.amount = amount;
    }
}
