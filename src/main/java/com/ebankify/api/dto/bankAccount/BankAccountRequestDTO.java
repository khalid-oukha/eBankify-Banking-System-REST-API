package com.ebankify.api.dto.bankAccount;

public record BankAccountRequestDTO(
        
        String accountNumber,
        String accountType,
        String bankName,
        String branchName,
        String ifscCode
) {
}
