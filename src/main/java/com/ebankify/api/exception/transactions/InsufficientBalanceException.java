package com.ebankify.api.exception.transactions;

public class InsufficientBalanceException extends TransactionException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}