package com.dbl.nsl.erp.controllers;

import java.util.ArrayList;
//import lombok.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dbl.nsl.erp.models.PermanentAddress;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.repository.EmployeeRepository;
import com.dbl.nsl.erp.service.EmployeeInfoService;

@RestController
@RequestMapping("/api/test")
public class EmployeeController {
	
	@Autowired
	EmployeeInfoService employeeService;
	
	
//    @PostMapping("/employee/save")
//    @PreAuthorize("hasRole('ADMIN')")
//    public Employee createEmployee(@RequestBody Employee employee) {
//        return employeeService.save(employee);
//    }   
	
//    @GetMapping("/employee/find/{employeeNumber}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<Employee> getEmployeeByEmployeeNumber(@PathVariable(value = "employeeNumber") Long employeeNumber)
//        throws ResourceNotFoundException {
//        Employee employee = employeeService.findByEmployeeNumber(employeeNumber);
//        return ResponseEntity.ok().body(employee);
//    }
	
//	@GetMapping("employee/findall")
//	@PreAuthorize("hasRole('ADMIN')")
//	public List<Employee> getAllEmployee(){
//		return this.employeeService.findAll();
//	}
	
//    @PutMapping("/employee/edit/{employeeNumber}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "employeeNumber") Long employeeNumber,
//         @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
//        Employee employee = employeeService.findByEmployeeNumber(employeeNumber);
////        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//
//        employee.setLastName(employeeDetails.getLastName());
//        employee.setFirstName(employeeDetails.getFirstName());
//        employee.setFatherName(employeeDetails.getFatherName());
//        employee.setMotherName(employeeDetails.getMotherName());
//        employee.setDoB(employeeDetails.getDoB());
//        employee.setGender(employeeDetails.getGender());
//        employee.setNidNumber(employeeDetails.getNidNumber());
//        employee.setNationality(employeeDetails.getNationality());
//        employee.setPresentAddress(employeeDetails.getPresentAddress());
//        employee.setPermanentAddress(employeeDetails.getPermanentAddress());
//        employee.setEmail(employeeDetails.getEmail());
//        employee.setMobileNumber(employeeDetails.getMobileNumber());
//        employee.setEmergencyContact(employeeDetails.getEmergencyContact());
//        employee.setDesignation(employeeDetails.getDesignation());
//        
//        final Employee updatedEmployee = employeeService.save(employee);
//        return ResponseEntity.ok(updatedEmployee);
//    }
    
     
//    @DeleteMapping("/employee/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeNumber)
//         throws ResourceNotFoundException {
//        Employee employee = employeeService.findByEmployeeNumber(employeeNumber);
////       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeNumber));
//
//        employeeService.delete(employee);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }
    
//    @GetMapping("/employee/email/{email}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<List<String>> getEmployeeByEmail(@PathVariable(value = "email") String email)
//        throws ResourceNotFoundException {
//        List <Employee> employee = employeeService.findByEmailAddress(email);
//        List<String> name = new ArrayList<String>();
//        employee.stream().forEach((e)->name.add(e.getFirstName()));
////        	.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId))
////        return ResponseEntity.ok().body(employee);
//        return ResponseEntity.ok().body(name);
//    }
}

