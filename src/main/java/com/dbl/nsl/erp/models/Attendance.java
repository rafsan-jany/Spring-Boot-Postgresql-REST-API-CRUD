package com.dbl.nsl.erp.models;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "attendances")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@Column(name = "employee_id")
	private Long employeeId;
	
	@Column(name = "employee_name")
	private String employeeName;
	
	@Column(name = "designation_name")
	private String designationName;
	
	@Column(name = "department_name")
	private String departmentName;

	@Column
	@JsonFormat(pattern = "HH:mm:ss")
	private Time inTime;

	@Column
	@JsonFormat(pattern = "HH:mm:ss")
	private Time outTime;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "group_name")
	private String groupName;
	
	@Column(name = "group_id")
	private Long groupId;

	public Attendance() {

	}
	public Attendance(Date date, Long employeeId, String employeeName, String departmentName,
			String designationName, Time inTime, Time outTime, String status, String groupName, Long groupId) {
		this.date = date;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.designationName = designationName;
		this.departmentName = departmentName;
		this.inTime = inTime;
		this.outTime = outTime;
		this.status = status;
		this.groupName = groupName;
		this.groupId = groupId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEpmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Time getInTime() {
		return inTime;
	}
	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}
	public Time getOutTime() {
		return outTime;
	}
	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
