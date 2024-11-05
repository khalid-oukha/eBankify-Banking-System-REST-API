package com.ebankify.api.exception.transactions;

public class InvalidTransactionStatusException extends TransactionException {
    public InvalidTransactionStatusException(String message) {
        super(message);
    }
}
