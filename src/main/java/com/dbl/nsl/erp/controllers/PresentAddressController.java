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
import com.dbl.nsl.erp.models.PresentAddress;
import com.dbl.nsl.erp.repository.PresentAddressRepository;

@RestController
@RequestMapping("/api/test")
public class PresentAddressController {
	
	@Autowired
	PresentAddressRepository presentAddressRepository;
	
	@PostMapping("/present/save")
    @PreAuthorize("hasRole('ADMIN')")
    public PresentAddress employeePresentAddress(@RequestBody PresentAddress presentAddress) {
		return presentAddressRepository.save(presentAddress);
    }
	
	@GetMapping("/present/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<PresentAddress>> getPresentAddressById(@PathVariable(value = "id") Long presentAddressId)
        throws ResourceNotFoundException {
        Optional<PresentAddress> presentAddress = presentAddressRepository.findById(presentAddressId);
        return ResponseEntity.ok().body(presentAddress);
    }
    
	@GetMapping("present/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<PresentAddress> getAllAddress(){
		return this.presentAddressRepository.findAll();
	}
	
    @PutMapping("/present/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PresentAddress> updatePresentAddress(@PathVariable(value = "id") Long presentAddressId,
         @RequestBody PresentAddress presentAddressDetails) throws ResourceNotFoundException {
    	 PresentAddress presentAddress = presentAddressRepository.findById(presentAddressId)
        .orElseThrow(() -> new ResourceNotFoundException("Present Address not found for this id :: " + presentAddressId));

    	presentAddress.setId(presentAddressDetails.getId());
    	presentAddress.setHouseNo(presentAddressDetails.getHouseNo());
    	presentAddress.setEmployee(presentAddressDetails.getEmployee());
    	
        final PresentAddress updatedPresentAddress = presentAddressRepository.save(presentAddress);
        return ResponseEntity.ok(updatedPresentAddress);
    }
    
    @DeleteMapping("/present/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deletePresentAddress(@PathVariable(value = "id") Long presentAddressId)
         throws ResourceNotFoundException {
        PresentAddress presentAddress = presentAddressRepository.findById(presentAddressId)
       .orElseThrow(() -> new ResourceNotFoundException("Present Address not found for this id :: " + presentAddressId));

        presentAddressRepository.delete(presentAddress);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
