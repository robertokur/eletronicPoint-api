package com.robertokur.eletronicpoint.api.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

public class EntryDto {

	private Optional<Long> id = Optional.empty();
	private String date;
	private String type;
	private String description;
	private String localization;
	private Long employeeId;

	public EntryDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	@NotEmpty(message = "Date must not be empty")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocalization() {
		return localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "EntryDto [id=" + id + ", date=" + date + ", type=" + type + ", description=" + description
				+ ", localization=" + localization + ", employeeId=" + employeeId + "]";
	}

}
