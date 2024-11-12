package com.ebankify.api.web.rest.controller;

import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.service.invoice.InvoiceService;
import com.ebankify.api.web.dto.invoice.InvoiceRequestDTO;
import com.ebankify.api.web.dto.invoice.InvoiceResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByUser(@PathVariable Long userId) {
        try {
            List<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByUser(userId);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoicesById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(InvoiceResponseDTO.fromInvoice(invoiceService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> create(@Valid @RequestBody InvoiceRequestDTO invoiceRequestDTO) {
        try {
            InvoiceResponseDTO invoiceResponseDTO = InvoiceResponseDTO.fromInvoice(invoiceService.create(invoiceRequestDTO));
            return ApiResponse.created("/invoices" + invoiceResponseDTO.getId(), invoiceResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{invoiceId}/pay/{accountNumber}")
    public ResponseEntity<InvoiceResponseDTO> payInvoice(
            @PathVariable Long invoiceId,
            @PathVariable UUID accountNumber) {

        InvoiceResponseDTO paidInvoice = invoiceService.payInvoice(invoiceId, accountNumber);
        return ResponseEntity.ok(paidInvoice);
    }
}
