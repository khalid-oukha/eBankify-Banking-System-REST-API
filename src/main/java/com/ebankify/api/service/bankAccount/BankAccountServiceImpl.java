package com.ebankify.api.service.bankAccount;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.AccountStatus;
import com.ebankify.api.repository.BankAccountRepository;
import com.ebankify.api.service.transaction.TransactionService;
import com.ebankify.api.service.user.UserService;
import com.ebankify.api.util.UserUtils;
import com.ebankify.api.web.dto.bankAccount.BankAccountDetailsDTO;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.web.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import com.ebankify.api.web.dto.user.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserService userService, TransactionService transactionService) {
        this.bankAccountRepository = bankAccountRepository;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Override
    public List<BankAccountResponseDto> findAll() {
        return bankAccountRepository.findAll().stream()
                .map(bankAccount -> BankAccountResponseDto.fromBankAccountAndUser(bankAccount, bankAccount.getUser()))
                .toList();
    }

    @Override
    public void deleteBankAccountById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        bankAccountRepository.delete(bankAccount);
    }

    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
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

    @Override
    public BankAccountResponseDto createBankAccountNewUser(UserRequestDTO userRequestDTO) {
        User user = userService.createUser(userRequestDTO).toUser();
        return createBankAccount(user);
    }

    @Override
    public List<BankAccountResponseDto> getBankAccountsByUserId(Long userId) {
        UserUtils.validateUserExists(userId);
        return bankAccountRepository.findAllByUserId(userId).stream()
                .map(bankAccount -> BankAccountResponseDto.fromBankAccountAndUser(bankAccount, bankAccount.getUser()))
                .toList();
    }

    @Override
    public BankAccountDetailsDTO findBankAccountDetailsById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));

        List<TransactionResponseDTO> transactionFrom = transactionService.findAllByAccountFrom(id);
        List<TransactionResponseDTO> transactionTo = transactionService.findAllByAccountTo(id);

        return BankAccountDetailsDTO.bankAccountAndTransactionsToDTO(bankAccount, transactionFrom, transactionTo);
    }

    @Override
    public BankAccountResponseDto getBankAccountByAccountNumber(UUID accountNumber) {
        return null;
    }

    @Override
    public BankAccountResponseDto updateBankAccount(UUID accountNumber, UserBankAccountRequestDTO userBankAccountRequestDTO) {
        return null;
    }

    @Override
    public Boolean updateBankAccountBalance(UUID accountNumber, Double balance) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        bankAccount.setBalance(balance);
        bankAccountRepository.save(bankAccount);
        return true;
    }

    @Override
    public BankAccountResponseDto withdrawFromBankAccount(UUID accountNumber, Double amount) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        if (bankAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough balance");
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
        return BankAccountResponseDto.fromBankAccountAndUser(bankAccount, bankAccount.getUser());
    }

    @Override
    public BankAccountResponseDto depositToBankAccount(UUID accountNumber, Double amount) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        return BankAccountResponseDto.fromBankAccountAndUser(bankAccount, bankAccount.getUser());
    }

    @Override
    public BankAccountResponseDto updateBankAccountStatus(Long id, String status) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        bankAccount.setStatus(AccountStatus.valueOf(status));
        bankAccountRepository.save(bankAccount);
        return BankAccountResponseDto.fromBankAccountAndUser(bankAccount, bankAccount.getUser());
    }


}
