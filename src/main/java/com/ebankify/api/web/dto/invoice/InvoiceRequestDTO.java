package com.ebankify.api.web.dto.invoice;

import com.ebankify.api.entity.Invoice;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.InvoiceStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;


public record InvoiceRequestDTO(
        @Positive(message = "Amount due must be positive")
        double amountDue,

        @NotNull(message = "Due date is required")
        @FutureOrPresent(message = "Due date must be in the future")
        LocalDate dueDate,

        @NotNull(message = "Email is required")
        @Email(message = "Email must be valid")
        String email
) {
    public Invoice toInvoice(User user) {
        return Invoice.builder()
                .amountDue(this.amountDue)
                .dueDate(this.dueDate)
                .status(InvoiceStatus.UNPAID)
                .user(user)
                .build();
    }
}