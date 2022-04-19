package ru.sbrf.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.sbrf.common.messages.BalanceRequest;
import ru.sbrf.common.messages.BalanceResponse;
import ru.sbrf.server.exception.CardNotFoundException;
import ru.sbrf.server.model.Card;
import ru.sbrf.server.repository.ATMRepository;
import ru.sbrf.server.repository.CardRepository;

import java.util.Optional;

@SpringBootTest
public class BalanceServiceTest {

    private final String BALANCE = "balance";
    private final String REPLENISHMENT = "replenishment";
    private final String WITHDRAWAL = "withdrawal";
    private final String SUCCESSFULLY = "Successfully";
    private final String INVALID_PIN = "Invalid pin";
    private final String INSUFFICIENT_FUNDS = "Insufficient funds";
    private final Long DEFAULT_RESPONSE_BALANCE = 0L;

    private final Optional<Card> cardFromDB = Optional.of(new Card(1111222233330000L, 1230, 10000L));

    @Autowired
    private BalanceService balanceService;
    @MockBean
    private CardRepository cardRepository;
    @MockBean
    private ATMRepository atmRepository;

    @Test
    void testGetBalanceResponse() {
        BalanceRequest request = new BalanceRequest(cardFromDB.get().getNumber(), cardFromDB.get().getPin(), BALANCE);
        Mockito.when(cardRepository.findByNumber(request.getNumberCard())).thenReturn(cardFromDB);
        Assertions.assertEquals(balanceService.getResponse(request), new BalanceResponse(SUCCESSFULLY, cardFromDB.get().getBalance()));
    }

    @Test
    void testGetReplenishResponse() {
        BalanceRequest request = new BalanceRequest(cardFromDB.get().getNumber(), cardFromDB.get().getPin(), REPLENISHMENT, 100L);
        Mockito.when(cardRepository.findByNumber(request.getNumberCard())).thenReturn(cardFromDB);
        Assertions.assertEquals(balanceService.getResponse(request), new BalanceResponse(SUCCESSFULLY, cardFromDB.get().getBalance()));
    }

    @Test
    void testGetWithdrawResponse() {
        BalanceRequest request = new BalanceRequest(cardFromDB.get().getNumber(), cardFromDB.get().getPin(), WITHDRAWAL, 100L);
        Mockito.when(cardRepository.findByNumber(request.getNumberCard())).thenReturn(cardFromDB);
        Assertions.assertEquals(balanceService.getResponse(request), new BalanceResponse(SUCCESSFULLY, cardFromDB.get().getBalance()));
    }

    @Test
    void testFailedGetResponseCardNotFound() {
        BalanceRequest request = new BalanceRequest(cardFromDB.get().getNumber(), cardFromDB.get().getPin(), BALANCE);
        Mockito.when(cardRepository.findByNumber(request.getNumberCard())).thenReturn(cardFromDB);
        request.setNumberCard(1111222200001111L);
        Assertions.assertThrows(CardNotFoundException.class, () -> {
            balanceService.getResponse(request);
        });
    }

    @Test
    void testGetResponseInvalidPin() {
        BalanceRequest request = new BalanceRequest(cardFromDB.get().getNumber(), cardFromDB.get().getPin(), BALANCE);
        Mockito.when(cardRepository.findByNumber(request.getNumberCard())).thenReturn(cardFromDB);
        request.setPin(1001);
        Assertions.assertEquals(balanceService.getResponse(request), new BalanceResponse(INVALID_PIN, DEFAULT_RESPONSE_BALANCE));
    }

    @Test
    void testGetResponseInsufficientFunds() {
        BalanceRequest request = new BalanceRequest(cardFromDB.get().getNumber(), cardFromDB.get().getPin(), WITHDRAWAL, 111111L);
        Mockito.when(cardRepository.findByNumber(request.getNumberCard())).thenReturn(cardFromDB);
        Assertions.assertEquals(balanceService.getResponse(request), new BalanceResponse(INSUFFICIENT_FUNDS, cardFromDB.get().getBalance()));
    }
}
