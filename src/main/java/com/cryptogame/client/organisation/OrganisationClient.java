package com.cryptogame.client.organisation;

import com.cryptogame.client.FrontEndConfig;
import com.cryptogame.domain.Organisation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrganisationClient {

    private final RestTemplate restTemplate;
    private final FrontEndConfig config;

    public List<Organisation> getOrganisations(){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/organisation")
                .build().encode().toUri();

        try {
            Organisation[] response = restTemplate.getForObject(url, Organisation[].class);
            return new ArrayList<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public Organisation getOrganisation(Long organisationId){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/organisation/"+organisationId)
                .build().encode().toUri();
        Optional<Organisation> response = Optional.ofNullable(restTemplate.getForObject(url, Organisation.class));
        return response.orElse(new Organisation());
    }
}
