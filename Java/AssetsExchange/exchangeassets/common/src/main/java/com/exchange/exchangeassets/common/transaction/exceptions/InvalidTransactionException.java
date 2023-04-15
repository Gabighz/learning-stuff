package com.exchange.exchangeassets.common.transaction.exceptions;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String errorMessage) {
        super(errorMessage);
    }
}
