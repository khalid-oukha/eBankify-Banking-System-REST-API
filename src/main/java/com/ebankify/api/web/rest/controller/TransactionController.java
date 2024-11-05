package com.ebankify.api.web.rest.controller;

import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.service.transaction.TransactionService;
import com.ebankify.api.web.dto.transaction.TransactionRequestDTO;
import com.ebankify.api.web.dto.transaction.TransactionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping()
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            TransactionResponseDTO createdTransaction = transactionService.create(transactionRequestDTO);
            return ApiResponse.created("/api/v1/transactions/" + createdTransaction.getId(), createdTransaction);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
