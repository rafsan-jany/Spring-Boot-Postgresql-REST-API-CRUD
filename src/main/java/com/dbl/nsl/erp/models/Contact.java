package com.dbl.nsl.erp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "contacts")
public class Contact {

	public Contact() {
		
	}
	public Contact(String contact, String emergencyContact, String relation) {
		this.contact = contact;
		this.emergencyContact = emergencyContact;
		this.relation = relation;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contact_id")
	private Long id;
	
	@Column(name = "contact")
	private String contact;
	
	@Column(name = "emergency_contact")
	@JsonProperty("emergency_contact")
	private String emergencyContact;
	
	@Column(name = "relation")
	private String relation;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
    @JsonBackReference
    public Employee getEmployee() {
    	return employee;
    }
    public void setEmployee(Employee employee) {
    	this.employee = employee;
    }
}
