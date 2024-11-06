package com.ebankify.api.web.dto.bankAccount;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.AccountStatus;
import com.ebankify.api.web.dto.user.UserRequestDTO;
import jakarta.validation.constraints.Min;

public record UserBankAccountRequestDTO(
        UserRequestDTO userRequestDTO,

        @Min(value = 0, message = "Balance must be at least 0")
        double balance,

        AccountStatus status
) {
    public User toUser() {
        return User.builder()
                .username(this.userRequestDTO.username())
                .email(this.userRequestDTO.email())
                .password(this.userRequestDTO.password())
                .age(this.userRequestDTO.age())
                .build();
    }

    public BankAccount toBankAccount() {
        return BankAccount.builder()
                .balance(this.balance)
                .status(this.status)
                .build();
    }

}
