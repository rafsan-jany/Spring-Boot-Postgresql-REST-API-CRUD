package com.dbl.nsl.erp.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Entity
@Data
@Table(name = "locations")
public class Location {
	public Location() {
		
	}
	public Location(String alias, String district, String thana, String address) {
		this.alias = alias;
		this.district = district;
		this.thana = thana;
		this.address = address;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private Long id;
	
	@Column(name = "alias")
	private String alias;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "thana")
//	@JsonProperty("police_station")
	private String thana;
	
	@Column(name = "address")
	private String address;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getThana() {
		return thana;
	}
	public void setThana(String thana) {
		this.thana = thana;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@JsonBackReference
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company; 
	}

}
