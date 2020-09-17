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
import com.dbl.nsl.erp.exception.ResourceAlreadyExists;
import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Group;
import com.dbl.nsl.erp.payload.response.DepartmentResponse;
import com.dbl.nsl.erp.payload.response.GroupHrResponse;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.DepartmnetRepository;
import com.dbl.nsl.erp.repository.eRepository;

@RestController
@RequestMapping("/api/test")
public class DepartmentController {

	@Autowired
	private DepartmnetRepository departmentRepository;
	
	@Autowired
	eRepository employeeRepository;

//	@PostMapping("/departments")
//	@PreAuthorize("hasRole('ADMIN')")
//	public Department employeeDepartment(@RequestBody Department department) {
//		return departmentRepository.save(department);
//	}
	
	@PostMapping("/departments")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> employeeDepartment(@RequestBody Department department) {
		departmentRepository.save(department);
//		throw new ResourceAlreadyExists("Resource already exists in DB.");
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("ok"));
	}

	@GetMapping("/departments/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Department> getDepartmentById(@PathVariable(value = "id") Long departmentId)
			throws ResourceNotFoundException{
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));
		return ResponseEntity.ok().body(department);
	}

	@GetMapping("/departments")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Department> getAllDepartment()throws ForbiddenException {
		return this.departmentRepository.findAll();
	}

	@PutMapping("/departments/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") Long departmentId,
			@RequestBody Department departmentDetails) throws ResourceNotFoundException, ForbiddenException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		department.setDeparmentId(departmentDetails.getDepartmentId());
		department.setDepartmentName(departmentDetails.getDepartmentName());
//        department.setDepartmentLeadName(departmentDetails.getDepartmentLeadName());
		final Department updatedDepartment = departmentRepository.save(department);
		return ResponseEntity.ok(updatedDepartment);
	}

	@DeleteMapping("/department/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteDepartment(@PathVariable(value = "id") Long departmentId)
			throws ResourceNotFoundException {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found"));

		departmentRepository.delete(department);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
	@GetMapping("/departments/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public List<DepartmentResponse> getAllDepartmentInformation() {
		
		List<Department> departments = departmentRepository.findAll();
		
		List<DepartmentResponse> departmentResponseList = new ArrayList<DepartmentResponse>();
		
		for (Department department : departments) {
			DepartmentResponse departmentResponse = new DepartmentResponse();
			departmentResponse.setDepartmentName(department.getDepartmentName());
			Set<Long> queryEmployee = employeeRepository.findByDepartmenId(department.getDepartmentId());
			long totalEmployee = queryEmployee.size();
			departmentResponse.setTotalEmployee(totalEmployee);
//			departmentResponse.setDepartmentLeadName(department.getDepartmentLeadName());
			
			departmentResponseList.add(departmentResponse);	
		}
		return departmentResponseList;
	}
}
