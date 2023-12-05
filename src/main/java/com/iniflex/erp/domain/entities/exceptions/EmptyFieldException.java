package com.iniflex.erp.domain.entities.exceptions;

public class EmptyFieldException extends RuntimeException{

    public EmptyFieldException(String message) {
        super(message);
    }
}
