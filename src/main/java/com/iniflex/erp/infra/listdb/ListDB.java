package com.iniflex.erp.infra.listdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.iniflex.erp.application.repositories.EmployeeRepository;
import com.iniflex.erp.domain.entities.Employee;
import com.iniflex.erp.infra.listdb.exceptions.EmployeeNotFoundException;

public class ListDB implements EmployeeRepository {
    
    private static List<Employee> listEmployees = new ArrayList<>();
    
    @Override
    public void save(Employee employee) {
        listEmployees.add(employee);
    }

    @Override
    public void delete(Employee employee) {
        listEmployees.remove(employee);
    }

    @Override
    public Employee findByName(String name) {
        for (Employee employee : listEmployees) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public List<Employee> findAll() {        
        return Collections.unmodifiableList(listEmployees);
    }

 
}
