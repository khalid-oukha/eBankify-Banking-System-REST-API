package com.ebankify.api.web.dto.invoice;

import com.ebankify.api.entity.Invoice;
import com.ebankify.api.entity.enums.InvoiceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Setter
@Getter
public class InvoiceResponseDTO {
    private long id;
    private double amountDue;
    private LocalDate dueDate;
    private String email;
    private String username;
    private LocalDateTime paidAt;
    private InvoiceStatus status;

    public static InvoiceResponseDTO fromInvoice(Invoice invoice) {
        return InvoiceResponseDTO.builder()
                .id(invoice.getId())
                .amountDue(invoice.getAmountDue())
                .dueDate(invoice.getDueDate())
                .email(invoice.getUser().getEmail())
                .username(invoice.getUser().getUsername())
                .status(invoice.getStatus())
                .paidAt(invoice.getPaidAt())
                .build();
    }

    public Invoice toInvoice() {
        return Invoice.builder()
                .id(this.id)
                .amountDue(this.amountDue)
                .dueDate(this.dueDate)
                .status(this.status)
                .paidAt(this.paidAt)
                .build();
    }

}
