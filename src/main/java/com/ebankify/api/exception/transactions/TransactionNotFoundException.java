package com.ebankify.api.exception.transactions;

public class TransactionNotFoundException extends TransactionException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
