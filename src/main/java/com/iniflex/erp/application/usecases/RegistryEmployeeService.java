package com.iniflex.erp.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.iniflex.erp.application.repositories.EmployeeRepository;
import com.iniflex.erp.application.usecases.exceptions.EmployeeNotFoundException;
import com.iniflex.erp.application.usecases.exceptions.NullEmployeeException;
import com.iniflex.erp.domain.entities.Employee;
import com.iniflex.erp.domain.entities.exceptions.IllegalFieldException;
import com.iniflex.erp.domain.entities.exceptions.NullFieldException;

public class RegistryEmployeeService {

    private EmployeeRepository employeeRepository;

    public RegistryEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void create(Employee employee) {                
        if(employee == null) {
            throw new NullEmployeeException();
        }
        employeeRepository.save(employee);        
    }

    public void delete(Employee employee) {
        if(employee == null) {
            throw new NullEmployeeException();
        }
        if(findByName(employee.getName()) == null) {
            throw new EmployeeNotFoundException();
        }
        employeeRepository.delete(employee);
    }

    public Employee findByName(String name) {
        if(name == null) {
            throw new NullFieldException("Name cannot be null");
        }
        return employeeRepository.findByName(name);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void updateByName(String name, Employee employee) {
        if(name == null) {
            throw new NullFieldException("Name cannot be null");
        }
        if(employee == null) {
            throw new NullEmployeeException();
        }
        if(findByName(name) == null) {
            throw new EmployeeNotFoundException();
        }
        employeeRepository.updateByName(name, employee);
    }

    public void increaseSalary(Employee employee, double percentage) {
        if(employee == null) {
            throw new NullEmployeeException();
        }
        if(percentage < 0) {
            throw new IllegalFieldException("Percentage cannot be negative");
        }

        var newSalary = employee.getSalary().multiply(BigDecimal.valueOf(percentage/100+1));
        employee.setSalary(newSalary);
        this.updateByName(employee.getName(), employee);
    }

    public void increaseAllSalariesByPercentage(int percentage) {       		
        List<Employee> foundEmployees = employeeRepository.findAll();
        for (int i = 0; i < foundEmployees.size(); i++) {
            var employeeToRaiseSalary = foundEmployees.get(i);
            increaseSalary(employeeToRaiseSalary, percentage);            
        }
				
    }

    public List<Employee> findAllByPosition(String position) {
        if(position == null) {
            throw new NullFieldException("Position cannot be null");
        }
        return employeeRepository.findAllByPosition(position);
    }

    public List<String> findAllPositions() {
        return employeeRepository.getAllPositions();
    }

    public Employee findByBirthDate(LocalDate birthDate) {
        return employeeRepository.findByBirthDate(birthDate);

    }

    public List<Employee> findAllByBirthMonth(int month) {
        return employeeRepository.findAllByBirthMonth(month);
    }

    public Employee findWithBiggestAge() {
        List<Employee> foundEmployees = employeeRepository.findAll();
        Employee biggestAge = foundEmployees.get(0);
		for (int i = 1; i < foundEmployees.size(); i++) {
			LocalDate birthDate = foundEmployees.get(i).getBirthDate();
			if(birthDate.isBefore(biggestAge.getBirthDate())) {
				biggestAge = foundEmployees.get(i);
			}
		}
        return biggestAge;
    }

    public List<Employee> findAllOrderByName(){
        return employeeRepository.findAllOrderByName();
    }

    public BigDecimal getSumOfSalaries() {
        List<Employee> foundEmployees = employeeRepository.findAll();
        BigDecimal totalSalary = BigDecimal.ZERO;
        for (Employee employee : foundEmployees) {
            totalSalary = totalSalary.add(employee.getSalary());
        }
        return totalSalary;
    }



}
