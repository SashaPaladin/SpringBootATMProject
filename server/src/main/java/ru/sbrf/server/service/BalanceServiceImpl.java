package ru.sbrf.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.common.messages.BalanceRequest;
import ru.sbrf.common.messages.BalanceResponse;
import ru.sbrf.server.exception.CardNotFoundException;
import ru.sbrf.server.model.Card;
import ru.sbrf.server.repository.CardRepository;


@Service
public class BalanceServiceImpl implements BalanceService {

    private static final String REPLENISHMENT = "replenishment";
    private static final String WITHDRAWAL = "withdrawal";
    private static final String CARD_NOT_FOUND = "Card not found";
    private static final String SUCCESSFULLY = "Successfully";
    private static final String INVALID_PIN = "Invalid pin";
    private static final String INSUFFICIENT_FUNDS = "Insufficient funds";
    private static final Long DEFAULT_RESPONSE_BALANCE = 0L;

    private final CardRepository cardRepository;

    public BalanceServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    @Override
    public BalanceResponse getResponse(BalanceRequest request) {
        Card card = findCardByNumber(request.getNumberCard());
        String operationStatus;
        if (!cardPinIsValid(request.getPin(), card.getPin())) {
            return new BalanceResponse(INVALID_PIN, DEFAULT_RESPONSE_BALANCE);
        }
        switch (request.getOperationType()) {
            case REPLENISHMENT:
                operationStatus = replenishCardBalance(card, request.getValue());
                break;
            case WITHDRAWAL:
                operationStatus = withdrawCardBalance(card, request.getValue());
                break;
            default:
                operationStatus = SUCCESSFULLY;
        }
        return new BalanceResponse(operationStatus, card.getBalance());
    }

    private Card findCardByNumber(Long number) {
        return cardRepository.findByNumber(number)
                .orElseThrow(() -> new CardNotFoundException(CARD_NOT_FOUND));
    }

    private boolean cardPinIsValid(Integer requestPin, Integer accountPin) {
        return requestPin.equals(accountPin);
    }

    private String replenishCardBalance(Card card, Long value) {
        card.setBalance(card.getBalance() + value);
        cardRepository.save(card);
        return SUCCESSFULLY;
    }

    private String withdrawCardBalance(Card card, Long value) {
        if (card.getBalance() < value) {
            return INSUFFICIENT_FUNDS;
        }
        card.setBalance(card.getBalance() - value);
        cardRepository.save(card);
        return SUCCESSFULLY;
    }
}
