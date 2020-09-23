package com.dbl.nsl.erp.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "educations")
public class Education {
	public Education() {
		
	}
	public Education(String degree, String institute, String passingYear, String concentration) {
		this.degree = degree;
		this.institute = institute;
		this.passingYear = passingYear;
		this.concentration = concentration;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "degree")
	private String degree;
	
	@Column(name = "institute")
	private String institute;
	
	@Column(name = "passing_year")
	@JsonProperty("passing_year")
	private String passingYear;
	
	@Column(name = "concentration")
	private String concentration;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
	
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getDegree() {
    	return degree;
    }
    public void setDegree(String degree) {
    	this.degree = degree;
    }
    
    public String getInstitute() {
    	return institute;
    }
    public void setInstitute(String institute) {
    	this.institute = institute;
    }
	
    public String getPassingYear() {
    	return passingYear;
    }
    public void setPassingYear(String passingYear) {
    	this.passingYear = passingYear;
    }
	public String getConcentration() {
		return concentration;
	}
	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}
	
	@JsonBackReference
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee; 
	}
}
