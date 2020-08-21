package com.dbl.nsl.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.repository.EmployeeRepository;

@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Override
	public List<Employee> findByEmailAddress(String name){
//		return List<Employee> repository.findByEmailAddress(name);
		return repository.findByEmailAddress(name);
	}
	
	
	@Override
	public Employee findByEmployeeNumber(Long eNumber){
		return (Employee)repository.findByEmployeeNumber(eNumber);
	}
	
	@Override
	public Employee save(Employee employee){
		return repository.save(employee);
	}


	@Override
	public void delete(Employee employee) {
		repository.delete(employee);
		
	}


	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
}
