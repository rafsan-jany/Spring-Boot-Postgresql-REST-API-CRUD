package com.dbl.nsl.erp.service;

import java.util.List;

import com.dbl.nsl.erp.models.Employee;

public interface EmployeeInfoService {
	List<Employee> findByEmailAddress(String name);
	Employee findByEmployeeNumber(Long eNumber);
	Employee save(Employee employee);
	void delete(Employee employee);
	List<Employee> findAll();

}
