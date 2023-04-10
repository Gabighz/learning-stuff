package com.exchange.exchangeassets.common.exceptions;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String errorMessage) {
        super(errorMessage);
    }
}
