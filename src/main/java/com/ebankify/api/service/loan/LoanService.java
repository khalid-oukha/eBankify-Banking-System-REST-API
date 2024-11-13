package com.ebankify.api.service.loan;

import com.ebankify.api.entity.enums.LoanStatus;
import com.ebankify.api.web.dto.loan.LoanRequestDTO;
import com.ebankify.api.web.dto.loan.LoanResponseDTO;

import java.util.List;

public interface LoanService {
    List<LoanResponseDTO> findAll();

    LoanResponseDTO findById(Long id);

    LoanResponseDTO create(LoanRequestDTO loanRequestDTO);

    void delete(Long id);

    LoanResponseDTO updateStatus(Long id, LoanStatus status);
}
