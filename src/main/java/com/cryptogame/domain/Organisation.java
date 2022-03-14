package com.cryptogame.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {

    private Long id;

    private String groupName;

    private BigDecimal groupFunds;

    private Map<String, BigDecimal> crypto;

    private List<Long> users;
}
