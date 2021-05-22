package com.robertokur.eletronicpoint.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.robertokur.eletronicpoint.api.entities.Employee;
import com.robertokur.eletronicpoint.api.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test2")
public class EmployeeServiceTest {
	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.employeeRepository.save(Mockito.any(Employee.class))).willReturn(new Employee());
		BDDMockito.given(this.employeeRepository.findByEmail(Mockito.anyString())).willReturn(new Employee());
		BDDMockito.given(this.employeeRepository.findByCpf(Mockito.anyString())).willReturn(new Employee());
	}

	@Test
	public void testInsertEmployee() {
		Employee employee = this.employeeService.insert(new Employee());

		assertNotNull(employee);
	}

	@Test
	public void testFindEmployeeByEmail() {
		Optional<Employee> employee = this.employeeService.findByEmail("email@email.com");

		assertTrue(employee.isPresent());
	}

	@Test
	public void testFindEmployeeByCpf() {
		Optional<Employee> employee = this.employeeService.findByCpf("24291173474");

		assertTrue(employee.isPresent());
	}
	

}
