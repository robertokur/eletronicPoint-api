package com.robertokur.eletronicpoint.api.dto;



public class CompanyDto {

	private Long id;
	private String companyName;
	private String cnpj;

	public CompanyDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CompanyDto [id=" + id + ", companyName=" + companyName + ", cnpj=" + cnpj + "]";
	}

}
