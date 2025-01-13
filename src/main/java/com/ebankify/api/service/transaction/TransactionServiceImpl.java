package com.ebankify.api.service.transaction;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.Transaction;
import com.ebankify.api.entity.enums.TransactionStatus;
import com.ebankify.api.entity.enums.TransactionType;
import com.ebankify.api.exception.transactions.TransactionNotFoundException;
import com.ebankify.api.repository.TransactionRepository;
import com.ebankify.api.util.BankAccountUtils;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionResponseDTO> findAll() {
        List<Transaction> transactions = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));

        transactions.sort((t1, t2) -> {
            if (t1.getStatus() == TransactionStatus.PENDING && t2.getStatus() != TransactionStatus.PENDING) {
                return -1;
            } else if (t1.getStatus() != TransactionStatus.PENDING && t2.getStatus() == TransactionStatus.PENDING) {
                return 1;
            } else {
                return 0;
            }
        });

        return transactions.stream().map(TransactionResponseDTO::transactionToDTO).toList();
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
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionResponseDTO updateTransaction(Transaction transaction) {
        return TransactionResponseDTO.transactionToDTO(transactionRepository.save(transaction));
    }

    @Override
    public double calculateFee(TransactionType transactionType, double amount) {
        double fee = 0;
        if (transactionType.equals(TransactionType.CLASSIC)) {
            fee = 0.01 * amount;
        } else if (transactionType.equals(TransactionType.INSTANT)) {
            fee = 0.005 * amount;
        } else if (transactionType.equals(TransactionType.SCHEDULED)) {
            fee = 0.0025 * amount;
        }
        return fee;
    }

    @Override
    public Map<String, List<TransactionResponseDTO>> findAllByAccountFromOrAccountTo(Long accountId) {
        BankAccount account = BankAccountUtils.getBankAccountById(accountId);

        List<TransactionResponseDTO> transactionsFrom = transactionRepository.findAllByAccountFrom(account)
                .stream()
                .map(TransactionResponseDTO::transactionToDTO)
                .toList();

        List<TransactionResponseDTO> transactionsTo = transactionRepository.findAllByAccountTo(account)
                .stream()
                .map(TransactionResponseDTO::transactionToDTO)
                .toList();

        Map<String, List<TransactionResponseDTO>> transactionsMap = new HashMap<>();
        transactionsMap.put("from", transactionsFrom);
        transactionsMap.put("to", transactionsTo);

        return transactionsMap;
    }
}
