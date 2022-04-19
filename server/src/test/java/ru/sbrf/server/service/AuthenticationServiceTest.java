package ru.sbrf.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.sbrf.common.messages.AuthenticationRequest;
import ru.sbrf.common.messages.AuthenticationResponse;
import ru.sbrf.server.exception.AuthenticationException;
import ru.sbrf.server.model.ATM;
import ru.sbrf.server.repository.ATMRepository;
import ru.sbrf.server.security.JwtProvider;

import java.util.Optional;

@SpringBootTest
public class AuthenticationServiceTest {

    private final AuthenticationRequest request = new AuthenticationRequest("username", "password");
    private final Optional<ATM> userFromDb = Optional.of(new ATM("username", "password"));
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtProvider jwtProvider;
    @MockBean
    private ATMRepository atmRepository;

    @Test
    public void testAuth() {
        userFromDb.get().setPassword(passwordEncoder.encode(userFromDb.get().getPassword()));
        Mockito.when(atmRepository.findByUsername(request.getUsername())).thenReturn(userFromDb);
        Assertions.assertEquals(authenticationService.auth(request), new AuthenticationResponse(jwtProvider.generateToken(request.getUsername())));
    }

    @Test
    public void testAuthUserNotFound() {
        userFromDb.get().setPassword(passwordEncoder.encode(userFromDb.get().getPassword()));
        Mockito.when(atmRepository.findByUsername(request.getUsername())).thenReturn(userFromDb);
        request.setUsername("unknownUser");
        Assertions.assertThrows(AuthenticationException.class, () -> {
            authenticationService.auth(request);
        });
    }

    @Test
    public void testAuthInvalidPass() {
        userFromDb.get().setPassword(passwordEncoder.encode(userFromDb.get().getPassword()));
        Mockito.when(atmRepository.findByUsername(request.getUsername())).thenReturn(userFromDb);
        request.setPassword("wrongPass");
        Assertions.assertThrows(AuthenticationException.class, () -> {
            authenticationService.auth(request);
        });
    }

    @Test
    public void testFindByUsernameWhenUserIsInDatabase() {
        Mockito.when(atmRepository.findByUsername(request.getUsername())).thenReturn(userFromDb);
        Assertions.assertNotNull(authenticationService.findByUsername(request.getUsername()));
    }

    @Test
    public void testFindByUsernameWhenUserIsNotInDatabase() {
        Mockito.when(atmRepository.findByUsername(request.getUsername())).thenReturn(null);
        Assertions.assertNull(authenticationService.findByUsername(request.getUsername()));
    }
}
