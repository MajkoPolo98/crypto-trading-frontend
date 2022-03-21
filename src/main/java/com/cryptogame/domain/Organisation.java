package com.cryptogame.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {

    private Long id;

    private String organisation_name;

    private BigDecimal organisation_funds;

    private Map<String, BigDecimal> organisation_wallet;

    private List<Long> users;

    public List<CryptoInWallet> crypto(){
        List<CryptoInWallet> crypto = new ArrayList<>();
        for(Map.Entry<String, BigDecimal> entry: organisation_wallet.entrySet()){
            crypto.add(new CryptoInWallet(entry.getKey(), entry.getValue()));
        }
        return crypto;
    }

    public Organisation(String organisation_name, BigDecimal organisation_funds) {
        this.organisation_name = organisation_name;
        this.organisation_funds = organisation_funds;
    }

}
