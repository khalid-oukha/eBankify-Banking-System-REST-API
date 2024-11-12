package com.ebankify.api.service.invoice;

import com.ebankify.api.entity.Invoice;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.InvoiceStatus;
import com.ebankify.api.repository.InvoiceRepository;
import com.ebankify.api.service.bankAccount.BankAccountService;
import com.ebankify.api.service.user.UserService;
import com.ebankify.api.util.UserUtils;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.web.dto.invoice.InvoiceRequestDTO;
import com.ebankify.api.web.dto.invoice.InvoiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserService userService;
    private final BankAccountService bankAccountService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserService userService, BankAccountService bankAccountService) {
        this.invoiceRepository = invoiceRepository;
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public List<InvoiceResponseDTO> getInvoicesByUser(Long userId) {
        User user = userService.getUserById(userId).toUser();

        return invoiceRepository.findByUserId(user.getId()).stream()
                .map(InvoiceResponseDTO::fromInvoice)
                .collect(Collectors.toList());
    }

    @Override
    public Invoice findById(long id) {
        return invoiceRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Invoice with id " + id + " not found"));
    }

    @Override
    public InvoiceResponseDTO payInvoice(long id, UUID accountNumber) {
        User user = UserUtils.getCurrentUser();
        Invoice invoice = findById(id);

        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new IllegalStateException("Invoice is already paid.");
        }

        BankAccountResponseDto bankAccount = bankAccountService.getBankAccountsByUserId(user.getId())
                .stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found or doesn't belong to the user."));

        if (bankAccount.getBalance() < invoice.getAmountDue()) {
            throw new IllegalStateException("Insufficient balance to pay the invoice.");
        }

        double newBalance = bankAccount.getBalance() - invoice.getAmountDue();

        bankAccountService.updateBankAccountBalance(bankAccount.getAccountNumber(), newBalance);

        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());

        invoiceRepository.save(invoice);

        return InvoiceResponseDTO.fromInvoice(invoice);
    }

    @Override
    public Invoice create(InvoiceRequestDTO invoiceRequestDTO) {
        User assignedUser = userService.findByEmail(invoiceRequestDTO.email());
        Invoice invoice = invoiceRequestDTO.toInvoice(assignedUser);
        return invoiceRepository.save(invoice);
    }


    @Override
    public Invoice delete(long id) {
        return null;
    }
}
