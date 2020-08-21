package com.dbl.nsl.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbl.nsl.erp.models.Employee;

public interface eRepository extends JpaRepository <Employee, Long>{

	List<Employee> findByEmail(String email);
	List<Employee> findByActiveTrue();

}
