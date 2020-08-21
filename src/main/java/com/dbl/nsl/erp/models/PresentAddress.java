package com.dbl.nsl.erp.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "present_addresses")

//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
public class PresentAddress {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String houseNo;
//    private String roadNo;
//    private Long postalCode;
//    private String policeStation;
//    private String districtName;
//    private String countryName;
    
//    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    public PresentAddress() {
    }
    
//    public PresentAddress(String houseNo, String roadNo, Long postalCode, String policeStation,
//    		String districtName, String countryName) {
//	 this.houseNo = houseNo;
//	 this.roadNo = roadNo;
//	 this.postalCode = postalCode;
//	 this.policeStation = policeStation;
//	 this.districtName = districtName;
//	 this.countryName = countryName;
//    }
    public PresentAddress(String houseNo, String roadNo, Long postalCode, String policeStation,
    		String districtName, String countryName) {
	 this.houseNo = houseNo;
    }
    
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    public String getHouseNo() {
    	return houseNo;
    }
    public void setHouseNo(String houseNo) {
    	this.houseNo = houseNo;
    }
//    public String getRoadNo() {
//    	return roadNo;
//    }
//    public void setRoadNo(String roadNo) {
//    	this.roadNo = roadNo;
//    }
//    public Long getPostalCode () {
//    	return postalCode;
//    }
//    public void setPostalCode(Long postalCode) {
//    	this.postalCode = postalCode;
//    }
//    public String getPoliceStation() {
//    	return policeStation;
//    }
//    public void setPoliceStation(String policeStation) {
//    	this.policeStation = policeStation;
//    }
//    public String getDistrictName() {
//    	return districtName;
//    }
//    public void setDistrictName(String districtName) {
//    	this.districtName = districtName;
//    }
//    public String getCountryName() {
//    	return countryName;
//    }
//    public void setCountryName(String countryName) {
//    	this.countryName = countryName;
//    }
    @JsonBackReference
    public Employee getEmployee() {
    	return employee;
    }
    public void setEmployee(Employee employee) {
    	this.employee = employee;
    }
}
