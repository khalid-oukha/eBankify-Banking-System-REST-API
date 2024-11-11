package com.ebankify.api.service.invoice;

import com.ebankify.api.web.dto.invoice.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    List<InvoiceResponseDTO> getInvoicesByUser(Long userId);

    InvoiceResponseDTO findById(long id);

    InvoiceResponseDTO payInvoice(long id);

    InvoiceResponseDTO create();

    InvoiceResponseDTO delete(long id);

}
