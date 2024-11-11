package com.ebankify.api.web.dto.loan;

import com.ebankify.api.entity.Loan;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoanRequestDTO(

        @NotNull(message = "Loan amount must not be null")
        @Positive(message = "Loan amount must be greater than 0")
        @DecimalMin(value = "1000.00", message = "Loan amount must be at least 1000.00") // Example minimum loan amount
        Double amount,

        @NotNull(message = "Loan duration must not be null")
        @Min(value = 6, message = "Loan duration must be at least 6 months")
        Integer duration,

        @NotNull(message = "Monthly income must not be null")
        @Positive(message = "Monthly income must be greater than 0")
        @DecimalMin(value = "500.00", message = "Monthly income must be at least 500.00") // Example minimum monthly income
        double monthlyIncome,

        @NotNull(message = "Total monthly debt payments must not be null")
        @Positive(message = "Total monthly debt payments must be greater than 0")
        @DecimalMin(value = "0.00", message = "Total monthly debt payments must be at least 0.00")
        double totalMonthlyDebtPayments
) {
    public Loan toLoan() {
        return Loan.builder()
                .amount(this.amount)
                .duration(this.duration)
                .monthlyIncome(this.monthlyIncome)
                .totalMonthlyDebtPayments(this.totalMonthlyDebtPayments)
                .build();
    }
}
