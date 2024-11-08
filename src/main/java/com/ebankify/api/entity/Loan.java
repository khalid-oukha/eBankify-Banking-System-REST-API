package com.ebankify.api.entity;

import com.ebankify.api.entity.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loans", indexes = {
        @Index(name = "idx_loan_id", columnList = "id")
})
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "monthly_income", nullable = false)
    private double monthlyIncome;

    @Column(name = "debt_to_income_ratio", nullable = false)
    private double debtToIncomeRatio;

    @Column(name = "member_since", nullable = false)
    private LocalDateTime memberSince;

    private String eligibilityCriteria;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
