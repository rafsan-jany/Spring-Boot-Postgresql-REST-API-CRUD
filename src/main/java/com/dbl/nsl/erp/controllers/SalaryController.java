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
import com.dbl.nsl.erp.models.Salary;
import com.dbl.nsl.erp.repository.SalaryRepository;

@RestController
@RequestMapping("/api/test")
public class SalaryController {
	
	@Autowired
	private SalaryRepository salaryRepository;
	
    @PostMapping("/salary/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Salary employeePolicy(@RequestBody Salary salary) {
		return salaryRepository.save(salary);
    }
    
    @GetMapping("/salary/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Salary>> getSalaryById(@PathVariable(value = "id") Long salaryId)
        throws ResourceNotFoundException {
        Optional<Salary> salary = salaryRepository.findById(salaryId);
        return ResponseEntity.ok().body(salary);
    }
    
	@GetMapping("/salary/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Salary> getAllpolicy(){
		return this.salaryRepository.findAll();
	}
	
    @PutMapping("/salary/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Salary> updatePolicy(@PathVariable(value = "id") Long salaryId,
         @RequestBody Salary salaryDetails) throws ResourceNotFoundException {
        Salary salary = salaryRepository.findById(salaryId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + salaryId));

        salary.setSalaryId(salaryDetails.getSalaryId());
        salary.setTotalSalary(salaryDetails.getTotalSalary());
        salary.setBasicSalary(salaryDetails.getBasicSalary());

        final Salary updatedSalary = salaryRepository.save(salary);
        return ResponseEntity.ok(updatedSalary);
    }
    
    @DeleteMapping("/salary/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteSalary(@PathVariable(value = "id") Long salaryId)
         throws ResourceNotFoundException {
        Salary salary = salaryRepository.findById(salaryId)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + salaryId));

        salaryRepository.delete(salary);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
