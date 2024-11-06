package com.ebankify.api.service.transaction;

import com.ebankify.api.entity.Transaction;
import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> findAllByAccountFrom(Long accountFromId);

    List<TransactionResponseDTO> findAllByAccountTo(Long accountToId);

    TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO);

    Transaction findById(Long id);

    TransactionResponseDTO updateTransaction(Transaction transaction);
}
