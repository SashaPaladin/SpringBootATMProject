package ru.sbrf.server.kafka.service;

import ru.sbrf.common.messages.BalanceRequest;
import ru.sbrf.common.messages.BalanceResponse;

public interface KafkaService {

    void send(BalanceResponse response);

    void consume(BalanceRequest request);
}
