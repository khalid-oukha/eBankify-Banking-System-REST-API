package com.ebankify.api.service.bankAccount;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.web.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.web.dto.user.UserRequestDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountService {

    Optional<BankAccount> findById(Long id);
    
    BankAccountResponseDto createBankAccount(User user);

    BankAccountResponseDto createBankAccountNewUser(UserRequestDTO userRequestDTO);

    BankAccountResponseDto createBankAccountExistedUser(Long userId);

    List<BankAccountResponseDto> getBankAccountsByUserId(Long userId);

    BankAccountResponseDto getBankAccountByAccountNumber(UUID accountNumber);

    BankAccountResponseDto updateBankAccount(UUID accountNumber, UserBankAccountRequestDTO userBankAccountRequestDTO);
}
