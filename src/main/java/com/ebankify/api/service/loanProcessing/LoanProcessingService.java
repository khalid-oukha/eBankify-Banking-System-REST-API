package com.ebankify.api.service.loanProcessing;

import com.ebankify.api.entity.Loan;

import java.time.LocalDateTime;

public interface LoanProcessingService {
    double calculateDebtToIncomeRatio(double totalMonthlyDebtPayments, double monthlyIncome);

    Integer checkEligibility(double debtToIncomeRatio);

    Integer checkMemberSince(LocalDateTime memberSince);

    Integer calculateCreditRating(Loan loan);
}
