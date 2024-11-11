package com.ebankify.api.service.loanProcessing;

import com.ebankify.api.entity.Loan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanProcessingServiceImpl implements LoanProcessingService {

    public double calculateDebtToIncomeRatio(double totalMonthlyDebtPayments, double monthlyIncome) {
        return (totalMonthlyDebtPayments / monthlyIncome) * 100;
    }

    public Integer checkEligibility(double debtToIncomeRatio) {
        if (debtToIncomeRatio < 30) {
            return 1;
        } else if (debtToIncomeRatio < 40) {
            return 3;
        } else if (debtToIncomeRatio < 50) {
            return 6;
        } else {
            return 9;
        }
    }

    public Integer checkMemberSince(LocalDateTime memberSince) {
        if (memberSince.isAfter(LocalDateTime.now().minusMonths(6))) {
            return 1;
        } else if (memberSince.isAfter(LocalDateTime.now().minusMonths(12))) {
            return 2;
        } else if (memberSince.isAfter(LocalDateTime.now().minusMonths(24))) {
            return 3;
        } else {
            return 4;
        }
    }


    public Integer calculateCreditRating(Loan loan) {
        int creditRating = 0;

        double debtToIncomeRatio = calculateDebtToIncomeRatio(loan.getTotalMonthlyDebtPayments(), loan.getMonthlyIncome());
        creditRating += checkEligibility(debtToIncomeRatio);

        creditRating += checkMemberSince(loan.getMemberSince());

        return creditRating;
    }
}
