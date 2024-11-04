package com.ebankify.api.service.bankAccount;

import com.ebankify.api.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.dto.user.UserRequestDTO;
import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.enums.Role;
import com.ebankify.api.mapper.bankAccount.BankAccountMapper;
import com.ebankify.api.repository.BankAccountRepository;
import com.ebankify.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserService userService) {
        this.bankAccountRepository = bankAccountRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public BankAccountResponseDto createBankAccount(UserBankAccountRequestDTO userBankAccountRequestDTO) {
        User user = userService.getCurrentUser();
        BankAccount bankAccount;

        if (user.getRole().equals(Role.USER)) {
            bankAccount = BankAccount.builder()
                    .balance(0)
                    .user(user)
                    .build();
        } else {
            UserRequestDTO userRequestDTO = BankAccountMapper.INSTANCE.toUserRequestDTO(userBankAccountRequestDTO);
            userService.createUser(userRequestDTO);
            bankAccount = BankAccountMapper.INSTANCE.toBankAccount(userBankAccountRequestDTO);
        }

        bankAccountRepository.save(bankAccount);

        return BankAccountMapper.INSTANCE.toResponseDto(bankAccount, user);
    }


    @Override
    public BankAccountResponseDto getBankAccountByUserId(Long userId) {
        return null;
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
