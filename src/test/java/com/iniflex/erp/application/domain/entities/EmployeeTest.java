package com.iniflex.erp.application.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.iniflex.erp.domain.entities.Employee;
import com.iniflex.erp.domain.entities.exceptions.EmptyFieldException;
import com.iniflex.erp.domain.entities.exceptions.IllegalFieldException;


public class EmployeeTest {


    @Test
    void shouldCreateEmployeeWithValidFields() {

        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";

        var employee = new Employee(name, birthDate, salary, position);

        Assertions.assertEquals(name, employee.getName());
        Assertions.assertEquals(birthDate, employee.getBirthDate());
        Assertions.assertEquals(salary, employee.getSalary());
        Assertions.assertEquals(position, employee.getPosition());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        String name = null;
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";

        Assertions.assertThrowsExactly(EmptyFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

    @Test
    void shouldThrowExcetionWhenNameIsBlank() {

        String name = "";
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";
        Assertions.assertThrowsExactly(EmptyFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

    @Test
    void shouldThrowExceptionWhenBirthDateIsNull() {
        String name = "some name";
        LocalDate birthDate = null;
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";

        Assertions.assertThrowsExactly(EmptyFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

    @Test
    void shouldThrowExceptionWhenSalaryIsNull() {
        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = null;
        String position = "some position";

        Assertions.assertThrowsExactly(EmptyFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

    @Test
    void shouldThrowExceptionWhenSalaryIsZero() {
        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = new BigDecimal(0);
        String position = "some position";

        Assertions.assertThrowsExactly(IllegalFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

    @Test
    void shouldThrowExceptionWhenSalaryIsNegative() {
        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = new BigDecimal(-1);
        String position = "some position";

        Assertions.assertThrowsExactly(IllegalFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

    @Test
    void shouldThrowExceptionWhenPositionIsNull() {
        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = null;

        Assertions.assertThrowsExactly(EmptyFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

    @Test
    void shouldThrowExcetionWhenPositionIsBlank() {

        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 10, 18);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "";
        Assertions.assertThrowsExactly(EmptyFieldException.class,
                () -> new Employee(name, birthDate, salary, position));
    }

}
