package ru.sbrf.server.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "cards")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    private Long number;
    private Integer pin;

    @ManyToOne
    private Account account;
}
