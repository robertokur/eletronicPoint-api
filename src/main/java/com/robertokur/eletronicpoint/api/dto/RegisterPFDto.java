package com.robertokur.eletronicpoint.api.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class RegisterPFDto {


	private Long id;
	private String name;
	private String email;
	private String password;
	private String cpf;
	private Optional<String> hourValue = Optional.empty();
	private Optional<String> qtdHoursWorkDay = Optional.empty();
	private Optional<String> qtdHoursLunch = Optional.empty();
	private String cnpj;

	public RegisterPFDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Name must not be empty.")
	@Length(min = 3, max = 200, message = "must contain between 3 and 200 characters.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotEmpty(message = "Email must not be empty.")
	@Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters.")
	@Email(message = "Email inválido.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "password  must not be empty.")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty(message = "CPF must not be empty.")
	@CPF(message = "CPF invalid")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	@NotEmpty(message = "CNPJ must not be empty.")
	@CNPJ(message = "CNPJ invalid.")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "RegisterPFDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", cpf="
				+ cpf + ", hourValue=" + hourValue + ", qtdHoursWorkDay=" + qtdHoursWorkDay + ", qtdHoursLunch="
				+ qtdHoursLunch + ", cnpj=" + cnpj + "]";
	}
}
