package com.ebankify.api.dto.bankAccount;

import jakarta.validation.constraints.NotNull;

public record BankAccountRequestDTO(
        @NotNull(message = "Account type is required")
        String accountType
) {
}
