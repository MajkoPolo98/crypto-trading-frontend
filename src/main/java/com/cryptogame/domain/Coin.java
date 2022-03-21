package com.cryptogame.domain;

import com.vaadin.flow.component.html.Image;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coin {

    private String symbol;

    private String name;

    private BigDecimal price;

    private String logo_url;

}
