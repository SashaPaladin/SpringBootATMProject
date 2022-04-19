package ru.sbrf.server.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.common.messages.AuthenticationRequest;
import ru.sbrf.common.messages.AuthenticationResponse;
import ru.sbrf.server.exception.AuthenticationException;
import ru.sbrf.server.model.ATM;
import ru.sbrf.server.repository.ATMRepository;
import ru.sbrf.server.security.JwtProvider;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String INVALID_PASSWORD = "Invalid password";
    private static final String EMPTY_PASSWORD = "Password cannot be empty";
    private static final String USER_ALREADY_REGISTERED = "Username is already registered";


    private final ATMRepository atmRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(ATMRepository atmRepository, JwtProvider jwtProvider, BCryptPasswordEncoder passwordEncoder) {
        this.atmRepository = atmRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ATM> findByUsername(String username) {
        return atmRepository.findByUsername(username);
    }

    @Override
    public AuthenticationResponse auth(AuthenticationRequest request) {
        ATM atm = findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));
        if (passwordEncoder.matches(request.getPassword(), atm.getPassword())) {
            return new AuthenticationResponse(jwtProvider.generateToken(request.getUsername()));
        } else {
            throw new AuthenticationException(INVALID_PASSWORD);
        }
    }

    @Transactional
    @Override
    public void register(AuthenticationRequest request) {
        if (request.getPassword() == null) throw new AuthenticationException(EMPTY_PASSWORD);
        ATM newATM = new ATM(request.getUsername(), request.getPassword());
        if (findByUsername(newATM.getUsername()).isEmpty()) {
            newATM.setPassword(passwordEncoder.encode(newATM.getPassword()));
            atmRepository.save(newATM);
        } else {
            throw new AuthenticationException(USER_ALREADY_REGISTERED);
        }
    }
}
