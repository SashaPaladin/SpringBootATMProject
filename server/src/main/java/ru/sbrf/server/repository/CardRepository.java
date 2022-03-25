package ru.sbrf.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.server.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByNumber(Long number);
}