package ru.sbrf.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sbrf.client.service.ATMService;
import ru.sbrf.common.messages.ProcessingResponse;

@RestController
public class ATMController {

    private final ATMService atmService;

    public ATMController(ATMService atmService) {
        this.atmService = atmService;
    }

    @GetMapping("/auth")
    public void authorizationAtm() {
        atmService.authorizationAtm();
    }

    @GetMapping("/balance")
    public ProcessingResponse getCardBalance(@RequestParam Long number, @RequestParam Integer pin) {
        return atmService.getCardBalance(number, pin);
    }

    @GetMapping("/replenishment")
    public ProcessingResponse cardReplenishment(@RequestParam Long number, @RequestParam Integer pin, @RequestParam Long value) {
        return atmService.cardReplenishment(number, pin, value);
    }

    @GetMapping("/withdrawal")
    public ProcessingResponse cardWithdrawal(@RequestParam Long number, @RequestParam Integer pin, @RequestParam Long value) {
        return atmService.cardWithdrawal(number, pin, value);
    }
}
