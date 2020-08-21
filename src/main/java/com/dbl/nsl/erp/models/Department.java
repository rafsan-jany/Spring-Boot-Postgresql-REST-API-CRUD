package com.dbl.nsl.erp.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "departments")

//
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "departmentId")
public class Department {
	public Department() {
	}
	
	public Department (long departmentId, String departmentName, String departmentLeadName, String projectManagerName) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
//		this.departmentLeadName = departmentLeadName;
//		this.projectManagerName = projectManagerName;
		
	}
	
	@Id
	@Column(name = "department_id")
	private Long departmentId;
	@Column(name = "department_name")
	private String departmentName;
//	@Column(name = "department_lead_name")
//	private String departmentLeadName;
//	@Column(name = "project_manager_name")
//	private String projectManagerName;
	
//	@JsonBackReference
    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
//    private Set<Employee> employees = new HashSet<>();
	private List<Employee> employees;
	
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDeparmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

//	public String getDepartmentLeadName() {
//		return departmentLeadName;
//	}
//
//	public void setDepartmentLeadName(String departmentLeadName) {
//		this.departmentLeadName = departmentLeadName;
//	}
//
//	public String getProjectManagerName () {
//		return projectManagerName ;
//	}
//
//	public void setProjectManagerName(String projectManagerName) {
//		this.projectManagerName = projectManagerName;
//	}
	
	@JsonBackReference
	public List<Employee> getEmployee() {
		return employees;
	}
	public void setEmployee(List<Employee> employees) {
		this.employees = employees;
	}
}
