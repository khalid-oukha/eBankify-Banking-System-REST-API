package com.ebankify.api.web.rest.controller;


import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.service.loan.LoanService;
import com.ebankify.api.web.dto.loan.LoanRequestDTO;
import com.ebankify.api.web.dto.loan.LoanResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        try {
            LoanResponseDTO loanResponseDTO = loanService.create(loanRequestDTO);
            return ApiResponse.created("/api/v1/loans/" + loanResponseDTO.getId(), loanResponseDTO);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
