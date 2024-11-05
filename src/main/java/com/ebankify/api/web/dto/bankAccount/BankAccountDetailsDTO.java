package com.ebankify.api.web.dto.bankAccount;

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

    public static BankAccountDetailsDTO bankAccountAndTransactionsToDTO(BankAccountResponseDto bankAccountResponseDto, List<TransactionResponseDTO> recentTransactionsFrom, List<TransactionResponseDTO> recentTransactionsTo) {
        return BankAccountDetailsDTO.builder()
                .accountNumber(bankAccountResponseDto.getAccountNumber())
                .balance(bankAccountResponseDto.getBalance())
                .status(bankAccountResponseDto.getStatus().toString())
                .username(bankAccountResponseDto.getUsername())
                .email(bankAccountResponseDto.getEmail())
                .recentTransactionsFrom(recentTransactionsFrom)
                .recentTransactionsTo(recentTransactionsTo)
                .build();
    }
}
