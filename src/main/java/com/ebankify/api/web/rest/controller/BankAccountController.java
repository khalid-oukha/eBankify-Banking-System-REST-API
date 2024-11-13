package com.ebankify.api.web.rest.controller;

import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.Role;
import com.ebankify.api.exception.user.UserAlreadyExistsException;
import com.ebankify.api.service.bankAccount.BankAccountService;
import com.ebankify.api.util.UserUtils;
import com.ebankify.api.web.dto.bankAccount.BankAccountDetailsDTO;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.web.dto.user.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/bank-accounts/{userId}")
    public ResponseEntity<BankAccountResponseDto> createBankAccountForExistingUser(
            @PathVariable Long userId) {
        BankAccountResponseDto createdBankAccount = bankAccountService.createBankAccountExistedUser(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/bank-accounts/new-user")
    public ResponseEntity<BankAccountResponseDto> createBankAccountForNewUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            BankAccountResponseDto createdBankAccount = bankAccountService.createBankAccountNewUser(userRequestDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
        } catch (UserAlreadyExistsException e) {
            return ApiResponse.badRequest("User with email " + userRequestDTO.email() + " already exists");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/bank-accounts/{bankAccountId}/status")
    public ResponseEntity<BankAccountResponseDto> updateBankAccountStatus(
            @PathVariable Long bankAccountId,
            @RequestParam String status) {
        BankAccountResponseDto updatedBankAccount = bankAccountService.updateBankAccountStatus(bankAccountId, status);
        return ResponseEntity.ok(updatedBankAccount);
    }

    @PostMapping("/user/bank-accounts")
    public ResponseEntity<BankAccountResponseDto> createOwnBankAccount() {
        User currentUser = UserUtils.getCurrentUser();
        BankAccountResponseDto createdBankAccount = bankAccountService.createBankAccount(currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
    }

    @GetMapping("/bank-accounts/user/{userId}")
    public ResponseEntity<List<BankAccountResponseDto>> getBankAccountByUserId(@PathVariable Long userId) {
        User currentUser = UserUtils.getCurrentUser();

        if (!currentUser.getRole().equals(Role.ADMIN) && !currentUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            List<BankAccountResponseDto> userBankAccounts = bankAccountService.getBankAccountsByUserId(userId);
            return ApiResponse.ok(userBankAccounts);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    @GetMapping("/bank-accounts/account/{bankAccountId}")
    public ResponseEntity<BankAccountDetailsDTO> getBankAccountDetails(@PathVariable Long bankAccountId) {
        try {
            return ApiResponse.ok(bankAccountService.findBankAccountDetailsById(bankAccountId));
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

}