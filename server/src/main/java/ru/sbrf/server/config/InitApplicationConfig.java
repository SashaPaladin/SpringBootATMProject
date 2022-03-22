package ru.sbrf.server.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbrf.common.messages.AuthenticationRequest;
import ru.sbrf.server.service.AuthenticationService;

@Configuration
public class InitApplicationConfig {

    @Bean
    CommandLineRunner initDatabaseWithEncodedPasswords(AuthenticationService authenticationService) {
        return args -> {
            authenticationService.register(new AuthenticationRequest("username", "password"));
            authenticationService.register(new AuthenticationRequest("test", "test"));
        };
    }
}
