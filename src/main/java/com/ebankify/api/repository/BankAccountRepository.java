package com.ebankify.api.repository;

import com.ebankify.api.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findAllByUserId(Long userId);

    Optional<BankAccount> findByAccountNumber(UUID accountNumber);
}
