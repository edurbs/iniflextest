package com.iniflex.erp.domain.entities;

import java.time.LocalDate;

import com.iniflex.erp.domain.entities.exceptions.EmptyFieldException;

import lombok.Data;

@Data
public class Person {

    protected String name;
    protected LocalDate birthDate;

    public Person(String name, LocalDate birthDate) {
        if (name == null || name.isBlank()) {
            throw new EmptyFieldException("Name cannot be null or empty");
        }
        if (birthDate == null) {
            throw new EmptyFieldException("Birth date cannot be null");
        }
        this.name = name;
        this.birthDate = birthDate;
    }

}
