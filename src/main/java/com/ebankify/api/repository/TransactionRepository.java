package com.ebankify.api.repository;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountFrom(BankAccount accountFrom);

    List<Transaction> findAllByAccountTo(BankAccount accountTo);
}
