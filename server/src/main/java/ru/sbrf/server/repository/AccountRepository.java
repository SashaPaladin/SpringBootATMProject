package ru.sbrf.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.server.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCards_Number(Long number);
}