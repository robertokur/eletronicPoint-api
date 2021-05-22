package com.robertokur.eletronicpoint.api.services.impl;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robertokur.eletronicpoint.api.entities.Company;
import com.robertokur.eletronicpoint.api.repository.CompanyRepository;
import com.robertokur.eletronicpoint.api.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	@Autowired
	private CompanyRepository companyRepository;
	@Override
	public Optional<Company> findByCnpj(String cnpj) {
		log.info("Find a company by CNPJ {}", cnpj);
		return Optional.ofNullable(companyRepository.findByCnpj(cnpj));
	}
	@Override
	public Company insert(Company company) {
		log.info("Insert company : {}", company);
		return this.companyRepository.save(company);
	}

}
