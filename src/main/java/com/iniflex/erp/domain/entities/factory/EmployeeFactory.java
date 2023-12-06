package com.iniflex.erp.domain.entities.factory;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.iniflex.erp.domain.entities.Employee;

public class EmployeeFactory {
    public static Employee create (String name, int year, int month, int day, double salary, String position) {		
		LocalDate birthDate = LocalDate.of(year, month, day);
		BigDecimal salaryBigDecimal = BigDecimal.valueOf(salary);		
		return new Employee(name, birthDate, salaryBigDecimal, position);
    }
}
