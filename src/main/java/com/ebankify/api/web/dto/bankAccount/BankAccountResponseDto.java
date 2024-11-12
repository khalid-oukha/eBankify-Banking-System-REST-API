package com.ebankify.api.web.dto.bankAccount;


import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.AccountStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class BankAccountResponseDto {
    private Long id;
    private UUID accountNumber;
    private double balance;
    private AccountStatus status;
    private String username;
    private String email;

    public static BankAccountResponseDto fromBankAccountAndUser(BankAccount bankAccount, User user) {
        return BankAccountResponseDto.builder()
                .id(bankAccount.getId())
                .accountNumber(bankAccount.getAccountNumber())
                .balance(bankAccount.getBalance())
                .status(bankAccount.getStatus())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public BankAccount toBankAccountEntity() {
        return BankAccount.builder()
                .id(this.id)
                .accountNumber(this.accountNumber)
                .balance(this.balance)
                .status(this.status)
                .build();
    }
}
