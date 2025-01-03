package com.ebankify.api.service.loan;

import com.ebankify.api.entity.Loan;
import com.ebankify.api.entity.enums.LoanStatus;
import com.ebankify.api.repository.LoanRepository;
import com.ebankify.api.service.loanProcessing.LoanProcessingService;
import com.ebankify.api.util.UserUtils;
import com.ebankify.api.web.dto.loan.LoanRequestDTO;
import com.ebankify.api.web.dto.loan.LoanResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanProcessingService loanProcessingService;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, LoanProcessingService loanProcessingService) {
        this.loanRepository = loanRepository;
        this.loanProcessingService = loanProcessingService;
    }


    @Override
    public List<LoanResponseDTO> findAll() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(loan -> LoanResponseDTO.fromLoanAndUser(loan, loan.getUser())).toList();
    }

    @Override
    public LoanResponseDTO findById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        return LoanResponseDTO.fromLoanAndUser(loan, loan.getUser());
    }

    @Override
    public LoanResponseDTO create(LoanRequestDTO loanRequestDTO) {
        Loan loan = loanRequestDTO.toLoan();
        loan.setUser(UserUtils.getCurrentUser());
        loan.setMemberSince(UserUtils.getCurrentUser().getCreatedAt());
        loan.setCreditRating(loanProcessingService.calculateCreditRating(loan));
        loan.setDebtToIncomeRatio(loanProcessingService.calculateDebtToIncomeRatio(loan.getTotalMonthlyDebtPayments(), loan.getMonthlyIncome()));
        loan.setEligibilityCriteria("we will send you an email with the eligibility criteria");
        loan.setStatus(LoanStatus.PENDING);
        Loan createdLoan = loanRepository.save(loan);
        return LoanResponseDTO.fromLoanAndUser(createdLoan, createdLoan.getUser());
    }

    @Override
    public void delete(Long id) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        loanRepository.delete(existingLoan);
    }

    public LoanResponseDTO updateStatus(Long id, LoanStatus status) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        existingLoan.setStatus(status);
        loanRepository.save(existingLoan);
        return LoanResponseDTO.fromLoanAndUser(existingLoan, existingLoan.getUser());
    }
}
