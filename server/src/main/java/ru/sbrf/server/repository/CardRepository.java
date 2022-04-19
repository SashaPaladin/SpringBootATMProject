package ru.sbrf.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.server.model.Card;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNumber(Long number);
}