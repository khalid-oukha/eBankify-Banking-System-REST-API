package com.ebankify.api.repository;

import com.ebankify.api.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findAllByUserId(Long userId);

    Optional<BankAccount> findByAccountNumber(UUID accountNumber);

    @Modifying
    @Transactional
    @Query("UPDATE BankAccount b set b.status = :status where b.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") String status);
}
