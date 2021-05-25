package com.robertokur.eletronicpoint.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.robertokur.eletronicpoint.api.dto.EntryDto;
import com.robertokur.eletronicpoint.api.entities.Employee;
import com.robertokur.eletronicpoint.api.entities.Entry;
import com.robertokur.eletronicpoint.api.enums.TypeEnum;
import com.robertokur.eletronicpoint.api.response.Response;
import com.robertokur.eletronicpoint.api.services.EmployeeService;
import com.robertokur.eletronicpoint.api.services.EntryService;

@RestController
@RequestMapping("/api/entry")
@CrossOrigin(origins = "*")
public class EntryController {

	private static final Logger log = LoggerFactory.getLogger(EntryController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private EntryService entryService;

	@Autowired
	private EmployeeService employeeService;
	
	public EntryController() {
	}

	/**
	 * Returns the listing of an employee's postings.
	 * 
	 * @param employeeId
	 * @return ResponseEntity<Response<EmployeeDto>>
	 */
	@GetMapping(value = "/employee/{employeeId}")
	public ResponseEntity<Response<Optional<EntryDto>>> listEmployeeById(
			@PathVariable("employeeId") Long employeeId)
{
		log.info("Searching entries by employee ID: {}", employeeId);
		Response<Optional<EntryDto>> response = new Response<Optional<EntryDto>>();

		Optional<Entry> entries = this.entryService.findEmployeeById(employeeId);
		Optional<EntryDto> entryDto = entries.map(entry -> this.convertEntryDto(entry));

		response.setData(entryDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Find entry by ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<LancamentoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<EntryDto>> findById(@PathVariable("id") Long id){
		log.info("Fetching entry by ID: {}", id);
		Response<EntryDto> response = new Response<EntryDto>();
		Optional<Entry> entry = this.entryService.findById(id);

		if (!entry.isPresent()) {
			log.info("Entry not found for ID: {}", id);
			response.getErrors().add("Entry not found for id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.convertEntryDto(entry.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Add new entry.
	 * 
	 * @param entry
	 * @param result
	 * @return ResponseEntity<Response<entryDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<EntryDto>> add(@Valid @RequestBody EntryDto entryDto,
			BindingResult result) throws ParseException {
		log.info("adding entry: {}", entryDto.toString());
		Response<EntryDto> response = new Response<EntryDto>();
		EmployeeValidation(entryDto, result);
		Entry entry = this.convertDtoToEntry(entryDto, result);

		if (result.hasErrors()) {
			log.error("Error validating entry: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		entry = this.entryService.insert(entry);
		response.setData(this.convertEntryDto(entry));
		return ResponseEntity.ok(response);
	}

	/**
	 * Updates the data for a entry.
	 * 
	 * @param id
	 * @param entryDto
	 * @return ResponseEntity<Response<Entry>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<EntryDto>> update(@PathVariable("id") Long id,
			@Valid @RequestBody EntryDto entryDto, BindingResult result) throws ParseException , NoSuchElementException {
		log.info("Updating entry: {}", entryDto.toString());
		Response<EntryDto> response = new Response<EntryDto>();
		EmployeeValidation(entryDto, result);
		entryDto.setId(Optional.of(id));
		Entry entry = this.convertDtoToEntry(entryDto, result);

		if (result.hasErrors()) {
			log.error("Error validating entry: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		entry = this.entryService.insert(entry);
		response.setData(this.convertEntryDto(entry));
		return ResponseEntity.ok(response);
	}

	/**
	 * Removes a posting by ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Entry>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
		Response<String> response = new Response<String>();
		log.info("Deleting entry: {}", id);
		Optional<Entry> entry = this.entryService.findById(id);

		if (!entry.isPresent()) {
			log.info("Error removing due to posting ID: {} being invalid.", id);
			response.getErrors().add("Error removing release. Record not found for id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.entryService.remove(id);
		return ResponseEntity.ok(new Response<String>());
		
	}

	/**
	 * Validates an employee, verifying that it exists and valid in the
	 * system.
	 * 
	 * @param entryDto
	 * @param result
	 */
	private void EmployeeValidation(EntryDto entryDto, BindingResult result) {
		
			if (entryDto.getEmployeeId() == null) {
				result.addError(new ObjectError("employee", "Employee not informed."));
				return;
			}
	
			log.info("Validate employee id {}: ", entryDto.getEmployeeId());
				
				Optional<Employee> employee = this.employeeService.findById(entryDto.getEmployeeId());
		
			if (!employee.isPresent() || employee.isEmpty()) {
				result.addError(new ObjectError("employee", "Employee not found. Nonexistent ID."));
			}
		
	}

	/**
	 * Converts a launch entity to its respective DTO.
	 * 
	 * @param entry
	 * @return EntryDto
	 */
	private EntryDto convertEntryDto(Entry entry) {
		EntryDto entryDto = new EntryDto();
		entryDto.setId(Optional.of(entry.getId()));
		entryDto.setDate(this.dateFormat.format(entry.getDate()));
		entryDto.setType(entry.getType().toString());
		entryDto.setDescription(entry.getDescription());
		entryDto.setLocalization(entry.getLocalization());
		entryDto.setEmployeeId(entry.getEmployee().getId());

		return entryDto;
	}

	/**
	 * Converts a EntryDto to a entry entity.
	 * 
	 * @param entryDto
	 * @param result
	 * @return Entry
	 * @throws ParseException 
	 */
	private Entry convertDtoToEntry(EntryDto entryDto, BindingResult result) throws ParseException {
		Entry entry = new Entry();

		if (entryDto.getId().isPresent()) {
			Optional<Entry> entr = this.entryService.findById(entryDto.getId().get());
			if (entr.isPresent()) {
				entry = entr.get();
			} else {
				result.addError(new ObjectError("entry", "Entry not found."));
			}
		} else {
			entry.setEmployee(new Employee());
			entry.getEmployee().setId(entryDto.getEmployeeId());
		}

		entry.setDescription(entryDto.getDescription());
		entry.setLocalization(entryDto.getLocalization());
		entry.setDate(this.dateFormat.parse(entryDto.getDate()));

		if (EnumUtils.isValidEnum(TypeEnum.class, entryDto.getType())) {
			entry.setType(TypeEnum.valueOf(entryDto.getType()));
		} else {
			result.addError(new ObjectError("type", "Invalid type."));
		}

		return entry;
	}

}


