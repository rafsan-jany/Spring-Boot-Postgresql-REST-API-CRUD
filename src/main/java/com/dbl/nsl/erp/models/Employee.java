package com.dbl.nsl.erp.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {

	public Employee() {
	}

	public Employee(Long employeeId, String firstName, String email, boolean active, String employeeInTime,
			String permanent, String present) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.email = email;
		this.active = active;
		this.employeeInTime = employeeInTime;
		this.permanent = permanent;
		this.present = present;
//		this.fatherName = fatherName;
//		this.motherName = motherName;
//		this.birthDate = birthDate;
	}

//	public Employee(Long employeeId, String firstName, String lastName, String email, String fatherName,
//			String motherName, String DoB, String gender, Long nidNumber, String nationality,
//			String joiningDate) {
//		this.employeeId = employeeId;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.fatherName = fatherName;
//		this.motherName = motherName;
//		this.mobileNumber = mobileNumber;
//		this.DoB = DoB;
//		this.joiningDate = joiningDate;
//		this.gender = gender;
//		this.nidNumber = nidNumber;
//		this.nationality = nationality;
//	}

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;

	@Id
	@Column(name = "employee_id")
	private Long employeeId;

	@Column(name = "first_name")
	private String firstName;

//	@Column(name = "last_name")
//	private String lastName;
//	
	@Column(name = "email")
	private String email;

	@Column(name = "active")
	private boolean active;

	@Column(name = "in_time")
	private String employeeInTime;

	@Column(name = "permanent")
	private String permanent;

	@Column(name = "present")
	private String present;

	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "mother_name")
	private String motherName;

//	@Column(name = "birth_date")
//	private Date birthDate;
//	
//	@Column(name = "joining_date")
//	private String joiningDate;
//	
//	@Column(name = "gender")
//	private String gender;
//	
//	@Column(name = "nid_number")
//	private Long nidNumber;
//	
//	@Column(name = "nationality")
//	private String nationality;

	@JsonManagedReference
	@OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // inverse side
	private PermanentAddress permanentAddress;

	@JsonManagedReference
	@OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // inverse side
	private PresentAddress presentAddress;

//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "employees_departments", joinColumns = {
			@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false, updatable = false) })
//    private Set<Department> departments = new HashSet<>();
	private List<Department> departments;

//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // owning side
	@JoinTable(name = "employees_designations", joinColumns = {
			@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "designation_id", referencedColumnName = "designation_id", nullable = false, updatable = false) })
//    private Set<Designation> designations = new HashSet<>();
	private List<Designation> designations;

//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // owning side
	@JoinTable(name = "employees_groups", joinColumns = {
			@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false, updatable = false) })
//    private Set<Designation> designations = new HashSet<>();
	private List<Group> groups;

//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // owning side
	@JoinTable(name = "employees_companies", joinColumns = {
			@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false, updatable = false) })
//    private Set<Designation> designations = new HashSet<>();
	private List<Company> companies;

//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // owning side
	@JoinTable(name = "employees_leaves", joinColumns = {
			@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "leave_id", referencedColumnName = "leave_id", nullable = false, updatable = false) })
//    private Set<Designation> designations = new HashSet<>();
	private List<Leave> leaves;
//
////  @JsonBackReference
//	@ManyToOne(fetch = FetchType.LAZY, optional = false) // owning side
//	@JoinColumn(name = "salary_id", nullable = false)
//	private Salary salary;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<LeaveConsume> leaveConsume;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Experience> experience;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Education> education;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Contact> contact;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, // inverse side
			cascade = CascadeType.ALL)
	private List<Certification> certification;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

//	public Date getBirthDate() {
//		return birthDate;
//	}
//
//	public void setBirthDate(Date birthDate) {
//		this.birthDate = birthDate;
//	}
//	public String getJoiningDate() {
//		return joiningDate;
//	}
//	public void setJoiningDate(String joiningDate) {
//		this.joiningDate = joiningDate;
//	}
//	public String getGender() {
//		return gender;
//	}
//	public void setGender( String gender) {
//		this.gender = gender;
//	}
//	public Long getNidNumber() {
//		return nidNumber;
//	}
//	public void setNidNumber( Long nidNumber) {
//		this.nidNumber = nidNumber;
//	}	
//	public String getNationality() {
//		return nationality;
//	}	
//	public void setNationality( String nationality) {
//		this.nationality = nationality;
//	}	

//	public String getLastName() {
//		return lastName;
//	}
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmployeeInTime() {
		return employeeInTime;
	}

	public void setEmployeeInTime(String employeeInTime) {
		this.employeeInTime = employeeInTime;
	}

	public String getPermanent() {
		return permanent;
	}

	public void setPermanent(String permanent) {
		this.permanent = permanent;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

//	public Long getpolicyId() {
//		return policyId;
//	}
//	public void setPolicyId(Long policyId) {
//		this.policyId = policyId;
//	}
	public PermanentAddress getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(PermanentAddress permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	public List<Certification> getCertification() {
		return certification;
	}

	public void setCertification(List<Certification> certification) {
		this.certification = certification;
	}

	public PresentAddress getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(PresentAddress presentAddress) {
		this.presentAddress = presentAddress;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartment(List<Department> departments) {
		this.departments = departments;
	}

	public List<Group> getGroup() {
		return groups;
	}

	public void setGroup(List<Group> groups) {
		this.groups = groups;
	}

	public List<Company> getCompany() {
		return companies;
	}

	public void setCompany(List<Company> companies) {
		this.companies = companies;
	}

	public List<Designation> getDesignation() {
		return designations;
	}

	public void setDesignation(List<Designation> designations) {
		this.designations = designations;
	}

	public List<Leave> getLeave() {
		return leaves;
	}

	public void setLeave(List<Leave> leaves) {
		this.leaves = leaves;
	}

//	@JsonBackReference
//	public Salary getSalary() {
//		return salary;
//	}
//
//	public void setSalary(Salary salary) {
//		this.salary = salary;
//	}

//	@JsonManagedReference
	public List<LeaveConsume> getLeaveConsume() {
		return leaveConsume;
	}

	public void setLeaveConsume(List<LeaveConsume> leaveConsume) {
		this.leaveConsume = leaveConsume;

	}

	public List<Experience> getExperience() {
		return experience;
	}

	public void setExperience(List<Experience> experience) {
		this.experience = experience;

	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;

	}
}
