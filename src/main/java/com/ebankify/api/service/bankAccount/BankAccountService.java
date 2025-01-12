package com.ebankify.api.service.bankAccount;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.web.dto.bankAccount.BankAccountDetailsDTO;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.web.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.web.dto.user.UserRequestDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountService {

    List<BankAccountResponseDto> findAll();

    Optional<BankAccount> findById(Long id);

    void deleteBankAccountById(Long id);

    BankAccountDetailsDTO findBankAccountDetailsById(Long id);

    BankAccountResponseDto createBankAccount(User user);

    BankAccountResponseDto createBankAccountNewUser(UserRequestDTO userRequestDTO);

    BankAccountResponseDto createBankAccountExistedUser(Long userId);

    List<BankAccountResponseDto> getBankAccountsByUserId(Long userId);

    Boolean updateBankAccountBalance(UUID accountNumber, Double balance);

    BankAccountResponseDto withdrawFromBankAccount(UUID accountNumber, Double amount);

    BankAccountResponseDto depositToBankAccount(UUID accountNumber, Double amount);

    BankAccountResponseDto updateBankAccountStatus(Long id, String status);

    BankAccountResponseDto getBankAccountByAccountNumber(UUID accountNumber);

    BankAccountResponseDto updateBankAccount(UUID accountNumber, UserBankAccountRequestDTO userBankAccountRequestDTO);
}
