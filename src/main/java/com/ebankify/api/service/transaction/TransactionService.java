package com.ebankify.api.service.transaction;

import com.ebankify.api.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAllByAccountFrom(Long accountFromId);

    List<Transaction> findAllByAccountTo(Long accountToId);
}
