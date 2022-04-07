package ru.sbrf.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.sbrf.common.messages.AuthenticationRequest;
import ru.sbrf.server.model.ATM;
import ru.sbrf.server.repository.ATMRepository;

import java.util.Optional;

@SpringBootTest
public class AuthenticationServiceTest {

    private final AuthenticationRequest request = new AuthenticationRequest("username", "password");
    private final Optional<ATM> userFromDb = Optional.of(new ATM("username", "password"));
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationService authenticationService;
    @MockBean
    private ATMRepository atmRepository;

    @Test
    void findByUsernameTest() {
    }

    @Test
    public void authTest() {
        //TODO()
    }

    @Test
    public void registerTest() {
        //TODO()
    }

    @Test
    public void findByUsernameWhenUserIsInDatabaseTest() {
        Mockito.when(atmRepository.findByUsername(request.getUsername())).thenReturn(userFromDb);
        authenticationService.findByUsername(request.getUsername());
        Assertions.assertNotNull(authenticationService.findByUsername(request.getUsername()));
    }

    @Test
    public void findByUsernameWhenUserIsNotInDatabaseTest() {
        Mockito.when(atmRepository.findByUsername(request.getUsername())).thenReturn(null);
        authenticationService.findByUsername(request.getUsername());
        Assertions.assertNull(authenticationService.findByUsername(request.getUsername()));
    }
}
