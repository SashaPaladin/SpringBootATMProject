package ru.sbrf.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sbrf.common.messages.ProcessingRequest;
import ru.sbrf.common.messages.ProcessingResponse;
import ru.sbrf.server.service.ProcessingService;

@RestController()
@RequestMapping("/api")
public class ProcessingController {

    private final ProcessingService processingService;

    public ProcessingController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/balance")
    public ProcessingResponse balance(@RequestBody ProcessingRequest request) {
        return processingService.balance(request);
    }

    @PostMapping("/replenishment")
    public ProcessingResponse replenishment(@RequestBody ProcessingRequest request) {
        return processingService.replenishment(request);
    }

    @PostMapping("/withdrawal")
    public ProcessingResponse withdrawal(@RequestBody ProcessingRequest request) {
        return processingService.withdrawal(request);
    }
}
