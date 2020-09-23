package com.dbl.nsl.erp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ForbiddenException;
import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.payload.response.DepartmentResponse;
import com.dbl.nsl.erp.payload.response.DesignationResponse;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.eRepository;

@RestController
//@RequestMapping("/api/test")
public class DesignationController {
	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	eRepository employeeRepository;
	
    @PostMapping("/designation/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> employeeDesignation(@RequestBody Designation designation) {
		if (designationRepository.existsByDesignationName(designation.getDesignationName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Designation already exists"));
		}
		designationRepository.save(designation);
		return ResponseEntity.ok(new MessageResponse("Designation registered successfully"));
    }
    
    @GetMapping("/designation/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Designation> getDesignation(@PathVariable(value = "id") Long designationId)
        throws ResourceNotFoundException {
        Designation designation = designationRepository.findById(designationId)
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found"));
        return ResponseEntity.ok().body(designation);
    }
    
	@GetMapping("/designation/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Designation> getAllDesignation() throws ForbiddenException {
		return this.designationRepository.findAll();
	}
	
    @PutMapping("/designation/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Designation> updateDesignation(@PathVariable(value = "id") Long designationId,
         @RequestBody Designation designationDetails) throws ResourceNotFoundException {
         Designation designation = designationRepository.findById(designationId)
        .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        designation.setDesignationName(designationDetails.getDesignationName());
        designation.setDescription(designationDetails.getDescription());
        final Designation updatedDesignation = designationRepository.save(designation);
        return ResponseEntity.ok(updatedDesignation);
    }
    
    @DeleteMapping("/designation/{id}")
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
