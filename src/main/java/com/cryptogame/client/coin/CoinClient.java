package com.cryptogame.client.coin;

import com.cryptogame.client.FrontEndConfig;
import com.cryptogame.domain.Coin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CoinClient {

    private final RestTemplate restTemplate;
    private final FrontEndConfig config;

    public List<Coin> getCoins(){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/stock")
                .build().encode().toUri();

        try {
            Coin[] response = restTemplate.getForObject(url, Coin[].class);
            return new ArrayList<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public Coin getCoin(String symbol){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/stock/"+symbol)
                .build().encode().toUri();
        Optional<Coin> response = Optional.ofNullable(restTemplate.getForObject(url, Coin.class));
        return response.orElse(new Coin());
    }

}
