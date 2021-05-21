package com.robertokur.eletronicpoint.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.robertokur.eletronicpoint.api.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	@Transactional(readOnly = true)
	Company findByCnpj(String cnpj);
}
