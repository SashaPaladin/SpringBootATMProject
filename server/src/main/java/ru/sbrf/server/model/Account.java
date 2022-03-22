package ru.sbrf.server.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private Long id;
    private Long balance;

    @OneToMany
    @ToString.Exclude
    private Set<Card> cards;
}
