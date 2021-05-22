package com.robertokur.eletronicpoint.api.services;

import java.util.Optional;

import com.robertokur.eletronicpoint.api.entities.Employee;

public interface EmployeeService {
	/**
	 * Insert a employee in DB.
	 * 
	 * @param employee
	 * @return Employee
	 */
	Employee insert(Employee employee);
	
	/**
	 * Find and return a employee for a specific CPF.
	 * 
	 * @param cpf
	 * @return Optional<Employee>
	 */
	Optional<Employee> findByCpf(String cpf);
	
	/**
	 * Find and return employee for a specific email.
	 * 
	 * @param email
	 * @return Optional<Employee>
	 */
	Optional<Employee> findByEmail(String email);
	
	/**
	 *  Find and return employee for a specific ID.
	 * 
	 * @param id
	 * @return Optional<Employee>
	 */
	Optional<Employee> findById(Long id);
	
	
}
