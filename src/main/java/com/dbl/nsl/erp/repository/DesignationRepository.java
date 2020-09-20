package com.dbl.nsl.erp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long>{

	@Query (value = "select designations.designation_name from designations join employees_designations on designations.designation_id = employees_designations.designation_id join employees on employees_designations.employee_id = ?1", nativeQuery = true)
	Set<String> findDesignationByEmployeeId(Long employeeId);

	boolean existsByName(String name);

}
