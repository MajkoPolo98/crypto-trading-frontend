package com.cryptogame.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;

    private boolean adminStatus;

    private BigDecimal money;

    private BigDecimal value;

    private String group_name;

    private Map<String, BigDecimal> wallet;

    public List<CryptoInWallet> crypto(){
        List<CryptoInWallet> crypto = new ArrayList<>();
        for(Map.Entry<String, BigDecimal> entry: wallet.entrySet()){
            crypto.add(new CryptoInWallet(entry.getKey(), entry.getValue()));
        }
        return crypto;
    }

    public User(String userName, String email, String password, boolean adminStatus, BigDecimal money) {
        this.name = userName;
        this.email = email;
        this.password = password;
        this.adminStatus = adminStatus;
        this.money = money;
    }
}
