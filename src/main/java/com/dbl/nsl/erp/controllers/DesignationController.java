package com.dbl.nsl.erp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.payload.response.DepartmentResponse;
import com.dbl.nsl.erp.payload.response.DesignationResponse;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.eRepository;

@RestController
@RequestMapping("/api/test")
public class DesignationController {
	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	eRepository employeeRepository;
	
    @PostMapping("/designations")
    @PreAuthorize("hasRole('ADMIN')")
    public Designation employeeDesignation(@RequestBody Designation designation) {
		return designationRepository.save(designation);
    }
    
    @GetMapping("/designations/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Designation>> getSalaryById(@PathVariable(value = "id") Long designationId)
        throws ResourceNotFoundException {
        Optional<Designation> designation = designationRepository.findById(designationId);
        return ResponseEntity.ok().body(designation);
    }
    
	@GetMapping("/designations")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Designation> getAllDesignation(){
		return this.designationRepository.findAll();
	}
	
    @PutMapping("/designations/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Designation> updateDesignation(@PathVariable(value = "id") Long designationId,
         @RequestBody Designation designationDetails) throws ResourceNotFoundException {
         Designation designation = designationRepository.findById(designationId)
        .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

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
       .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        designationRepository.delete(designation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    
	@GetMapping("/designations/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public List<DesignationResponse> getAllDesignationInformation(){
		
		List<Designation> designations = designationRepository.findAll();
		
		List<DesignationResponse> designationResponseList = new ArrayList<DesignationResponse>();
		
		for (Designation designation : designations) {
			DesignationResponse designationResponse = new DesignationResponse();
			designationResponse.setDesignationName(designation.getDesignationName());
			Set<Long> queryEmployee = employeeRepository.findByDesignationId(designation.getDesignationId());
			long totalEmployee = queryEmployee.size();
			designationResponse.setTotalEmployee(totalEmployee);
			
			designationResponseList.add(designationResponse);	
		}
		
		return designationResponseList;
	}
}
