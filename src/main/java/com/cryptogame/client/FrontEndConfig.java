package com.cryptogame.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FrontEndConfig {
    @Value("${backend.api.endpoint}")
    private String backApi;
}
