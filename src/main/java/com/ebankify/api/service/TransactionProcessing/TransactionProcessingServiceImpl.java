package com.ebankify.api.service.TransactionProcessing;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.Transaction;
import com.ebankify.api.entity.enums.TransactionStatus;
import com.ebankify.api.exception.transactions.InvalidTransactionStatusException;
import com.ebankify.api.service.bankAccount.BankAccountService;
import com.ebankify.api.service.transaction.TransactionService;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionProcessingServiceImpl implements TransactionProcessingService {
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionProcessingServiceImpl(BankAccountService bankAccountService, TransactionService transactionService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
    }

    @Transactional
    @Override
    public TransactionResponseDTO approveTransaction(Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new InvalidTransactionStatusException("Transaction is not in a pending state and cannot be approved.");
        }

        BankAccount accountFrom = transaction.getAccountFrom();
        BankAccount accountTo = transaction.getAccountTo();

        accountFrom.setBalance(accountFrom.getBalance() - transaction.getAmount());
        accountTo.setBalance(accountTo.getBalance() + transaction.getAmount());

        bankAccountService.updateBankAccountBalance(accountFrom.getAccountNumber(), accountFrom.getBalance());
        bankAccountService.updateBankAccountBalance(accountTo.getAccountNumber(), accountTo.getBalance());

        transaction.setStatus(TransactionStatus.APPROVED);

        return transactionService.updateTransaction(transaction);
    }
}
