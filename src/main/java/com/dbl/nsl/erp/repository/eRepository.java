package com.dbl.nsl.erp.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Attendance;
import com.dbl.nsl.erp.models.Employee;

public interface eRepository extends JpaRepository <Employee, Long>{

	List<Employee> findByEmail(String email);
	List<Employee> findByActiveTrue();
//	Long countByGroupName(String groupName);
//	Set<Employee> findDistinctByGroupName(String groupName);
	
    @Query( value = "select employees.employee_id from employees join employees_departments on employees.employee_id = employees_departments.employee_id join departments on employees_departments.department_id = ?1", nativeQuery = true)
	Set<Long> findByDepartmenId(Long departmentId);
    
    @Query( value = "select employees.employee_id from employees join employees_designations on employees.employee_id = employees_designations.employee_id join designations on employees_designations.designation_id = ?1", nativeQuery = true)
	Set<Long> findByDesignationId(Long designationId);
    
    @Query(value = "select employees.employee_id from employees where group_name = ?1", nativeQuery = true)
	List<Long> findByGroupName(String groupName);
    
    @Query( value = "select employees.first_name from employees where employee_id = ?1", nativeQuery = true)
	String findNameById(Long employeeId);
    
    @Query( value = "select employees.employee_id from employees", nativeQuery = true)
	List<Long> findEmployeeId();
    
//	Optional<Employee> findByEmployeeId(Long employeeId);
    
//    @Query( value = "select employees.employee_id from employees join employees_groups on employees.employee_id = employees_groups.group_id join groups on employees_groups.group_id = ?1", nativeQuery = true )
//	Set<Long> totalEmployeeByGroupId(Long groupName);
    

}
