package com.ebankify.api.web.dto.invoice;

import com.ebankify.api.entity.Invoice;
import com.ebankify.api.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class InvoiceRequestDTO {
    @Positive(message = "Amount due must be positive")
    private double amountDue;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the future")
    private LocalDate dueDate;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    private LocalDateTime paidAt;

    public static Invoice toInvoice(InvoiceRequestDTO invoiceRequestDTO, User user) {
        return Invoice.builder()
                .amountDue(invoiceRequestDTO.getAmountDue())
                .dueDate(invoiceRequestDTO.getDueDate())
                .user(user)
                .paidAt(invoiceRequestDTO.getPaidAt())
                .build();
    }
}
