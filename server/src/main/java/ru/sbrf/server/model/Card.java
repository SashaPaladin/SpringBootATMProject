package ru.sbrf.server.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "cards")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity {

    @Column(name = "number")
    private Long number;
    @Column(name = "pin")
    private Integer pin;
    @Column(name = "balance")
    private Long balance;
}
