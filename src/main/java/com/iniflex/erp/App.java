package com.iniflex.erp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iniflex.erp.application.usecases.RegistryEmployeeService;
import com.iniflex.erp.domain.entities.Employee;
import com.iniflex.erp.infra.listdb.ListDB;

public class App {
	private static RegistryEmployeeService registryEmployeeService = new RegistryEmployeeService(new ListDB());

	public static void main(String[] args) {		

		System.out.println("\n3.1 Inserir todos os funcionários, na mesma ordem e informações da tabela acima.");
		addAll();

		System.out.println("\n3.2 Remover o funcionário “João” da lista.");
		removeJoao();

		System.out.println("\n3.3 Imprimir todos os funcionários com todas suas informações, sendo que:");
		System.out.println(" • informação de data deve ser exibido no formato dd/mm/aaaa;");
		System.out.println(" • informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.");
		printFormatted();

		System.out.println("\n3.4 Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.");
		increaseAllSalary();		

		System.out.println("\n3.5 Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.");
		Map<String, List<Employee>> groupByPosition = getGroupByPosition();		

		System.out.println("\n3.6 Imprimir os funcionários, agrupados por função.");
		printByPosition(groupByPosition);

		System.out.println("\n3.8 Imprimir os funcionários que fazem aniversário no mês 10 e 12.");
		printEmployeesWithBirthMonth(10);
		printEmployeesWithBirthMonth(12);

		System.out.println("\n3.9 Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.");
		printEmployeeWithBiggestAge();

		System.out.println("\n3.10 Imprimir a lista de funcionários por ordem alfabética.");
		printEmployeesOrderedByName();

		System.out.println("\n3.11 Imprimir o total dos salários dos funcionários.");
		printSumOfSalaries();


		System.out.println("\n3.12 Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.");

	}

	private static void printSumOfSalaries() {
		BigDecimal totalSalary = registryEmployeeService.getSumOfSalaries();
		System.out.println(totalSalary);
	}

	private static void printEmployeesOrderedByName() {
		List<Employee> foundEmployees = registryEmployeeService.findAllOrderByName();		
		foundEmployees.forEach(employee -> System.out.println(employee.getName()));
	}

	private static void printEmployeeWithBiggestAge() {				
		Employee biggestAge = registryEmployeeService.findWithBiggestAge();
		System.out.println(biggestAge.getName() + " - " + biggestAge.getAge());
	}

	private static void printEmployeesWithBirthMonth(int month) {
		List<Employee> foundEmployees = registryEmployeeService.findAllByBirthMonth(month);
		if(foundEmployees.isEmpty()) {
			System.out.printf("Nenhum funcionário com data de nascimento no mês %s.\n", month);
			return;
		}
		System.out.printf("Funcionarios com data de nascimento no mês %s:\n", month);
		foundEmployees.forEach(employee -> System.out.println(employee.getName()));
	}

	private static void printByPosition(Map<String, List<Employee>> groupByPosition) {
		groupByPosition.forEach((key, value) -> {
			System.out.println(key + ": ");
			value.forEach(employee -> System.out.print(" - "+ employee.getName()));
			System.out.println("");
		});
	}

	private static Map<String, List<Employee>> getGroupByPosition() {
		List<String> positions = registryEmployeeService.findAllPositions();
		Map<String, List<Employee>> groupByPosition = new HashMap<>();
		positions.forEach(position -> {
			registryEmployeeService.findAllByPosition(position).forEach(employee -> {
				if (!groupByPosition.containsKey(position)) {
					groupByPosition.put(position, new ArrayList<>());
				}
				groupByPosition.get(position).add(employee);
			});
		});
		return groupByPosition;
	}

	private static void increaseAllSalary() {
		registryEmployeeService.increaseAllSalariesByPercentage(10);		
	}

	private static void removeJoao() {
		var employeeJoao = registryEmployeeService.findByName("João");
		registryEmployeeService.delete(employeeJoao);
	}

	private static void addAll() {
		List<Employee> firstListEmployees = new ArrayList<>();
		addNames(firstListEmployees);
		firstListEmployees.forEach(registryEmployeeService::create);
	}

	private static void printFormatted() {
		List<Employee> foundEmployees = registryEmployeeService.findAll();
		foundEmployees.forEach(emp -> System.out.println(emp.getName() + " " + emp.getFormatedBirhDate()+ " " +emp.getFormatedSalary()+ " " +emp.getPosition()));
	}

	private static void addNames(List<Employee> listEmployees) {
		listEmployees.add(new Employee("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador"));
		listEmployees.add(new Employee("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador"));
		listEmployees.add(new Employee("Caio", LocalDate.of(1961, 5, 2), BigDecimal.valueOf(9836.14), "Coordenador"));
		listEmployees.add(new Employee("Miguel", LocalDate.of(1988, 10, 14), BigDecimal.valueOf(19119.88), "Diretor"));
		listEmployees.add(new Employee("Alice", LocalDate.of(1995, 1, 05), BigDecimal.valueOf(2234.68), "Recepcionista"));
		listEmployees.add(new Employee("Heitor", LocalDate.of(1999, 11, 19), BigDecimal.valueOf(1582.72), "Operador"));
		listEmployees.add(new Employee("Arthur", LocalDate.of(1993, 3, 31), BigDecimal.valueOf(4071.84), "Contador"));
		listEmployees.add(new Employee("Laura", LocalDate.of(1994, 7, 8), BigDecimal.valueOf(3017.45), "Gerente"));
		listEmployees.add(new Employee("Heloísa", LocalDate.of(2003, 5, 24), BigDecimal.valueOf(1606.85), "Eletricista"));
		listEmployees.add(new Employee("Helena", LocalDate.of(1996, 9, 2), BigDecimal.valueOf(2799.93), "Gerente"));
	}
}
