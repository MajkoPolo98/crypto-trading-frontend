package com.cryptogame.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CryptoInWallet {

    private String symbol;

    private BigDecimal amount;

}
