package com.robertokur.eletronicpoint.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robertokur.eletronicpoint.api.dto.RegisterPFDto;
import com.robertokur.eletronicpoint.api.entities.Company;
import com.robertokur.eletronicpoint.api.entities.Employee;
import com.robertokur.eletronicpoint.api.enums.ProfileEnum;
import com.robertokur.eletronicpoint.api.response.Response;
import com.robertokur.eletronicpoint.api.services.CompanyService;
import com.robertokur.eletronicpoint.api.services.EmployeeService;
import com.robertokur.eletronicpoint.api.utils.PasswordUtils;

@Controller
@RestController
@RequestMapping("/api/register-pf")
@CrossOrigin(origins = "*")
public class RegisterPFController {
	private static final Logger log = LoggerFactory.getLogger(RegisterPFController.class);
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private EmployeeService employeeService;
	
	
	public RegisterPFController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Register a employee PF on system.
	 * 
	 * @param registerPFDto
	 * @param result
	 * @return ResponseEntity<Response<RegisterPFDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<RegisterPFDto>> insert(@Valid @RequestBody RegisterPFDto registerPFDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Inserting PF: {}", registerPFDto.toString());
		Response<RegisterPFDto> response = new Response<RegisterPFDto>();
		
		validIfExistsData(registerPFDto, result);
		
		Employee employee = this.convertDTOToEmployee(registerPFDto, result);

		if (result.hasErrors()) {
			log.error("Error when validating data on PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Company> company = this.companyService.findByCnpj(registerPFDto.getCnpj());
		company.ifPresent(comp -> employee.setCompany(comp));
		this.employeeService.insert(employee);

		response.setData(this.convertRegisterPFDto(employee));
		return ResponseEntity.ok(response);
	}

	/**
	 * Check if exists company and employee in db
	 * 
	 * @param registerPFDto
	 * @param result
	 */
	private void validIfExistsData(RegisterPFDto registerPFDto, BindingResult result) {
		Optional<Company> company = this.companyService.findByCnpj(registerPFDto.getCnpj());
		if (!company.isPresent()) {
			result.addError(new ObjectError("company", "Company not found."));
		}
		
		this.employeeService.findByCpf(registerPFDto.getCpf())
			.ifPresent(empl -> result.addError(new ObjectError("employee", "CPF already exists.")));

		this.employeeService.findByEmail(registerPFDto.getEmail())
			.ifPresent(empl -> result.addError(new ObjectError("employee", "Email already exists.")));
	}

	/**
	 * Convert data to DTO employee.
	 * 
	 * @param registerPFDto
	 * @param result
	 * @return Employee
	 * @throws NoSuchAlgorithmException
	 */
	private Employee convertDTOToEmployee(RegisterPFDto registerPFDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName(registerPFDto.getName());
		employee.setEmail(registerPFDto.getEmail());
		employee.setCpf(registerPFDto.getCpf());
		employee.setProfile(ProfileEnum.ROLE_USER);
		employee.setPassword(PasswordUtils.gerarBCrypt(registerPFDto.getPassword()));
		registerPFDto.getQtdHoursLunch()
				.ifPresent(qtdHoursLunch -> employee.setQtdHoursLunch(Float.valueOf(qtdHoursLunch)));
		registerPFDto.getQtdHoursWorkDay()
				.ifPresent(qtdHoursWorkDay -> employee.setQtdWorkHourDay(Float.valueOf(qtdHoursWorkDay)));
		registerPFDto.getHourValue().ifPresent(hourValue -> employee.sethourValue(new BigDecimal(hourValue)));

		return employee;
	}

	/**
	 * Insert a DTO for employee and company.
	 * 
	 * @param employee
	 * @return RegisterPFDto
	 */
	private RegisterPFDto convertRegisterPFDto(Employee employee) {
		RegisterPFDto registerPFDto = new RegisterPFDto();
		registerPFDto.setId(employee.getId());
		registerPFDto.setName(employee.getName());
		registerPFDto.setEmail(employee.getEmail());
		registerPFDto.setCpf(employee.getCpf());
		registerPFDto.setCnpj(employee.getCompany().getCnpj());
		employee.getQtdHoursLunchOpt().ifPresent(qtdHorasLunch -> registerPFDto
				.setQtdHoursLunch(Optional.of(Float.toString(qtdHorasLunch))));
		employee.getQtdWorkHourDayOpt().ifPresent(
				qtdHoursWorkDay -> registerPFDto.setQtdHoursWorkDay(Optional.of(Float.toString(qtdHoursWorkDay))));
		employee.getHourValueOpt()
				.ifPresent(hourValue -> registerPFDto.setHourValue(Optional.of(hourValue.toString())));

		return registerPFDto;
	}

}
