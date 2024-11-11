package com.ebankify.api.service.invoice;

import com.ebankify.api.entity.User;
import com.ebankify.api.repository.InvoiceRepository;
import com.ebankify.api.service.user.UserService;
import com.ebankify.api.web.dto.invoice.InvoiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserService userService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserService userService) {
        this.invoiceRepository = invoiceRepository;
        this.userService = userService;
    }

    @Override
    public List<InvoiceResponseDTO> getInvoicesByUser(Long userId) {
        User user = userService.getUserById(userId).toUser();

        return invoiceRepository.findByUserId(user.getId()).stream()
                .map(InvoiceResponseDTO::fromInvoice)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceResponseDTO findById(long id) {
        return null;
    }

    @Override
    public InvoiceResponseDTO payInvoice(long id) {
        return null;
    }

    @Override
    public InvoiceResponseDTO create() {
        return null;
    }

    @Override
    public InvoiceResponseDTO delete(long id) {
        return null;
    }
}
