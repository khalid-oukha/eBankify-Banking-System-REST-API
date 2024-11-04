package com.ebankify.api.service.bankAccount;

import com.ebankify.api.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.dto.bankAccount.UserBankAccountRequestDTO;

import java.util.UUID;

public interface BankAccountService {

    BankAccountResponseDto createBankAccount(UserBankAccountRequestDTO bankAccountRequestDTO);

    BankAccountResponseDto getBankAccountByUserId(Long userId);

    BankAccountResponseDto getBankAccountByAccountNumber(UUID accountNumber);

    BankAccountResponseDto updateBankAccount(UUID accountNumber, UserBankAccountRequestDTO userBankAccountRequestDTO);
}
