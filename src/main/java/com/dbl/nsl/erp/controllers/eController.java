package com.dbl.nsl.erp.controllers;

import java.util.ArrayList;
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
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.PermanentAddress;
import com.dbl.nsl.erp.models.PresentAddress;
import com.dbl.nsl.erp.models.Salary;
import com.dbl.nsl.erp.repository.DepartmnetRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.SalaryRepository;
import com.dbl.nsl.erp.repository.eRepository;

import io.jsonwebtoken.lang.Arrays;

@RestController
@RequestMapping("/api/test")
public class eController {
	
	@Autowired
	eRepository erepository;
	
	@Autowired
	DepartmnetRepository departmentRepository;
	
	@Autowired
	SalaryRepository salaryRepository;
	
	@Autowired
	DesignationRepository designationRepository;
	
    @PostMapping("/employee/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee createEmployee(@RequestBody Employee employee) {
    	
//    	Employee employee2 = new Employee(7l, "a", "b", "c", "d", "e", "f", "g", "h",
//    			3l, "j", "k", 4l, "i" );
//    	
//    	
//    	PresentAddress presentAddress = new PresentAddress("a", "b", 1L, "c", "d", "e");
//    	presentAddress.setEmployee(employee2);
//    	employee2.setPresentAddress(presentAddress);
//    	
//    	PermanentAddress permanentAddress = new PermanentAddress("aaa", "bbb", 1L, "ccc", "ddd", "eee");
//    	permanentAddress.setEmployee(employee2);
//    	employee2.setPermanentAddress(permanentAddress);
//    	
//    	Department department1 = new Department(6000l, "aa", "bb", "cc");
//    	departmentRepository.save(department1);
//    	
//    	Designation designation1 = new Designation(3l, "designer");
//    	designationRepository.save(designation1);
//    	
//    	Salary salary = new Salary(1L, 30000l, 20000l);
//    	salaryRepository.save(salary);
//    	
//    	employee2.getDepartments().add(department1);
//    	employee2.setPermanentAddress(permanentAddress);
//    	employee2.setPresentAddress(presentAddress);
//    	employee2.setSalary(salary);
//    	employee2.getDesignation().add(designation1);
//    	
//        return erepository.save(employee2);
        return erepository.save(employee);
    }   
    
    @GetMapping("/employee/find/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Optional<Employee>> getEmployeeByEmployeeNumber(@PathVariable(value = "id") Long employeeNumber)
        throws ResourceNotFoundException {
        Optional<Employee> employee = erepository.findById(employeeNumber);
        return ResponseEntity.ok().body(employee);
    }
    
	@GetMapping("employee/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Employee> getAllEmployee(){
		return this.erepository.findAll();
	}
	
    @PutMapping("/employee/edit/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeNumber,
         @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = erepository.findById(employeeNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeNumber));

//        employee.setLastName(employeeDetails.getLastName());
//        employee.setFirstName(employeeDetails.getFirstName());
//        employee.setFatherName(employeeDetails.getFatherName());
//        employee.setMotherName(employeeDetails.getMotherName());
//        employee.setDoB(employeeDetails.getDoB());
//        employee.setGender(employeeDetails.getGender());
//        employee.setNidNumber(employeeDetails.getNidNumber());
//        employee.setNationality(employeeDetails.getNationality());
//        employee.setEmail(employeeDetails.getEmail());
//        employee.setMobileNumber(employeeDetails.getMobileNumber());
//        employee.setEmergencyContact(employeeDetails.getEmergencyContact());
        employee.setPermanentAddress(employeeDetails.getPermanentAddress());
        employee.setPresentAddress(employeeDetails.getPresentAddress());
        employee.setDepartment(employeeDetails.getDepartments());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setSsc(employeeDetails.getSsc());
        employee.setLeave(employeeDetails.getLeave());
       
//        employee.setDesignation(employeeDetails.getDesignation());
        
        
        final Employee updatedEmployee = erepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
    
    @DeleteMapping("/employee/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeNumber)
         throws ResourceNotFoundException {
        Employee employee = erepository.findById(employeeNumber)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeNumber));

        erepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    
    @GetMapping("/employee/email/{email}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<String>> getEmployeeByEmail(@PathVariable(value = "email") String email)
        throws ResourceNotFoundException {
        List <Employee> employee = erepository.findByEmail(email);
        List<String> name = new ArrayList<String>();
        employee.stream().forEach((e)->name.add(e.getFirstName()));
//        	.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + email))
//        return ResponseEntity.ok().body(employee);
        return ResponseEntity.ok().body(name);
    }
    
	@GetMapping("employee/active")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Employee> getAllActiveEmployee(){
		return this.erepository.findByActiveTrue();
	}
}
