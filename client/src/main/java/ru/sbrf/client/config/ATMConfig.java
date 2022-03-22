package ru.sbrf.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.client.dto.ATMData;

@Configuration
public class ATMConfig {

    @Value("${atm.uuid}")
    private String atmUUID;
    @Value("${atm.password}")
    private String atmPassword;

    @Bean
    ATMData atmInit() {
        return new ATMData(atmUUID, atmPassword);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
