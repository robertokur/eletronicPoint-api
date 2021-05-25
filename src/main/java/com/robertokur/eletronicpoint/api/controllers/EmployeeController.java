package com.robertokur.eletronicpoint.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robertokur.eletronicpoint.api.dto.EmployeeDto;
import com.robertokur.eletronicpoint.api.entities.Employee;
import com.robertokur.eletronicpoint.api.response.Response;
import com.robertokur.eletronicpoint.api.services.EmployeeService;
import com.robertokur.eletronicpoint.api.utils.PasswordUtils;



@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	public EmployeeController() {
	}

	/**
	 * Updates an employee's data.
	 * 
	 * @param id
	 * @param employeeDto
	 * @param result
	 * @return ResponseEntity<Response<FuncionarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<EmployeeDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody EmployeeDto employeeDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Updating employee: {}", employeeDto.toString());
		Response<EmployeeDto> response = new Response<EmployeeDto>();

		Optional<Employee> employee = this.employeeService.findById(id);
		if (!employee.isPresent()) {
			result.addError(new ObjectError("employee", "Employee not found."));
		}

		this.updateDataEmployee(employee.get(), employeeDto, result);

		if (result.hasErrors()) {
			log.error("Error validating employee: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.employeeService.insert(employee.get());
		response.setData(this.convertEmployeeDto(employee.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Updates employee data based on data found in the DTO.
	 * 
	 * @param employee
	 * @param employeeDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void updateDataEmployee(Employee employee, EmployeeDto employeeDto, BindingResult result)
			throws NoSuchAlgorithmException {
		employee.setName(employeeDto.getName());

		if (!employee.getEmail().equals(employeeDto.getEmail())) {
			this.employeeService.findByEmail(employeeDto.getEmail())
					.ifPresent(func -> result.addError(new ObjectError("email", "Email already exists.")));
			employee.setEmail(employeeDto.getEmail());
		}

		employee.setQtdHoursLunch(null);
		employeeDto.getQtdHoursLunch()
				.ifPresent(qtdHoursLunch -> employee.setQtdHoursLunch(Float.valueOf(qtdHoursLunch)));

		employee.setQtdWorkHourDay(null);
		employeeDto.getQtdHoursWorkDay()
				.ifPresent(qtdHoursWorkDay -> employee.setQtdWorkHourDay(Float.valueOf(qtdHoursWorkDay)));

		employee.sethourValue(null);
		employeeDto.getHourValue().ifPresent(hourValue -> employee.sethourValue(new BigDecimal(hourValue)));

		if (employeeDto.getPassword().isPresent()) {
			employee.setPassword(PasswordUtils.gerarBCrypt(employeeDto.getPassword().get()));
		}
	}

	/**
	 * Returns a DTO with an employee's data.
	 * 
	 * @param employee
	 * @return EmployeeDto
	 */
	private EmployeeDto convertEmployeeDto(Employee employee) {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setEmail(employee.getEmail());
		employeeDto.setName(employee.getName());
		employee.getQtdHoursLunchOpt().ifPresent(
				qtdHoursLunch -> employeeDto.setQtdHoursLunch(Optional.of(Float.toString(qtdHoursLunch))));
		employee.getQtdWorkHourDayOpt().ifPresent(
				qtdHoursWorkDay-> employeeDto.setQtdHoursWorkDay(Optional.of(Float.toString(qtdHoursWorkDay))));
		employee.getHourValueOpt()
				.ifPresent(hourValue -> employeeDto.setHourValue(Optional.of(hourValue.toString())));

		return employeeDto;
	}

}

