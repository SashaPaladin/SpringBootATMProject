package ru.sbrf.common.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRequest {

    private Long numberCard;
    private Integer pin;
    private String operationType;
    private Long value;

    public BalanceRequest(long numberCard, int pin, String operationType) {
        this.numberCard = numberCard;
        this.pin = pin;
        this.operationType = operationType;
    }
}
