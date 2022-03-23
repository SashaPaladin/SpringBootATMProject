package ru.sbrf.server.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "atms")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ATM extends BaseEntity {

    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
}
