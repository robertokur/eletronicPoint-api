package com.robertokur.eletronicpoint.api.services;

import org.springframework.data.domain.PageRequest;

import com.robertokur.eletronicpoint.api.entities.Entry;

import java.util.Optional;

import org.springframework.data.domain.Page;

public interface EntryService {
	/**
	 * Return a list paginable with specific employee.
	 * 
	 * @param employeeId
	 * @param pageRequest
	 * @return Page<Employee>
	 */
	Page<Entry> findEmployeeById(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 * Return a entry for ID.
	 * 
	 * @param id
	 * @return Optional<Entry>
	 */
	Optional<Entry> findById(Long id);
	
	/**
	 * Insert a entry in DB
	 * 
	 * @param entry
	 * @return Entry
	 */
	Entry insert(Entry entry);
	
	/**
	 * Remove a entry fod DB.
	 * 
	 * @param id
	 */
	void remove(Long id);
}
