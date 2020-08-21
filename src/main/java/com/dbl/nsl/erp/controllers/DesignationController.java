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
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.repository.DesignationRepository;

@RestController
@RequestMapping("/api/test")
public class DesignationController {
	@Autowired
	private DesignationRepository designationRepository;
	
    @PostMapping("/designation/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Designation employeeDesignation(@RequestBody Designation designation) {
		return designationRepository.save(designation);
    }
    
    @GetMapping("/designation/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Designation>> getSalaryById(@PathVariable(value = "id") Long designationId)
        throws ResourceNotFoundException {
        Optional<Designation> designation = designationRepository.findById(designationId);
        return ResponseEntity.ok().body(designation);
    }
    
	@GetMapping("/designation/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Designation> getAllpolicy(){
		return this.designationRepository.findAll();
	}
	
    @PutMapping("/designation/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Designation> updateDesignation(@PathVariable(value = "id") Long designationId,
         @RequestBody Designation designationDetails) throws ResourceNotFoundException {
         Designation designation = designationRepository.findById(designationId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + designationId));

        designation.setDesignationId(designationDetails.getDesignationId());
        designation.setDesignationName(designationDetails.getDesignationName());
        final Designation updatedDesignation = designationRepository.save(designation);
        return ResponseEntity.ok(updatedDesignation);
    }
    
    @DeleteMapping("/designation/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteDesignation(@PathVariable(value = "id") Long designationId)
         throws ResourceNotFoundException {
        Designation designation = designationRepository.findById(designationId)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + designationId));

        designationRepository.delete(designation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
