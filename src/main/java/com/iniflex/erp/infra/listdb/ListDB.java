package com.iniflex.erp.infra.listdb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.iniflex.erp.application.repositories.EmployeeRepository;
import com.iniflex.erp.application.usecases.exceptions.EmployeeNotFoundException;
import com.iniflex.erp.domain.entities.Employee;

public class ListDB implements EmployeeRepository {
    
    private List<Employee> listEmployees = new ArrayList<>();
    
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
        return new ArrayList<>(listEmployees);
    }

    @Override
    public void updateByName(String name, Employee newEmployee) {
        Employee employeeToDelete=null;
        for (Employee employee : listEmployees) {
            if(employee.getName().equals(name))  {
                employeeToDelete = employee;
                break;
            }
        }
        if(employeeToDelete == null) {
            throw new EmployeeNotFoundException();
        }
        delete(employeeToDelete);
        save(newEmployee);
    }

    @Override
    public List<Employee> findAllByPosition(String position) {
        List<Employee> foundEmployees = new ArrayList<>();
        for (Employee employee : listEmployees) {
            if (employee.getPosition().equals(position)) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;
    }

    @Override
    public List<String> getAllPositions() {
        List<String> positions = new ArrayList<>();
        for (Employee employee : listEmployees) {
            if (!positions.contains(employee.getPosition())){
                positions.add(employee.getPosition());
            }
        }
        return positions;        
    }

    @Override
    public Employee findByBirthDate(LocalDate birthDate) {
        for (Employee employee : listEmployees) {
            if (employee.getBirthDate().equals(birthDate)) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public List<Employee> findAllByBirthMonth(int month) {
        List<Employee> foundEmployees = new ArrayList<>();
        for (Employee employee : listEmployees) {
            if (employee.getBirthDate().getMonthValue() == month) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;        
    }

    @Override
    public List<Employee> findAllOrderByName() {
        List<Employee> foundEmployees = new ArrayList<>(listEmployees);
        foundEmployees.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        return foundEmployees;
    }

 
}
