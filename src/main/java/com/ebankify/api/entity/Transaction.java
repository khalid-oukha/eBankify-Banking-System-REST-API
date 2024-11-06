package com.ebankify.api.entity;

import com.ebankify.api.entity.enums.TransactionStatus;
import com.ebankify.api.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "transactions", indexes = {
        @Index(name = "idx_transaction_id", columnList = "id")
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_from_id", nullable = false)
    private BankAccount accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_to_id", nullable = false)
    private BankAccount accountTo;

    private double fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}