package com.ebankify.api.web.dto.transaction;

import com.ebankify.api.entity.Transaction;
import com.ebankify.api.entity.enums.TransactionStatus;
import com.ebankify.api.entity.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TransactionResponseDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private LocalDate date;
    private double fee;
    private TransactionStatus status;

    public static TransactionResponseDTO transactionToDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .fee(transaction.getFee())
                .status(transaction.getStatus())
                .build();
    }
}
