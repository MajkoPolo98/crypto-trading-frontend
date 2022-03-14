package com.cryptogame.client.organisation;

import com.cryptogame.client.FrontEndConfig;
import com.cryptogame.domain.OrganisationTransaction;
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
public class OrganisationTransactionClient {

    private final RestTemplate restTemplate;
    private final FrontEndConfig config;

    public List<OrganisationTransaction> getOrganisationTransactions(){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/organisation/transactions")
                .build().encode().toUri();

        try {
            OrganisationTransaction[] response = restTemplate.getForObject(url, OrganisationTransaction[].class);
            return new ArrayList<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public OrganisationTransaction getOrganisationTransaction(Long organisationTransactionId){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/organisation/transactions/"+organisationTransactionId)
                .build().encode().toUri();
        Optional<OrganisationTransaction> response = Optional.ofNullable(restTemplate.getForObject(url, OrganisationTransaction.class));
        return response.orElse(new OrganisationTransaction());
    }

    public OrganisationTransaction buyCrypto(OrganisationTransaction organisationTransaction){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/organisation/transactions/buy")
                .build().encode().toUri();
        return restTemplate.postForObject(url, organisationTransaction,OrganisationTransaction.class);
    }
}
