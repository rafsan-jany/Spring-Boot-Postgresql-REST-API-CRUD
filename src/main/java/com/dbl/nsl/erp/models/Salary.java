package com.dbl.nsl.erp.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "salaries")


//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "salaryId")
public class Salary {
	public Salary() {
		
	}
	
	public Salary(Long totalSalary, Long basicSalary) {
		this.totalSalary = totalSalary;
		this.basicSalary = basicSalary;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "salary_id")
	private Long salaryId;
	
	@Column(name = "total_salary")
	private Long totalSalary;
	
	@Column(name = "basic_salary")
	private Long basicSalary;
	
////	@JsonManagedReference
//    @OneToMany(mappedBy = "salary", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private List<Employee> employee;
	
	
	public Long getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(Long salaryId) {
		this.salaryId = salaryId;
	}
	public Long getTotalSalary() {
		return totalSalary;
	}
	public void setTotalSalary(Long totalSalary) {
		this.totalSalary = totalSalary;
	}
	public Long getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(Long basicSalary) {
		this.basicSalary = basicSalary;
	}
//	@JsonManagedReference
//	public List<Employee> getEmployee() {
//		return employee;
//	}
//	public void setEmployee(List<Employee> employee) {
//		this.employee = employee;
//	}
}
