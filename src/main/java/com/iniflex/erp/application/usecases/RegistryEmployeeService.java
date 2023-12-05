package com.iniflex.erp.application.usecases;

import java.util.List;

import com.iniflex.erp.application.repositories.EmployeeRepository;
import com.iniflex.erp.domain.entities.Employee;

public class RegistryEmployeeService {

    private EmployeeRepository employeeRepository;

    public RegistryEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void create(Employee employee) {                
        employeeRepository.save(employee);        
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public Employee findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

}
