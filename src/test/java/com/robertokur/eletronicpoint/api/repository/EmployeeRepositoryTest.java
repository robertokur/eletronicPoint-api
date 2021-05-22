package com.robertokur.eletronicpoint.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.robertokur.eletronicpoint.api.entities.Company;
import com.robertokur.eletronicpoint.api.entities.Employee;
import com.robertokur.eletronicpoint.api.enums.ProfileEnum;
import com.robertokur.eletronicpoint.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test2")

public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CompanyRepository companyRepository;

	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";

	@Before
	public void setUp() throws Exception {
		Company company = this.companyRepository.save(getCompanyData());
		this.employeeRepository.save(getEmployeeData(company));
	}

	@After
	public final void tearDown() {
		this.companyRepository.deleteAll();
	}

	@Test
	public void testFindEmployeeByEmail() {
		Employee employee = this.employeeRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, employee.getEmail());
	}

	@Test
	public void testFindEmployeeByCpf() {
		Employee employee = this.employeeRepository.findByCpf(CPF);

		assertEquals(CPF, employee.getCpf());
	}

	@Test
	public void testFindEmployeeByEmailAndCpf() {
		Employee employee = this.employeeRepository.findByCpfOrEmail(CPF, EMAIL);

		assertNotNull(employee);
	}

	@Test
	public void testFindEmployeeByEmailOrCpfParaEmailInvalid() {
		Employee employee = this.employeeRepository.findByCpfOrEmail(CPF, "email@invalido.com");

		assertNotNull(employee);
	}

	@Test
	public void testFindByEmployeeCpfOrEmailInvalid() {
		Employee employee = this.employeeRepository.findByCpfOrEmail("12345678901", EMAIL);

		assertNotNull(employee);
	}

	private Employee getEmployeeData(Company company) throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName("Fulano de Tal");
		employee.setProfile(ProfileEnum.ROLE_USER);
		employee.setPassword(PasswordUtils.gerarBCrypt("123456"));
		employee.setCpf(CPF);
		employee.setEmail(EMAIL);
		employee.setCompany(company);
		return employee;
	}

	private Company getCompanyData() {
		Company company = new Company();
		company.setCompanyName("Company for example");
		company.setCnpj("51463645000100");
		return company;
	}

}
