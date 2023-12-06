package com.iniflex.erp.application.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.iniflex.erp.domain.entities.Employee;
import com.iniflex.erp.domain.entities.exceptions.EmptyFieldException;
import com.iniflex.erp.domain.entities.exceptions.IllegalFieldException;


public class EmployeeTest {

    
    private Employee employeeFactory() {
        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 1, 2);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";
        var employee = new Employee(name, birthDate, salary, position);
        return employee;
    }


    @Test
    void shouldCreateEmployeeWithValidFields() {

        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000, 1, 2);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";

        var employee = employeeFactory();

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

    @Test
    void shouldReturnFormatedBirthDate() {
        var employee = employeeFactory();
        Assertions.assertEquals("02/01/2000", employee.getFormatedBirhDate());
    }


    @Test
    void shouldReturnFormatedSalary() {
        var employee = employeeFactory();
        Assertions.assertEquals("2.009,44", employee.getFormatedSalary());
    }

    @Test
    void get_age(){
        var employee1 = employeeFactory();        
        employee1.setBirthDate(LocalDate.of(2000, 1, 4));                
        
        assertEquals(23, employee1.getAge());
    }

}
