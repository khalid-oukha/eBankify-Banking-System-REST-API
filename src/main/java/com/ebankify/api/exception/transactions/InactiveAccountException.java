package com.ebankify.api.exception.transactions;

public class InactiveAccountException extends TransactionException {
    public InactiveAccountException(String message) {
        super(message);
    }
}
