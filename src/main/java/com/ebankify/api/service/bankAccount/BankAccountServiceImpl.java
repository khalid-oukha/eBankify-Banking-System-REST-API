package com.ebankify.api.service.bankAccount;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.AccountStatus;
import com.ebankify.api.repository.BankAccountRepository;
import com.ebankify.api.service.user.UserService;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.web.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.web.dto.user.UserRequestDTO;
import com.ebankify.api.web.mapper.bankAccount.BankAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserService userService, BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.userService = userService;
    }


    @Override
    public BankAccountResponseDto createBankAccount(User user) {
        BankAccount createdBankAccount = bankAccountRepository.save(
                BankAccount.builder()
                        .accountNumber(UUID.randomUUID())
                        .balance(0.0)
                        .status(AccountStatus.ACTIVE)
                        .user(user)
                        .build()
        );

        return BankAccountResponseDto.fromBankAccountAndUser(
                createdBankAccount, createdBankAccount.getUser()
        );
    }

    @Override
    public BankAccountResponseDto createBankAccountExistedUser(Long userId) {
        return createBankAccount(userService.getUserById(userId).toUser());
    }

    public BankAccountResponseDto createBankAccountNewUser(UserRequestDTO userRequestDTO) {
        User user = userService.createUser(userRequestDTO).toUser();
        return createBankAccount(user);
    }

    @Override
    public BankAccountResponseDto getBankAccountsByUserId(Long userId) {

    }

    @Override
    public BankAccountResponseDto getBankAccountByAccountNumber(UUID accountNumber) {
        return null;
    }

    @Override
    public BankAccountResponseDto updateBankAccount(UUID accountNumber, UserBankAccountRequestDTO userBankAccountRequestDTO) {
        return null;
    }
}
