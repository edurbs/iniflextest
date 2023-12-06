package com.iniflex.erp.application.usecases.exceptions;

public class NullEmployeeException extends RuntimeException {

    public NullEmployeeException() {
        super("Employee cannot be null");
    }
    
}
