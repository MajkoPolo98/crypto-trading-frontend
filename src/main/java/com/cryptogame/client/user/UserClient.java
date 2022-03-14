package com.cryptogame.client.user;

import com.cryptogame.client.FrontEndConfig;
import com.cryptogame.domain.User;
import com.vaadin.flow.server.VaadinSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;
    private final FrontEndConfig config;

    public List<User> getUsers(){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/user")
                .build().encode().toUri();

        try {
            User[] response = restTemplate.getForObject(url, User[].class);
            return new ArrayList<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            return Collections.emptyList();
        }
    }

    public User getUser(Long userId){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/user/"+userId)
                .build().encode().toUri();
        Optional<User> response = Optional.ofNullable(restTemplate.getForObject(url, User.class));
        return response.orElse(new User());
    }

    public boolean authenticate(String username, String password){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/user/find/"+username)
                .build().encode().toUri();
        User user = Optional.ofNullable(restTemplate.getForObject(url, User.class)).orElse(new User());
        if (user.getPassword().equals(password)){
            VaadinSession.getCurrent().setAttribute(User.class, user);
            return true;
        } else {
            return false;
        }
    }

    public User createUser(User user){
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getBackApi() + "/user")
                .build().encode().toUri();
        return restTemplate.postForObject(url, user, User.class);
    }

}
