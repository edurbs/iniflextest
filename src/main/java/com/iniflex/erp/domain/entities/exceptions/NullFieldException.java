package com.iniflex.erp.domain.entities.exceptions;

public class NullFieldException extends RuntimeException{

    public NullFieldException(String message) {
        super(message);
    }
}
