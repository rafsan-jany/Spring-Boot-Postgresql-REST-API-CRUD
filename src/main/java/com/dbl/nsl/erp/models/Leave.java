package com.dbl.nsl.erp.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "leaves")
public class Leave {
	public Leave() {
		
	}
	public Leave(String leaveType, Long leaveDays) {
		this.leaveType = leaveType;
		this.leaveDays = leaveDays;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leave_id")
	private Long leaveId;
	
	@Column(name = "leave_type")
	private String leaveType;
	
	@Column(name = "leave_days")
	private Long leaveDays;
	
    @ManyToMany(mappedBy = "leaves", fetch = FetchType.LAZY)
//  private Set<Employee> employees = new HashSet<>();
	private List<Employee> employees;
	
	public Long getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public Long getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(Long leaveDays) {
		this.leaveDays = leaveDays;
	}
	@JsonBackReference
	public List<Employee> getEmployee() {
		return employees;
	}
	public void setEmployee(List<Employee> employees) {
		this.employees = employees;
	}
}
