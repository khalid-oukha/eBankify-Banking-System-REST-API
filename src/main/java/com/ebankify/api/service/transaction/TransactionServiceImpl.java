package com.ebankify.api.service.transaction;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.Transaction;
import com.ebankify.api.repository.TransactionRepository;
import com.ebankify.api.util.BankAccountUtils;
import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
    public TransactionResponseDTO create(TransactionRequestDTO transactionRequestDTO) {

        BankAccount accountFrom = BankAccountUtils.getBankAccountById(transactionRequestDTO.accountFromId());
        BankAccount accountTo = BankAccountUtils.getBankAccountById(transactionRequestDTO.accountToId());

        Transaction transaction = transactionRequestDTO.toTransaction(accountFrom, accountTo);

        return TransactionResponseDTO.transactionToDTO(transactionRepository.save(transaction));
    }

}
