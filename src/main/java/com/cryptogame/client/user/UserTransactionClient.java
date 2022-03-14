package com.cryptogame.client.user;

import com.cryptogame.client.FrontEndConfig;
import com.cryptogame.domain.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserTransactionClient {

    private final RestTemplate restTemplate;
    private final FrontEndConfig config;

    public List<UserTransaction> getUserTransactions(){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/user/transactions")
                .build().encode().toUri();

        try {
            UserTransaction[] response = restTemplate.getForObject(url, UserTransaction[].class);
            return new ArrayList<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public UserTransaction getUserTransaction(Long userTransactionId){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/user/transactions/"+userTransactionId)
                .build().encode().toUri();
        Optional<UserTransaction> response = Optional.ofNullable(restTemplate.getForObject(url, UserTransaction.class));
        return response.orElse(new UserTransaction());
    }

    public UserTransaction buyCrypto(UserTransaction userTransaction){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/user/transactions/buy")
                .build().encode().toUri();
        return restTemplate.postForObject(url, userTransaction,UserTransaction.class);
    }
}
