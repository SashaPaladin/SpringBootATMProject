package ru.sbrf.client.service;

import ru.sbrf.common.messages.ProcessingResponse;

public interface ATMService {

    void authorizationAtm();

    ProcessingResponse getCardBalance(Long numberCard, Integer pin);

    ProcessingResponse cardReplenishment(Long numberCard, Integer pin, Long value);

    ProcessingResponse cardWithdrawal(Long numberCard, Integer pin, Long value);
}
