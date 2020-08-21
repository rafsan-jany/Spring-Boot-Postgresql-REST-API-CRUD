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
import com.dbl.nsl.erp.repository.DepartmnetRepository;

@RestController
@RequestMapping("/api/test")
public class DepartmentController {
	
	@Autowired
	private DepartmnetRepository departmentRepository;
	
	@PostMapping("/department/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Department employeeDepartment(@RequestBody Department department) {
		return departmentRepository.save(department);
    }
    
    @GetMapping("/department/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Department>> getDepartmentById(@PathVariable(value = "id") Long departmentId)
        throws ResourceNotFoundException {
        Optional<Department> department = departmentRepository.findById(departmentId);
        return ResponseEntity.ok().body(department);
    }
    
	@GetMapping("/department/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Department> getAllDepartment(){
		return this.departmentRepository.findAll();
	}
	
    @PutMapping("/department/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") Long departmentId,
         @RequestBody Department departmentDetails) throws ResourceNotFoundException {
        Department department = departmentRepository.findById(departmentId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));

        department.setDeparmentId(departmentDetails.getDepartmentId());
        department.setDepartmentName(departmentDetails.getDepartmentName());
//        department.setDepartmentLeadName(departmentDetails.getDepartmentLeadName());
//        department.setProjectManagerName(departmentDetails.getProjectManagerName());
        final Department updatedDepartment= departmentRepository.save(department);
        return ResponseEntity.ok(updatedDepartment);
    }
    
    @DeleteMapping("/department/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteDepartment(@PathVariable(value = "id") Long departmentId)
         throws ResourceNotFoundException {
        Department department = departmentRepository.findById(departmentId)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + departmentId));

        departmentRepository.delete(department);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
