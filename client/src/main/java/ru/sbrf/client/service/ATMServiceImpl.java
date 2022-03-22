package ru.sbrf.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.client.dto.*;
import ru.sbrf.common.messages.AuthenticationRequest;
import ru.sbrf.common.messages.AuthenticationResponse;
import ru.sbrf.common.messages.ProcessingRequest;
import ru.sbrf.common.messages.ProcessingResponse;

@Service
public class ATMServiceImpl implements ATMService {

    private static final Logger log = LoggerFactory.getLogger(ATMServiceImpl.class);
    private static final String SERVER_URL = "http://localhost:8080/api/";
    private static final String BALANCE = "balance";
    private static final String REPLENISHMENT = "replenishment";
    private static final String WITHDRAWAL = "withdrawal";

    private final ATMData atmData;
    private final RestTemplate restTemplate;

    public ATMServiceImpl(ATMData atmData, RestTemplate restTemplate) {
        this.atmData = atmData;
        this.restTemplate = restTemplate;
    }

    private ProcessingResponse sendWithHeader(ProcessingRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(atmData.getToken());
        HttpEntity<ProcessingRequest> requestEntity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(
                SERVER_URL + request.getOperationType(),
                HttpMethod.POST,
                requestEntity,
                ProcessingResponse.class
        ).getBody();
    }

    @Override
    public void authorizationAtm() {
        AuthenticationResponse response = restTemplate.postForEntity(
                        "http://localhost:8080/auth",
                        new AuthenticationRequest(atmData.getAtmUUID(), atmData.getPassword()),
                        AuthenticationResponse.class)
                .getBody();
        if (response != null) {
            atmData.setToken(response.getToken());
            log.info("Токен получен: " + atmData.getToken());
        }
    }

    @Override
    public ProcessingResponse getCardBalance(Long numberCard, Integer pin) {
        return sendWithHeader(new ProcessingRequest(numberCard, pin, BALANCE));
    }

    @Override
    public ProcessingResponse cardReplenishment(Long numberCard, Integer pin, Long value) {
        return sendWithHeader(new ProcessingRequest(numberCard, pin, REPLENISHMENT, value));
    }

    @Override
    public ProcessingResponse cardWithdrawal(Long numberCard, Integer pin, Long value) {
        return sendWithHeader(new ProcessingRequest(numberCard, pin, WITHDRAWAL, value));
    }
}
