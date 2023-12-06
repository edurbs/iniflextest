package com.iniflex.erp.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iniflex.erp.application.usecases.exceptions.EmployeeNotFoundException;
import com.iniflex.erp.application.usecases.exceptions.NullEmployeeException;
import com.iniflex.erp.domain.entities.Employee;
import com.iniflex.erp.domain.entities.exceptions.IllegalFieldException;
import com.iniflex.erp.infra.listdb.ListDB;


public class RegistryEmployeeServiceTest { 

    private RegistryEmployeeService registryEmployeeService;  
    private ListDB listDB;
    
    @BeforeEach
    void setup() {                
        this.listDB = new ListDB();
        this.registryEmployeeService = new RegistryEmployeeService(listDB);
    }
    
    private Employee employeeFactory() {
        String name = "some name";
        LocalDate birthDate = LocalDate.of(2000,10,18);
        BigDecimal salary = BigDecimal.valueOf(2009.44);
        String position = "some position";
        var employee = new Employee(name, birthDate, salary, position);
        return employee;
    }

    @Test
    void create_employee_with_valid_fields() {

        var employee = employeeFactory();
        
        registryEmployeeService.create(employee);        
          
        List<Employee> employees = registryEmployeeService.findAll();        
        assertTrue(employees.contains(employee));
    }

    @Test
    void delete_employee_successfully_removes_from_repository() {
        var employee = employeeFactory();        
        registryEmployeeService.create(employee);
        
        registryEmployeeService.delete(employee);
        List<Employee> employees = registryEmployeeService.findAll();

        assertFalse(employees.contains(employee));
    }

    @Test
    void find_existing_employee_by_name_returns_correct_employee() {
        var employee = employeeFactory();        
        registryEmployeeService.create(employee);
        
        Employee foundEmployee = registryEmployeeService.findByName(employee.getName());

        assertEquals(employee, foundEmployee);
    }

    @Test
    void delete_non_existing_employee_throws_exception() {        
        var employee = employeeFactory();        

        assertThrows(EmployeeNotFoundException.class, () -> {
            registryEmployeeService.delete(employee);
        });
    }

    @Test
    void delete_with_null_employee_throws_exception() {
        
        assertThrows(NullEmployeeException.class, () -> {
            registryEmployeeService.delete(null);
        });
    }

    @Test
    void find_non_existing_employee_by_name_throws_exception() {        
        var employee = employeeFactory();        
        registryEmployeeService.create(employee);        

        assertThrows(EmployeeNotFoundException.class, () -> {
            registryEmployeeService.findByName("other name");
        });
    }

    @Test
    void findAll_returnsListOfAllEmployees() {
        // Arrange
        var employee1 = employeeFactory();        
        var employee2 = employeeFactory();        
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);

        List<Employee> employees = registryEmployeeService.findAll();

