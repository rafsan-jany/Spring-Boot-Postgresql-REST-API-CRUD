package com.dbl.nsl.erp.models;

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

@Entity
@Table(name = "leaves_consumes")
public class LeaveConsume {
	public LeaveConsume() {
		
	}
	public LeaveConsume(String startDate, String endDate, Long totalDay, String leaveType, boolean isAccepted) {
		this.startDate = startDate;
//		this.endDate = endDate;
//		this.totalDay = totalDay;
//		this.leaveType = leaveType;
		this.isAccepted  = isAccepted;	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "start_date")
	private String startDate;
	
//	@Column(name = "end_date")
//	private String endDate;
//	
//	@Column(name = "total_day")
//	private Long totalDay;
//	
//	@Column(name = "leave_type")
//	private String leaveType;
	
	@Column(name = "is_accepted")
	private boolean isAccepted;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
//	public String getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//	public Long getTotalDay() {
//		return totalDay;
//	}
//	public void setTotalDay(Long totalDay) {
//		this.totalDay = totalDay;
//	}
//	public String getLeaveType() {
//		return leaveType;
//	}
//	public void setLeaveType(String leaveType) {
//		this.leaveType = leaveType;
//	}
	
	public boolean getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted; 
	}
	@JsonBackReference
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee; 
	}
}
