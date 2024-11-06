package com.ebankify.api.service.transaction;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.Transaction;
import com.ebankify.api.exception.transactions.TransactionNotFoundException;
import com.ebankify.api.repository.TransactionRepository;
import com.ebankify.api.util.BankAccountUtils;
import com.ebankify.api.validator.TransactionValidator;
import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + id + " not found"));
    }

    @Override
    public List<TransactionResponseDTO> findAllByAccountFrom(Long accountFromId) {
        BankAccount accountFrom = BankAccountUtils.getBankAccountById(accountFromId);

        List<Transaction> transactionsFrom = transactionRepository.findAllByAccountFrom(accountFrom);

        return transactionsFrom.stream().map(TransactionResponseDTO::transactionToDTO).toList();
    }

    @Override
    public List<TransactionResponseDTO> findAllByAccountTo(Long accountToId) {
        BankAccount accountTo = BankAccountUtils.getBankAccountById(accountToId);

        List<Transaction> transactionsTo = transactionRepository.findAllByAccountTo(accountTo);

        return transactionsTo.stream().map(TransactionResponseDTO::transactionToDTO).toList();
    }

    @Override
    @Transactional
    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {
        BankAccount accountFrom = BankAccountUtils.getBankAccountByAccountNumber(transactionRequestDTO.accountFromNumber());
        BankAccount accountTo = BankAccountUtils.getBankAccountByAccountNumber(transactionRequestDTO.accountToNumber());

        TransactionValidator.validateTransaction(accountFrom, accountTo, transactionRequestDTO);

        Transaction transaction = transactionRequestDTO.toTransaction(accountFrom, accountTo);
        
        return TransactionResponseDTO.transactionToDTO(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponseDTO updateTransaction(Transaction transaction) {
        return TransactionResponseDTO.transactionToDTO(transactionRepository.save(transaction));
    }
}
