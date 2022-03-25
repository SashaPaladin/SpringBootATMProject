package ru.sbrf.client.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.client.config.ATMProperties;
import ru.sbrf.common.messages.AuthenticationRequest;
import ru.sbrf.common.messages.AuthenticationResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String SERVER_AUTH_URL = "http://localhost:8080/auth";

    private final ATMProperties atmProperties;
    private final RestTemplate restTemplate;

    public AuthenticationServiceImpl(ATMProperties atmProperties, RestTemplate restTemplate) {
        this.atmProperties = atmProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public void authenticate() {
        AuthenticationResponse response = restTemplate.postForEntity(
                        SERVER_AUTH_URL,
                        new AuthenticationRequest(atmProperties.getUUID(), atmProperties.getPassword()),
                        AuthenticationResponse.class)
                .getBody();
        if (response != null) {
            atmProperties.setToken(response.getToken());
        }
    }
}
