package ru.sbrf.server.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.sbrf.common.messages.BalanceRequest;
import ru.sbrf.common.messages.BalanceResponse;
import ru.sbrf.server.service.BalanceService;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<Long, BalanceResponse> kafkaStarshipTemplate;
    private final ObjectMapper objectMapper;
    private final BalanceService balanceService;

    public KafkaServiceImpl(KafkaTemplate<Long, BalanceResponse> kafkaStarshipTemplate, ObjectMapper objectMapper, BalanceService balanceService) {
        this.kafkaStarshipTemplate = kafkaStarshipTemplate;
        this.objectMapper = objectMapper;
        this.balanceService = balanceService;
    }

    @Override
    public void send(BalanceResponse response) {
        kafkaStarshipTemplate.send("server.response", response);
    }

    @Override
    @KafkaListener(id = "Request", topics = {"client.request"}, containerFactory = "singleFactory")
    public void consume(BalanceRequest request) {
        send(balanceService.getResponse(request));
        log.info("=> consumed {}", writeValueAsString(request));
    }

    private String writeValueAsString(BalanceRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + request.toString());
        }
    }
}
