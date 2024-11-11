package com.ebankify.api.web.dto.invoice;

import com.ebankify.api.entity.Invoice;
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
    private boolean paid;
    private LocalDateTime paidAt;

    public static InvoiceResponseDTO fromInvoice(Invoice invoice) {
        Boolean isPaid = (invoice.getPaidAt() != null);
        return InvoiceResponseDTO.builder()
                .id(invoice.getId())
                .amountDue(invoice.getAmountDue())
                .dueDate(invoice.getDueDate())
                .email(invoice.getUser().getEmail())
                .username(invoice.getUser().getUsername())
                .paid(isPaid)
                .paidAt(invoice.getPaidAt())
                .build();
    }
}
