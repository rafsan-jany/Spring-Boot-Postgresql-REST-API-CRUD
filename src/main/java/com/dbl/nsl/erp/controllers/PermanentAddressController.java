package com.dbl.nsl.erp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.PermanentAddress;
import com.dbl.nsl.erp.repository.PermanentAddressRepository;

@RestController
@RequestMapping("/api/test")
public class PermanentAddressController {
	
	@Autowired
	PermanentAddressRepository permanentAddressRepository;
	
		@PostMapping("/permanent/save")
	    @PreAuthorize("hasRole('ADMIN')")
	    public PermanentAddress employeePermanentAddress(@RequestBody PermanentAddress permanentAddress) {
			return permanentAddressRepository.save(permanentAddress);
	    }
	
		@GetMapping("/permanent/find/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Optional<PermanentAddress>> getPermanentAddressById(@PathVariable(value = "id") Long permanentAddressId)
	        throws ResourceNotFoundException {
	        Optional<PermanentAddress> permanentAddress = permanentAddressRepository.findById(permanentAddressId);
	        return ResponseEntity.ok().body(permanentAddress);
	    }
	    
		@GetMapping("/permanent/findall")
		@PreAuthorize("hasRole('ADMIN')")
		public List<PermanentAddress> getAllPermanentAddress(){
			return this.permanentAddressRepository.findAll();
		}
		
	    @PutMapping("/permanent/edit/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<PermanentAddress> updatePermanentAddress(@PathVariable(value = "id") Long permanentAddressId,
	         @RequestBody PermanentAddress permanentAddressDetails) throws ResourceNotFoundException {
	    	 PermanentAddress permanentAddress = permanentAddressRepository.findById(permanentAddressId)
	        .orElseThrow(() -> new ResourceNotFoundException("Permanent Address not found for this id :: " + permanentAddressId));

	    	permanentAddress.setId(permanentAddressDetails.getId());
	    	permanentAddress.setHouseNo(permanentAddressDetails.getHouseNo());
	    	permanentAddress.setEmployee(permanentAddressDetails.getEmployee());
	    	
	        final PermanentAddress updatedPermanentAddress = permanentAddressRepository.save(permanentAddress);
	        return ResponseEntity.ok(updatedPermanentAddress);
	    }
	    
	    @DeleteMapping("/permanent/delete/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public Map<String, Boolean> deletePermanentAddress(@PathVariable(value = "id") Long permanentAddressId)
	         throws ResourceNotFoundException {
	        PermanentAddress permanentAddress = permanentAddressRepository.findById(permanentAddressId)
	       .orElseThrow(() -> new ResourceNotFoundException("Permanent Address not found for this id :: " + permanentAddressId));

	        permanentAddressRepository.delete(permanentAddress);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }

}
