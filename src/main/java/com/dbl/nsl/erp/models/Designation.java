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

@Entity
@Table(name = "designations")
public class Designation {
	public Designation() {
		
	}
	public Designation(Long designationId, String designationName) {
		this.designationId = designationId;
		this.designationName = designationName;	
	}
	
	@Id
	@Column(name = "designation_id")
	private Long designationId;
	
	@Column(name = "designation_name")
	private String designationName;
	
	@JsonBackReference
    @ManyToMany(mappedBy = "designations", fetch = FetchType.LAZY)
//    private Set<Employee> employees = new HashSet<>();
	private List<Employee> employees;
	
	
	public Long getDesignationId() {
		return designationId;
	}
	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	@JsonBackReference
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
