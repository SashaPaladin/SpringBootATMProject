package ru.sbrf.server.service;

import ru.sbrf.common.messages.ProcessingRequest;
import ru.sbrf.common.messages.ProcessingResponse;

public interface ProcessingService {

    ProcessingResponse balance(ProcessingRequest request);

    ProcessingResponse replenishment(ProcessingRequest request);

    ProcessingResponse withdrawal(ProcessingRequest request);
}
