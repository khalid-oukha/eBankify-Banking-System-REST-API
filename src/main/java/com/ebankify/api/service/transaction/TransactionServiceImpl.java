package com.ebankify.api.service.transaction;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.Transaction;
import com.ebankify.api.repository.TransactionRepository;
import com.ebankify.api.service.bankAccount.BankAccountService;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountService bankAccountService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, BankAccountService bankAccountService) {
        this.transactionRepository = transactionRepository;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public List<TransactionResponseDTO> findAllByAccountFrom(Long accountFromId) {
        BankAccount accountFrom = bankAccountService.findById(accountFromId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Transaction> transactionsFrom = transactionRepository.findAllByAccountFrom(accountFrom);

        return transactionsFrom.stream().map(TransactionResponseDTO::transactionToDTO).toList();
    }

    @Override
    public List<TransactionResponseDTO> findAllByAccountTo(Long accountToId) {
        BankAccount accountTo = bankAccountService.findById(accountToId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Transaction> transactionsTo = transactionRepository.findAllByAccountTo(accountTo);

        return transactionsTo.stream().map(TransactionResponseDTO::transactionToDTO).toList();
    }
}
