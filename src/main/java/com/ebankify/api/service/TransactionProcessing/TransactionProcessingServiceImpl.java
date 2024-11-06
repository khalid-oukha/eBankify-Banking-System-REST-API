package com.ebankify.api.service.TransactionProcessing;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.Transaction;
import com.ebankify.api.entity.enums.TransactionStatus;
import com.ebankify.api.exception.transactions.InvalidTransactionStatusException;
import com.ebankify.api.service.bankAccount.BankAccountService;
import com.ebankify.api.service.transaction.TransactionService;
import com.ebankify.api.util.BankAccountUtils;
import com.ebankify.api.util.TransactionValidator;
import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
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

    @Override
    @Transactional
    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
        BankAccount accountFrom = BankAccountUtils.getBankAccountByAccountNumber(transactionRequestDTO.accountFromNumber());
        BankAccount accountTo = BankAccountUtils.getBankAccountByAccountNumber(transactionRequestDTO.accountToNumber());

        Transaction transaction = transactionRequestDTO.toTransaction(accountFrom, accountTo);

        double fee = transactionService.calculateFee(transaction.getType(), transaction.getAmount());
        TransactionValidator.validateTransaction(accountFrom, accountTo, transactionRequestDTO, fee);

        updateDebitAccountBalance(transaction.getAccountFrom(), transaction.getAmount(), fee);

        if (transaction.getAmount() < 10000) {
            updateCreditAccountBalance(transaction.getAccountTo(), transaction.getAmount());
            transaction.setStatus(TransactionStatus.APPROVED);
        }

        transaction.setFee(fee);
        return TransactionResponseDTO.transactionToDTO(transactionService.save(transaction));
    }


    @Transactional
    @Override
    public TransactionResponseDTO approveTransaction(Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new InvalidTransactionStatusException("Transaction is not in a pending state and cannot be approved.");
        }

        updateCreditAccountBalance(transaction.getAccountTo(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.APPROVED);

        return transactionService.updateTransaction(transaction);
    }

    @Override
    public TransactionResponseDTO rejectTransaction(Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new InvalidTransactionStatusException("Transaction is not in a pending state and cannot be approved.");
        }
        updateDebitAccountBalance(transaction.getAccountFrom(), -transaction.getAmount(), 0);
        updateCreditAccountBalance(transaction.getAccountTo(), -transaction.getAmount());
        transaction.setStatus(TransactionStatus.REJECTED);

        return transactionService.updateTransaction(transaction);
    }


    public void updateDebitAccountBalance(BankAccount accountFrom, double amount, double fee) {
        accountFrom.setBalance(accountFrom.getBalance() - (amount + fee));
        bankAccountService.updateBankAccountBalance(accountFrom.getAccountNumber(), accountFrom.getBalance());
    }

    public void updateCreditAccountBalance(BankAccount accountTo, double amount) {
        accountTo.setBalance(accountTo.getBalance() + amount);
        bankAccountService.updateBankAccountBalance(accountTo.getAccountNumber(), accountTo.getBalance());
    }
}
