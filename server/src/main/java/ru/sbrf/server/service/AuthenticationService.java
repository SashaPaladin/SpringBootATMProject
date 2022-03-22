package ru.sbrf.server.service;

import ru.sbrf.common.messages.AuthenticationRequest;
import ru.sbrf.common.messages.AuthenticationResponse;
import ru.sbrf.server.model.ATM;

import java.util.Optional;

public interface AuthenticationService {

    Optional<ATM> findByUsername(String username);

    AuthenticationResponse auth(AuthenticationRequest authenticationRequest);

    void register(AuthenticationRequest authenticationRequest);
}
