package ru.sbrf.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.common.messages.BalanceRequest;
import ru.sbrf.common.messages.BalanceResponse;
import ru.sbrf.server.model.Card;
import ru.sbrf.server.repository.CardRepository;

import java.util.Objects;

@Service
public class BalanceServiceImpl implements BalanceService {

    private static final String REPLENISHMENT = "replenishment";
    private static final String WITHDRAWAL = "withdrawal";
    private static final String CARD_NOT_FOUND = "Card not found";
    private static final String SUCCESSFULLY = "Successfully";
    private static final String INVALID_PIN = "Invalid pin";
    private static final String INSUFFICIENT_FUNDS = "Insufficient funds";

    private final CardRepository cardRepository;

    public BalanceServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    @Override
    public BalanceResponse getResponse(BalanceRequest request) {
        Card card = cardRepository.findByNumber(request.getNumberCard());
        if (card == null) {
            return new BalanceResponse(CARD_NOT_FOUND, 0L);
        }
        if (!cardPinIsValid(request.getPin(), card.getPin())) {
            return new BalanceResponse(INVALID_PIN, 0L);
        }
        if (Objects.equals(request.getOperationType(), WITHDRAWAL)) {
            if (card.getBalance() < request.getValue()) {
                return new BalanceResponse(INSUFFICIENT_FUNDS, card.getBalance());
            }
            card.setBalance(card.getBalance() - request.getValue());
            cardRepository.save(card);
        } else if (Objects.equals(request.getOperationType(), REPLENISHMENT)) {
            card.setBalance(card.getBalance() + request.getValue());
            cardRepository.save(card);
        }
        return new BalanceResponse(SUCCESSFULLY, card.getBalance());
    }

    private boolean cardPinIsValid(Integer requestPin, Integer accountPin) {
        return requestPin.equals(accountPin);
    }
}
