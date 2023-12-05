package com.iniflex.erp.application.usecases;

import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iniflex.erp.domain.entities.Employee;
import com.iniflex.erp.infra.listdb.ListDB;

@ExtendWith(MockitoExtension.class)
public class RegistryEmployeeServiceTest {

    @InjectMocks
    private RegistryEmployeeService registryEmployeeService;    

    @Mock
    private ListDB listDB;

    
    void setup() {        
        MockitoAnnotations.openMocks(this);        
    }

    @Test
    void shouldCreateEmployeeWithValidFields() {

        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000,10,18);
        BigDecimal salary = new BigDecimal(2009.44);
        String position = "some position";

        var employee = new Employee(name, birthDate, salary, position);
        registryEmployeeService.create(employee);
        
        verify(listDB).save(employee);

     
    }

}
