package com.robertokur.eletronicpoint.api.services;

import com.robertokur.eletronicpoint.api.entities.Entry;

import java.util.Optional;

public interface EntryService {
	/**
	 * Return a list specific employee.
	 * 
	 * @param employeeId
	 * @param Optional<Entry>
	 * @return Optional<Employee>
	 */
	Optional<Entry> findEmployeeById(Long employeeId);
	
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
	 * Remove a entry for DB.
	 * 
	 * @param id
	 */
	void remove(Long id);
}
