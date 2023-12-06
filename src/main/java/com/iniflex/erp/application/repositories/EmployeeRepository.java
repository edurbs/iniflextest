package com.iniflex.erp.application.repositories;

import java.time.LocalDate;
import java.util.List;

import com.iniflex.erp.domain.entities.Employee;

public interface EmployeeRepository {

    public void save(Employee employee); 
    public void delete(Employee employee);
    public Employee findByName(String name);
    public List<Employee> findAll();
    public void updateByName (String name, Employee employee);
    public List<Employee> findAllByPosition(String position);
    public List<String> getAllPositions();
    public Employee findByBirthDate(LocalDate birthDate);
    public List<Employee> findAllByBirthMonth(int month);
    public List<Employee> findAllOrderByName();
}
