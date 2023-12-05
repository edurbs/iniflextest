package com.iniflex.erp.infra.listdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iniflex.erp.domain.entities.Employee;


public class ListDBTest {

    
    ListDB listDB;

    @BeforeEach
    void setup(){
        this.listDB = new ListDB();
    }

    Employee employeeFabric(){
        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000,10,18);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";

        return new Employee(name, birthDate, salary, position);        
    }
    
    
    @Test
    void testFindByName() {
        var employee = employeeFabric();
        listDB.save(employee);

        var result = listDB.findByName(employee.getName());

        assertEquals(employee, result);
        assertEquals(employee.getName(), result.getName());
        assertEquals(employee.getBirthDate(), result.getBirthDate());
        assertEquals(employee.getSalary(), result.getSalary());
        assertEquals(employee.getPosition(), result.getPosition());
    }

    @Test
    void testSave() {
        var employee = employeeFabric();
        
        listDB.save(employee);

        assertEquals(listDB.findAll().size(), 1);
    }

    @Test
    void testDelete() {
        var employee1 = employeeFabric();
        var employee2 = employeeFabric();
        var name2 = "some name 2";
        employee2.setName(name2);
        var employee3 = employeeFabric();
        employee3.setName("some name 3");

        listDB.save(employee1);
        listDB.save(employee2);
        listDB.save(employee3);        
        var employeeFound = listDB.findByName(name2);
        listDB.delete(employeeFound);

        assertEquals(employeeFound.getName(), name2);
        assertEquals(listDB.findAll().size(), 2);
    }
}
