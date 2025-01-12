package com.ebankify.api.service.transaction;

import com.ebankify.api.entity.Transaction;
import com.ebankify.api.entity.enums.TransactionType;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {

    List<TransactionResponseDTO> findAll();

    List<TransactionResponseDTO> findAllByAccountFrom(Long accountFromId);

    List<TransactionResponseDTO> findAllByAccountTo(Long accountToId);

    Transaction save(Transaction transaction);

    Transaction findById(Long id);

    TransactionResponseDTO updateTransaction(Transaction transaction);

    double calculateFee(TransactionType transactionType, double amount);
}
