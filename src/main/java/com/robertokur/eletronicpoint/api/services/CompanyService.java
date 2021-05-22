package com.robertokur.eletronicpoint.api.services;

import java.util.Optional;

import com.robertokur.eletronicpoint.api.entities.Company;

public interface CompanyService {
	/**
	 * Return a company CNPJ.
	 * 
	 * @param cnpj
	 * @return Optional<Company>
	 */
	Optional<Company> findByCnpj(String cnpj);
	
	/**
	 * Insert a new company in DB.
	 * 
	 * @param company
	 * @return Company
	 */
	Company insert(Company company);
	
}
