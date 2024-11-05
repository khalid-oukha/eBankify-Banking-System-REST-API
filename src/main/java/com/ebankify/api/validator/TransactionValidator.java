package com.ebankify.api.validator;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.enums.AccountStatus;
import com.ebankify.api.exception.transactions.InactiveAccountException;
import com.ebankify.api.exception.transactions.InsufficientBalanceException;
import com.ebankify.api.exception.transactions.SameAccountTransactionException;
import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;

public class TransactionValidator {

    public static void validateTransaction(BankAccount accountFrom, BankAccount accountTo, TransactionRequestDTO requestDTO) {
        validateBalance(accountFrom, requestDTO.amount());
        validateAccountStatus(accountFrom, accountTo);
        validateDifferentAccounts(accountFrom, accountTo);
    }

    private static void validateBalance(BankAccount accountFrom, Double amount) {
        if (accountFrom.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance in the source account.");
        }
    }

    private static void validateAccountStatus(BankAccount accountFrom, BankAccount accountTo) {
        if (!accountFrom.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new InactiveAccountException("Source account is inactive.");
        }
        if (!accountTo.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new InactiveAccountException("Destination account is inactive.");
        }
    }

    private static void validateDifferentAccounts(BankAccount accountFrom, BankAccount accountTo) {
        if (accountFrom.getAccountNumber().equals(accountTo.getAccountNumber())) {
            throw new SameAccountTransactionException("Source and destination accounts cannot be the same.");
        }
    }
}
