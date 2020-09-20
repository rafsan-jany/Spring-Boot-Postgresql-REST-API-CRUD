package com.dbl.nsl.erp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ForbiddenException;
import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.models.Location;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.CompanyRepository;
import com.dbl.nsl.erp.repository.LocationRepository;

@RestController
//@RequestMapping("/api/test")
public class CompanyController {
	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	LocationRepository locationRepository;

	@PostMapping("/company/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> employeeCompany(@RequestBody Company company) {
		if (companyRepository.existsByName(company.getName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Company already exists"));
		}
		companyRepository.save(company);
		return ResponseEntity.ok(new MessageResponse("Company registered successfully"));
	}

	@GetMapping("/company/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Company> getCompany(@PathVariable(value = "id") Long companyId)
			throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));
		return ResponseEntity.ok().body(company);
	}

	@GetMapping("/company/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Company> getAllCompany() throws ForbiddenException {
		return this.companyRepository.findAll();
	}

	@PutMapping("/company/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Company> updateCompany(@PathVariable(value = "id") Long companyId,
			@RequestBody Company companyDetails) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		company.setName(companyDetails.getName());
		company.setDescription(companyDetails.getDescription());
		final Company updatedCompany = companyRepository.save(company);
		return ResponseEntity.ok(updatedCompany);
	}

	@DeleteMapping("/company/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteCompany(@PathVariable(value = "id") Long companyId)
			throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		companyRepository.delete(company);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PostMapping("/company/{id}/location/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Company> postCompanyLocation(@PathVariable(value = "id") Long companyId,
			@RequestBody Location locationDetails) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		locationDetails.setCompany(company);
		locationRepository.save(locationDetails);
		return ResponseEntity.ok().body(company);
	}

	@PutMapping("/company/{id1}/location/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Location> updateCompany(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long locationId, @RequestBody Location locationDetails)
			throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Location location = locationRepository.findById(locationId)
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));

		location.setAlias(locationDetails.getAlias());
		location.setAddress(locationDetails.getAddress());
		location.setDistrict(locationDetails.getDistrict());
		location.setThana(locationDetails.getThana());
		final Location updatedLocation = locationRepository.save(location);
		return ResponseEntity.ok(updatedLocation);
	}

	@DeleteMapping("/company/{id1}/location/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteLocation(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long locationId) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Location location = locationRepository.findById(locationId)
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));
		locationRepository.delete(location);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@GetMapping("/company/{id1}/location/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Location> getLocation(@PathVariable(value = "id1") Long companyId,
			@PathVariable(value = "id2") Long locationId) throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		Location location = locationRepository.findById(locationId)
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));
		return ResponseEntity.ok().body(location);
	}

	@GetMapping("/company/{id1}/location/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Location>> getLocation(@PathVariable(value = "id1") Long companyId)
			throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company not found"));

		List<Location> location = locationRepository.findByCompanyId(companyId);
		return ResponseEntity.ok().body(location);
	}
}
