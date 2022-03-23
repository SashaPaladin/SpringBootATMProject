package ru.sbrf.server.service;

import ru.sbrf.common.messages.BalanceRequest;
import ru.sbrf.common.messages.BalanceResponse;

public interface BalanceService {

    BalanceResponse getResponse(BalanceRequest request);
}
