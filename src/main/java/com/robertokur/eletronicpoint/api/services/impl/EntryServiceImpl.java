package com.robertokur.eletronicpoint.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.robertokur.eletronicpoint.api.entities.Entry;
import com.robertokur.eletronicpoint.api.repository.EntryRepository;
import com.robertokur.eletronicpoint.api.services.EntryService;

@Service
public class EntryServiceImpl implements EntryService {
	private static final Logger log = LoggerFactory.getLogger(EntryServiceImpl.class);
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Override
	public Page<Entry> findEmployeeById(Long employeeId, PageRequest pageRequest) {
		log.info("Find entry by employee ID {}", employeeId);
		return this.entryRepository.findByEmployeeId(employeeId, pageRequest);
	}

	@Override
	@Cacheable("entryById")
	public Optional<Entry> findById(Long id) {
		log.info("Find entry by ID {}", id);
		return Optional.ofNullable(this.entryRepository.findById(id).get());
	}

	@Override
	@CachePut("entryById")
	public Entry insert(Entry entry) {
		log.info("Insert the entry {}", entry);
		return this.entryRepository.save(entry);
	}

	@Override
	public void remove(Long id) {
		log.info("Remove entry by ID {}", id);
		this.entryRepository.deleteById(id);
	}
		
}


