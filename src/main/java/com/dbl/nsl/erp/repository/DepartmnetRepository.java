package com.dbl.nsl.erp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Department;

public interface DepartmnetRepository extends JpaRepository<Department, Long> {

	@Query( value = "select departments.department_name from departments join employees_departments on departments.department_id = employees_departments.department_id join employees on employees_departments.employee_id = ?1", nativeQuery = true)
	Set<String> findDepartmentByEmployeeId(Long employeeId);

	boolean existsByName(String name);

}
