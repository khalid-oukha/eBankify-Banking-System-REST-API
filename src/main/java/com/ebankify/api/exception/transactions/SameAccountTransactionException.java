package com.ebankify.api.exception.transactions;

public class SameAccountTransactionException extends TransactionException {
    public SameAccountTransactionException(String message) {
        super(message);
    }
}