package com.robertokur.eletronicpoint.api.services.impl;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robertokur.eletronicpoint.api.entities.Employee;
import com.robertokur.eletronicpoint.api.repository.EmployeeRepository;
import com.robertokur.eletronicpoint.api.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee insert(Employee employee) {
		log.info("Insert employee: {}", employee);
		return this.employeeRepository.save(employee);
	}

	@Override
	public Optional<Employee> findByCpf(String cpf) {
		log.info("Find employee by CPF {}", cpf);
		return Optional.ofNullable(this.employeeRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Employee> findByEmail(String email) {
		log.info("Find employee by email {}", email);
		return Optional.ofNullable(this.employeeRepository.findByEmail(email));
	}

	@Override
	public Optional<Employee> findById(Long id) {
		try {
			
			log.info("find employee by IDl {}", id);
			
			return Optional.ofNullable(this.employeeRepository.findById(id).get());
		}catch (NoSuchElementException e) {
			return Optional.empty();
		}
	}
	

}
