package com.ebankify.api.service.TransactionProcessing;

import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;

public interface TransactionProcessingService {
    TransactionResponseDTO approveTransaction(Long transactionId);

    TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO);

    TransactionResponseDTO rejectTransaction(Long transactionId);
}
