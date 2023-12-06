package com.iniflex.erp.domain.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

import com.iniflex.erp.domain.entities.exceptions.EmptyFieldException;
import com.iniflex.erp.domain.entities.exceptions.IllegalFieldException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Employee extends Person {

    private BigDecimal salary;

    private String position;

    public Employee(String name, LocalDate birthDate, BigDecimal salary, String position) {
        super(name, birthDate);
        if (salary == null) {
            throw new EmptyFieldException("Salary cannot be null");
        }
        if (salary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalFieldException("Salary cannot be negative");
        } else if (salary.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalFieldException("Salary cannot be zero");
        }
        if (position == null || position.isBlank()) {
            throw new EmptyFieldException("Position cannot be null or empty");
        }
        this.salary = salary;
        this.position = position;
    }

    public String getFormatedSalary() {        
        var brazilianFormat = NumberFormat.getInstance(new Locale("pt", "BR"));        
        return brazilianFormat.format(salary.setScale(2, RoundingMode.HALF_UP));
    }

    public String getFormatedBirhDate(){
        
        var day = String.format("%02d", birthDate.getDayOfMonth());
        var month = String.format("%02d", birthDate.getMonthValue());

        return day + "/" + month + "/" + birthDate.getYear();
    }

    public int getAge() {
        var today = LocalDate.now();
        Period age = Period.between(birthDate, today);
        return age.getYears();
    }
}
