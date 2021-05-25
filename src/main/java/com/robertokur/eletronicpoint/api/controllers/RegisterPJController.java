package com.robertokur.eletronicpoint.api.controllers;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robertokur.eletronicpoint.api.dto.RegisterPJDto;
import com.robertokur.eletronicpoint.api.entities.Company;
import com.robertokur.eletronicpoint.api.entities.Employee;
import com.robertokur.eletronicpoint.api.enums.ProfileEnum;
import com.robertokur.eletronicpoint.api.response.Response;
import com.robertokur.eletronicpoint.api.services.CompanyService;
import com.robertokur.eletronicpoint.api.services.EmployeeService;
import com.robertokur.eletronicpoint.api.utils.PasswordUtils;


@RestController
@RequestMapping("/api/register-pj")
@CrossOrigin(origins = "*")
public class RegisterPJController {

	private static final Logger log = LoggerFactory.getLogger(RegisterPJController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CompanyService companyService;

	public RegisterPJController() {
	}

	/**
	 * Insert a PJ on system DB.
	 * 
	 * @param registerPJDto
	 * @param result
	 * @return ResponseEntity<Response<RegisterPJDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<RegisterPJDto>> insert(@Valid @RequestBody RegisterPJDto registerPJDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("insert PJ: {}", registerPJDto.toString());
		Response<RegisterPJDto> response = new Response<RegisterPJDto>();

		validExistingData(registerPJDto, result);
		Company company = this.convertDtoToCompany(registerPJDto);
		Employee employee = this.convertDtoToEmployee(registerPJDto, result);

		if (result.hasErrors()) {
			log.error("Error validating registration data PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.companyService.insert(company);
		employee.setCompany(company);
		this.employeeService.insert(employee);

		response.setData(this.convertRegisterPJDto(employee));
		return ResponseEntity.ok(response);
	}

	/**
	 * Checks whether the company or employee already exists in the database.
	 * 
	 * @param registerPJDto
	 * @param result
	 */
	private void validExistingData(RegisterPJDto registerPJDto, BindingResult result) {
		this.companyService.findByCnpj(registerPJDto.getCnpj())
				.ifPresent(cmp -> result.addError(new ObjectError("company", "Company already exists.")));

		this.employeeService.findByCpf(registerPJDto.getCpf())
				.ifPresent(emp -> result.addError(new ObjectError("employee", "CPF already exists.")));

		this.employeeService.findByEmail(registerPJDto.getEmail())
				.ifPresent(emp -> result.addError(new ObjectError("employee", "Email already exists.")));
	}

	/**
	 * Converts DTO data to company.
	 * 
	 * @param registerPJDto
	 * @return Company
	 */
	private Company convertDtoToCompany(RegisterPJDto registerPJDto) {
		Company company = new Company();
		company.setCnpj(registerPJDto.getCnpj());
		company.setCompanyName(registerPJDto.getCompanyName());

		return company;
	}

	/**
	 * Converts DTO data to employee.
	 * 
	 * @param registerPJDto
	 * @param result
	 * @return Employee
	 * @throws NoSuchAlgorithmException
	 */
	private Employee convertDtoToEmployee(RegisterPJDto registerPJDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName(registerPJDto.getName());
		employee.setEmail(registerPJDto.getEmail());
		employee.setCpf(registerPJDto.getCpf());
		employee.setProfile(ProfileEnum.ROLE_ADMIN);
		employee.setPassword(PasswordUtils.gerarBCrypt(registerPJDto.getPassword()));

		return employee;
	}

	/**
	 * Populates the registration DTO with the employee and company data..
	 * 
	 * @param employee
	 * @return RegisterPJDto
	 */
	private RegisterPJDto convertRegisterPJDto(Employee employee) {
		RegisterPJDto registerPJDto = new RegisterPJDto();
		registerPJDto.setId(employee.getId());
		registerPJDto.setName(employee.getName());
		registerPJDto.setEmail(employee.getEmail());
		registerPJDto.setCpf(employee.getCpf());
		registerPJDto.setCompanyName(employee.getCompany().getCompanyName());
		registerPJDto.setCnpj(employee.getCompany().getCnpj());

		return registerPJDto;
	
	}
}
