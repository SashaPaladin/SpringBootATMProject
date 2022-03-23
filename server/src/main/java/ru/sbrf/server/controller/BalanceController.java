package ru.sbrf.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sbrf.common.messages.BalanceRequest;
import ru.sbrf.common.messages.BalanceResponse;
import ru.sbrf.server.service.BalanceService;

@RestController()
@RequestMapping("/api")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/balance")
    public BalanceResponse balance(@RequestBody BalanceRequest request) {
        return balanceService.getResponse(request);
    }
}
