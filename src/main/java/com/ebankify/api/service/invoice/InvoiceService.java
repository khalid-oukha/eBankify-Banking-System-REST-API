package com.ebankify.api.service.invoice;

import com.ebankify.api.entity.Invoice;
import com.ebankify.api.web.dto.invoice.InvoiceRequestDTO;
import com.ebankify.api.web.dto.invoice.InvoiceResponseDTO;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    List<InvoiceResponseDTO> getInvoicesByUser(Long userId);

    Invoice findById(long id);

    Invoice create(InvoiceRequestDTO invoiceRequestDTO);

    Invoice delete(long id);

    InvoiceResponseDTO payInvoice(long id, UUID accountNumber);

}
