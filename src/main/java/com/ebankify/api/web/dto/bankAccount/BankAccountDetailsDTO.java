package com.ebankify.api.web.dto.bankAccount;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class BankAccountDetailsDTO {
    private UUID accountNumber;
    private double balance;
    private String status;
    private String username;
    private String email;
    private List<TransactionResponseDTO> recentTransactionsFrom;
    private List<TransactionResponseDTO> recentTransactionsTo;

    public static BankAccountDetailsDTO bankAccountAndTransactionsToDTO(BankAccount bankAccount, List<TransactionResponseDTO> transactionsFrom, List<TransactionResponseDTO> transactionsTo) {
        return BankAccountDetailsDTO.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .balance(bankAccount.getBalance())
                .status(bankAccount.getStatus().toString())
                .username(bankAccount.getUser().getUsername())
                .email(bankAccount.getUser().getEmail())
                .recentTransactionsFrom(transactionsFrom)
                .recentTransactionsTo(transactionsTo)
                .build();
    }
}
