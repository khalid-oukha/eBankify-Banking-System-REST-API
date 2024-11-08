package com.ebankify.api.web.dto.loan;


import com.ebankify.api.entity.Loan;
import com.ebankify.api.entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoanResponseDTO {
    private Long id;
    private Double amount;
    private Integer duration;
    private double monthlyIncome;
    private double debtToIncomeRatio;
    private String eligibilityCriteria;
    private String status;
    private String username;
    private String email;

    public static LoanResponseDTO fromLoanAndUser(Loan loan, User user) {
        return LoanResponseDTO.builder()
                .id(loan.getId())
                .amount(loan.getAmount())
                .duration(loan.getDuration())
                .monthlyIncome(loan.getMonthlyIncome())
                .debtToIncomeRatio(loan.getDebtToIncomeRatio())
                .eligibilityCriteria(loan.getEligibilityCriteria())
                .status(loan.getStatus().name())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
