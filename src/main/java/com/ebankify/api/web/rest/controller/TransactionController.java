package com.ebankify.api.web.rest.controller;

import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.service.TransactionProcessing.TransactionProcessingService;
import com.ebankify.api.service.transaction.TransactionService;
import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionProcessingService transactionProcessingService;


    @Autowired
    public TransactionController(TransactionService transactionService, TransactionProcessingService transactionProcessingService) {
        this.transactionService = transactionService;
        this.transactionProcessingService = transactionProcessingService;
    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        List<TransactionResponseDTO> transactions = transactionService.findAll();
        return ApiResponse.ok(transactions);
    }

    @PostMapping()
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            TransactionResponseDTO createdTransaction = transactionProcessingService.create(transactionRequestDTO);
            return ApiResponse.created("/api/v1/transactions/" + createdTransaction.getId(), createdTransaction);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    @PutMapping("/{transactionId}/approve")
    public ResponseEntity<TransactionResponseDTO> approveTransaction(@PathVariable Long transactionId) {
        try {
            TransactionResponseDTO approvedTransaction = transactionProcessingService.approveTransaction(transactionId);
            return ApiResponse.ok(approvedTransaction);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    @PutMapping("/{transactionId}/reject")
    public ResponseEntity<TransactionResponseDTO> rejectTransaction(@PathVariable Long transactionId) {
        try {
            TransactionResponseDTO rejectedTransaction = transactionProcessingService.rejectTransaction(transactionId);
            return ApiResponse.ok(rejectedTransaction);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
