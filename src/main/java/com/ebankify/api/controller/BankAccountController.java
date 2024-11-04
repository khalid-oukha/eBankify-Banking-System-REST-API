package com.ebankify.api.controller;

import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.service.bankAccount.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public ResponseEntity<?> createBankAccount(@Valid @RequestBody UserBankAccountRequestDTO userBankAccountRequestDTO) {
        try {
            BankAccountResponseDto createdBankAccount = bankAccountService.createBankAccount(userBankAccountRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // e.g., logger.error("Error creating bank account", e);
            return ApiResponse.badRequest("An unexpected error occurred.");
        }
    }

}