        assertEquals(2, employees.size());
        assertTrue(employees.contains(employee1));
        assertTrue(employees.contains(employee2));
    }

    @Test
    void retrieve_all_employees_from_empty_repository_returns_empty_list() {        
        List<Employee> employees = registryEmployeeService.findAll();

        assertTrue(employees.isEmpty());
    }

    
    @Test
    void update_non_existing_employee_throws_exception() {        
        var employee = employeeFactory();        
        registryEmployeeService.create(employee);        

        assertThrows(EmployeeNotFoundException.class, () -> {
            registryEmployeeService.updateByName("other name", employee);
        });
    }

    @Test
    void update_employee_successfully_updates_in_repository() {
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        var employee = employeeFactory();        
        var oldName = "Old Name";
        employee.setName(oldName);
        registryEmployeeService.create(employee);

        var updatedEmployee = employeeFactory();        
        var newName = "New name";
        updatedEmployee.setName(newName);

        registryEmployeeService.updateByName(oldName, updatedEmployee);
        Employee foundEmployee = registryEmployeeService.findByName(newName);

        assertEquals(updatedEmployee, foundEmployee);
        assertEquals(newName, foundEmployee.getName());
    }

    @Test
    void raise_salary_with_null_employee_throws_exception() {        
        Employee employee = null;
        double percentage = 10;

        assertThrows(NullEmployeeException.class, () -> {
            registryEmployeeService.increaseSalary(employee, percentage);
        });
    }

    @Test
    void raise_salary_with_negative_value_throws_exception() {        
        var employee = employeeFactory();
        double percentage = -10;

        assertThrows(IllegalFieldException.class, () -> {
            registryEmployeeService.increaseSalary(employee, percentage);
        });
    }

    @Test
    void raise_salary_with_valid_parameters() {
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        var employee = employeeFactory();
        var someNewName = "some new name";
        employee.setName(someNewName);
        employee.setSalary(BigDecimal.valueOf(100.0));
        registryEmployeeService.create(employee);        
        double percentage = 10;

        registryEmployeeService.increaseSalary(employee, percentage);

        BigDecimal newSalary = BigDecimal.valueOf(110.0).setScale(2);
        Employee foundEmployee = registryEmployeeService.findByName(someNewName);
        assertEquals(newSalary, foundEmployee.getSalary());
    }

    @Test
    void increase_all_salaries_by_10_percent(){
        var employee1 = employeeFactory();
        employee1.setName("aaa");
        employee1.setSalary(BigDecimal.valueOf(100.0));

        var employee2 = employeeFactory();
        employee2.setSalary(BigDecimal.valueOf(1000.0));
        employee2.setName("bbb");

        var employee3 = employeeFactory();
        employee3.setSalary(BigDecimal.valueOf(10000.0));
        employee3.setName("ccc");

        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        registryEmployeeService.increaseAllSalariesByPercentage(10);

        BigDecimal newSalary1 = BigDecimal.valueOf(110.0).setScale(2);
        BigDecimal newSalary2 = BigDecimal.valueOf(1100.0).setScale(2);
        BigDecimal newSalary3 = BigDecimal.valueOf(11000.0).setScale(2);

        var foundEmployee1 = registryEmployeeService.findByName(employee1.getName());
        var foundEmployee2 = registryEmployeeService.findByName(employee2.getName());
        var foundEmployee3 = registryEmployeeService.findByName(employee3.getName());

        assertEquals(newSalary1, foundEmployee1.getSalary());
        assertEquals(newSalary2, foundEmployee2.getSalary());
        assertEquals(newSalary3, foundEmployee3.getSalary());

    }

    @Test
    void retrieve_all_employees_of_a_position(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        employee1.setPosition("manager");
        employee2.setPosition("saler");
        employee3.setPosition("manager");
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        assertEquals(2, registryEmployeeService.findAllByPosition("manager").size());
        assertEquals(1, registryEmployeeService.findAllByPosition("saler").size());
    }

    @Test
    void retrieve_all_positions(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        employee1.setPosition("manager");
        employee2.setPosition("saler");
        employee3.setPosition("manager");
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        assertEquals(2, registryEmployeeService.findAllPositions().size());
    }

    @Test
    void find_employee_by_birthdate(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        employee1.setBirthDate(LocalDate.of(2000, 1, 4));
        employee2.setBirthDate(LocalDate.of(2001, 2, 5));
        employee3.setBirthDate(LocalDate.of(2002, 3, 6));
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        assertEquals(employee1, registryEmployeeService.findByBirthDate(LocalDate.of(2001, 2, 5)));
    }

    @Test
    void find_employee_by_non_existent_birthdate_throws_exception(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        employee1.setBirthDate(LocalDate.of(2000, 1, 4));
        employee2.setBirthDate(LocalDate.of(2001, 2, 5));
        employee3.setBirthDate(LocalDate.of(2002, 3, 6));
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        assertThrows(EmployeeNotFoundException.class, () -> {
            registryEmployeeService.findByBirthDate(LocalDate.of(2003, 4, 5));
        });
    }

    @Test
    void find_employee_by_month_of_birthdate(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        employee1.setBirthDate(LocalDate.of(2000, 1, 4));
        employee2.setBirthDate(LocalDate.of(2001, 2, 5));
        employee3.setBirthDate(LocalDate.of(2002, 2, 6));
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        assertEquals(2, registryEmployeeService.findAllByBirthMonth(2).size());
    }

    @Test
    void find_employee_with_biggest_age(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        employee1.setBirthDate(LocalDate.of(2000, 1, 4));
        employee2.setBirthDate(LocalDate.of(1960, 2, 5));
        employee3.setBirthDate(LocalDate.of(2002, 3, 6));
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        assertEquals(employee2, registryEmployeeService.findWithBiggestAge());
    }

    @Test
    void find_all_ordered_by_name(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        var employee4 = employeeFactory();
        employee1.setName("bbbbb");
        employee2.setName("aaaaa");
        employee3.setName("ddddd");
        employee4.setName("ccccc");
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);
        registryEmployeeService.create(employee4);

        List<Employee> orderedList = List.of(employee2, employee1, employee4, employee3);
        assertEquals(orderedList, registryEmployeeService.findAllOrderByName());
    }

    @Test
    void get_the_sum_of_all_salaries(){
        var employee1 = employeeFactory();
        var employee2 = employeeFactory();
        var employee3 = employeeFactory();
        employee1.setSalary(BigDecimal.valueOf(100.11));
        employee2.setSalary(BigDecimal.valueOf(200.22));
        employee3.setSalary(BigDecimal.valueOf(300.33));
        registryEmployeeService.create(employee1);
        registryEmployeeService.create(employee2);
        registryEmployeeService.create(employee3);

        assertEquals(BigDecimal.valueOf(600.66), registryEmployeeService.getSumOfSalaries());
    }

  

}
