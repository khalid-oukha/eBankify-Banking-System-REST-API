package com.ebankify.api.service.TransactionProcessing;

import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;

public interface TransactionProcessingService {
    TransactionResponseDTO approveTransaction(Long transactionId);

    TransactionResponseDTO rejectTransaction(Long transactionId);
}
