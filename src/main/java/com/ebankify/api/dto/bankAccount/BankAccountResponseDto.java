package com.ebankify.api.dto.bankAccount;


import com.ebankify.api.enums.AccountStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BankAccountResponseDto {
    private UUID accountNumber;
    private double balance;
    private AccountStatus status;
    private String username;
    private String email;
}
