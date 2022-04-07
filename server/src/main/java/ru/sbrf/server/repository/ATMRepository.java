package ru.sbrf.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.server.model.ATM;

import java.util.Optional;

public interface ATMRepository extends JpaRepository<ATM, Long> {

    Optional<ATM> findByUsername(String username);
}
