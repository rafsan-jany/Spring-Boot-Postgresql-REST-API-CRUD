package com.dbl.nsl.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	  @Query("select e from Employee e where e.email = ?1")
	  List<Employee> findByEmailAddress(String name);
  
	  @Query("select e from Employee e where e.employeeId = ?1")
	  Employee findByEmployeeNumber(Long eNumber);

}
