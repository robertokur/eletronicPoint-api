package com.robertokur.eletronicpoint.api.controllers;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robertokur.eletronicpoint.api.services.CompanyService;
import com.robertokur.eletronicpoint.api.dto.CompanyDto;
import com.robertokur.eletronicpoint.api.entities.Company;
import com.robertokur.eletronicpoint.api.response.Response;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*")
public class CompanyController {

	private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;

	public CompanyController() {
	}

	/**
	 * Returns a company given a CNPJ.
	 * 
	 * @param cnpj
	 * @return ResponseEntity<Response<CompanyDto>>
	 */
	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<CompanyDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		log.info("Searching company by CNPJ: {}", cnpj);
		Response<CompanyDto> response = new Response<CompanyDto>();
		Optional<Company> company = companyService.findByCnpj(cnpj);

		if (!company.isPresent()) {
			log.info("Company not found for CNPJ: {}", cnpj);
			response.getErrors().add("Company not found for CNPJ " + cnpj);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.convertCompanyDto(company.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Populates a DTO with company data.
	 * 
	 * @param company
	 * @return CompanyDto
	 */
	private CompanyDto convertCompanyDto(Company company) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setId(company.getId());
		companyDto.setCnpj(company.getCnpj());
		companyDto.setCompanyName(company.getCompanyName());
		return companyDto;
	}

}
