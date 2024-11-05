package com.ebankify.api.util;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAccountUtils {
    private static BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountUtils(BankAccountRepository bankAccountRepository) {
        BankAccountUtils.bankAccountRepository = bankAccountRepository;
    }

    public static BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
    }
}
