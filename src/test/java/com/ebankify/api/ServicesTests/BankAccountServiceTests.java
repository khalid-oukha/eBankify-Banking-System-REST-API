package com.ebankify.api.ServicesTests;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.AccountStatus;
import com.ebankify.api.entity.enums.Role;
import com.ebankify.api.repository.BankAccountRepository;
import com.ebankify.api.service.bankAccount.BankAccountService;
import com.ebankify.api.service.bankAccount.BankAccountServiceImpl;
import com.ebankify.api.service.transaction.TransactionService;
import com.ebankify.api.service.user.UserService;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankAccountServiceTests {
    private BankAccountRepository bankAccountRepository;
    private UserService userService;
    private TransactionService transactionService;
    private BankAccountService bankAccountService;

    @BeforeEach
    public void beforeEach() {
        bankAccountRepository = mock(BankAccountRepository.class);
        userService = mock(UserService.class);
        transactionService = mock(TransactionService.class);
        bankAccountService = new BankAccountServiceImpl(bankAccountRepository, userService, transactionService);
    }

    @Test
    void findById_should_return_bankAccount_if_exists() {
        BankAccount bankAccount = BankAccount.builder()
                .id(1000L)
                .accountNumber(UUID.randomUUID())
                .balance(0.0)
                .status(AccountStatus.ACTIVE)
                .build();

        when(bankAccountRepository.findById(1000L)).thenReturn(Optional.of(bankAccount));

        Optional<BankAccount> result = bankAccountService.findById(1000L);

        assert (result.isPresent());
        assertEquals(bankAccount, result.get(), "Bank account should match");
    }

    @Test
    void findById_should_return_empty_if_not_exists() {
        when(bankAccountRepository.findById(1000L)).thenReturn(Optional.empty());

        Optional<BankAccount> result = bankAccountService.findById(1000L);

        assert (result.isEmpty());
    }

    @Test
    void createBankAccount_should_create_new_account_for_user() {
        User ownerUser = User.builder()
                .id(1000L)
                .role(Role.USER)
                .username("khalid")
                .email("oukhakhalid@gmail")
                .build();

        BankAccount expectedBankAccount = BankAccount.builder()
                .id(1000L)
                .accountNumber(UUID.randomUUID())
                .balance(0.0)
                .status(AccountStatus.ACTIVE)
                .user(ownerUser)
                .build();


        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(expectedBankAccount);

        BankAccountResponseDto result = bankAccountService.createBankAccount(ownerUser);

        assertEquals(expectedBankAccount.getUser().getEmail(), result.getEmail(), "Email should match");
        assertEquals(expectedBankAccount.getUser().getUsername(), result.getUsername(), "Username should match");
        assertEquals(expectedBankAccount.getAccountNumber(), result.getAccountNumber(), "Account number should match");
        assertEquals(expectedBankAccount.getBalance(), result.getBalance(), "Balance should match");
        assertEquals(expectedBankAccount.getStatus(), result.getStatus(), "Status should match");
    }

    @Test
    void createBankAccountExistedUser_should_create_account_for_existing_user() {

    }

    @Test
    void createBankAccountExistedUser_should_throw_UserNotFoundException_if_user_not_found() {

    }

    @Test
    void createBankAccountNewUser_should_create_account_for_new_user() {

    }

    @Test
    void getBankAccountsByUserId_should_return_bank_accounts_for_user() {

    }

    @Test
    void getBankAccountsByUserId_should_throw_UserNotFoundException_if_user_does_not_exist() {

    }

    @Test
    void findBankAccountDetailsById_should_return_account_details_with_transactions() {

    }

    @Test
    void findBankAccountDetailsById_should_throw_exception_if_account_not_found() {

    }

    @Test
    void getBankAccountByAccountNumber_should_return_bank_account_if_exists() {

    }

    @Test
    void getBankAccountByAccountNumber_should_return_null_if_account_not_found() {

    }

    @Test
    void updateBankAccount_should_update_account_info() {

    }

    @Test
    void updateBankAccount_should_throw_exception_if_account_not_found() {

    }

    @Test
    void updateBankAccountBalance_should_update_balance_if_account_exists() {

    }

    @Test
    void updateBankAccountBalance_should_throw_exception_if_account_not_found() {

    }

    @Test
    void updateBankAccountStatus_should_update_status_if_account_exists() {

    }

    @Test
    void updateBankAccountStatus_should_throw_exception_if_account_not_found() {

    }

}
