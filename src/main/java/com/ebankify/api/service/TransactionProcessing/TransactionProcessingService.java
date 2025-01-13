package com.ebankify.api.service.TransactionProcessing;

import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;

import java.util.List;
import java.util.Map;

public interface TransactionProcessingService {
    TransactionResponseDTO approveTransaction(Long transactionId);

    TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO);

    TransactionResponseDTO rejectTransaction(Long transactionId);

    Map<String, List<TransactionResponseDTO>> findAllByAccountFromOrAccountTo(Long accountId);
}
