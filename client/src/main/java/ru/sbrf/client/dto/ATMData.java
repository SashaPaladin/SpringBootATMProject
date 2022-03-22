package ru.sbrf.client.dto;

import lombok.Data;

@Data
public class ATMData {

    private String atmUUID;
    private String password;
    private String token;

    public ATMData(String atmUUID, String password) {
        this.atmUUID = atmUUID;
        this.password = password;
    }
}
