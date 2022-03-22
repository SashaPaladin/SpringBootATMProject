package ru.sbrf.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.common.messages.ProcessingRequest;
import ru.sbrf.common.messages.ProcessingResponse;
import ru.sbrf.server.exception.ProcessingException;
import ru.sbrf.server.model.Account;
import ru.sbrf.server.repository.AccountRepository;

import java.util.Objects;

@Service
public class ProcessingServiceImpl implements ProcessingService {

    private static final String SUCCESSFULLY = "Successfully";
    private static final String INVALID_PIN = "Invalid pin";
    private static final String INSUFFICIENT_FUNDS = "Insufficient funds";

    private final AccountRepository accountRepository;

    public ProcessingServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private boolean validateCardPin(Integer requestPin, Integer accountPin) {
        return requestPin.equals(accountPin);
    }

    private Integer getPinCardFromAccount(Long numberCard, Account account) {
        return account.getCards().stream()
                .filter((c) -> Objects.equals(c.getNumber(), numberCard))
                .findFirst().get()
                .getPin();
    }

    private Account getAccountByNumberCard(Long numberCard) {
        return accountRepository.findByCards_Number(numberCard)
                .orElseThrow(() -> new ProcessingException("Account number not found"));
    }

    @Override
    public ProcessingResponse balance(ProcessingRequest request) {
        Account account = getAccountByNumberCard(request.getNumberCard());
        if (validateCardPin(request.getPin(), getPinCardFromAccount(request.getNumberCard(), account))) {
            return new ProcessingResponse(SUCCESSFULLY, account.getBalance());
        } else {
            return new ProcessingResponse(INVALID_PIN, 0L);
        }
    }

    @Override
    @Transactional
    public ProcessingResponse replenishment(ProcessingRequest request) {
        Account account = getAccountByNumberCard(request.getNumberCard());
        if (validateCardPin(request.getPin(), getPinCardFromAccount(request.getNumberCard(), account))) {
            account.setBalance(account.getBalance() + request.getValue());
            accountRepository.save(account);
            return new ProcessingResponse(SUCCESSFULLY, account.getBalance());
        } else {
            return new ProcessingResponse(INVALID_PIN, 0L);
        }
    }

    @Override
    @Transactional
    public ProcessingResponse withdrawal(ProcessingRequest request) {
        Account account = getAccountByNumberCard(request.getNumberCard());
        if (validateCardPin(request.getPin(), getPinCardFromAccount(request.getNumberCard(), account))) {
            if (account.getBalance() > request.getValue()) {
                account.setBalance(account.getBalance() - request.getValue());
                accountRepository.save(account);
                return new ProcessingResponse(SUCCESSFULLY, account.getBalance());
            } else {
                return new ProcessingResponse(INSUFFICIENT_FUNDS, account.getBalance());
            }
        } else {
            return new ProcessingResponse(INVALID_PIN, 0L);
        }
    }
}
