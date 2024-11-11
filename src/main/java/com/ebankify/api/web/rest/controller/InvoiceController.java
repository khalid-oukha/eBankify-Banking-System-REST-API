package com.ebankify.api.web.rest.controller;

import com.ebankify.api.repository.InvoiceRepository;
import com.ebankify.api.service.invoice.InvoiceService;
import com.ebankify.api.web.dto.invoice.InvoiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, InvoiceRepository invoiceRepository) {
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
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

    
}
