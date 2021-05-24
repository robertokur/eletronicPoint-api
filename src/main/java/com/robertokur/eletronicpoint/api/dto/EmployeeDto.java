package com.robertokur.eletronicpoint.api.dto;


import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeDto {

	private Long id;
	private String name;
	private String email;
	private Optional<String> password = Optional.empty();
	private Optional<String> hourValue = Optional.empty();
	private Optional<String> qtdHoursWorkDay = Optional.empty();
	private Optional<String> qtdHoursLunch = Optional.empty();

	public EmployeeDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Name cannot be empty")
	@Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotEmpty(message = "Email cannot be empty.")
	@Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters.")
	@Email(message = "Email inválido.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Optional<String> getPassword() {
		return password;
	}

	public void setPassword(Optional<String> password) {
		this.password = password;
	}

	public Optional<String> getHourValue() {
		return hourValue;
	}

	public void setHourValue(Optional<String> hourValue) {
		this.hourValue = hourValue;
	}

	public Optional<String> getQtdHoursWorkDay() {
		return qtdHoursWorkDay;
	}

	public void setQtdHoursWorkDay(Optional<String> qtdHoursWorkDay) {
		this.qtdHoursWorkDay = qtdHoursWorkDay;
	}

	public Optional<String> getQtdHoursLunch() {
		return qtdHoursLunch;
	}

	public void setQtdHoursLunch(Optional<String> qtdHoursLunch) {
		this.qtdHoursLunch = qtdHoursLunch;
	}

	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", hourValue="
				+ hourValue + ", qtdHoursWorkDay=" + qtdHoursWorkDay + ", qtdHoursLunch=" + qtdHoursLunch + "]";
	}

}
