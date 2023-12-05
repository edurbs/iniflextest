package com.iniflex.erp.application.repositories;

import java.util.List;

import com.iniflex.erp.domain.entities.Employee;

public interface EmployeeRepository {

    public void save(Employee employee); 
    public void delete(Employee employee);
    public Employee findByName(String name);
    public List<Employee> findAll();
}
