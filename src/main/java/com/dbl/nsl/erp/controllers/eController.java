package com.dbl.nsl.erp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Certification;
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Contact;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Designation;
import com.dbl.nsl.erp.models.Education;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Experience;
import com.dbl.nsl.erp.models.Location;
import com.dbl.nsl.erp.models.PermanentAddress;
import com.dbl.nsl.erp.models.PresentAddress;
import com.dbl.nsl.erp.models.Salary;
import com.dbl.nsl.erp.payload.request.AddressRequest;
import com.dbl.nsl.erp.payload.request.PersonalInformation;
import com.dbl.nsl.erp.repository.CertificationRepository;
import com.dbl.nsl.erp.repository.ContactRepository;
import com.dbl.nsl.erp.repository.DepartmnetRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.EducationRepository;
import com.dbl.nsl.erp.repository.ExperienceRepository;
import com.dbl.nsl.erp.repository.PermanentAddressRepository;
import com.dbl.nsl.erp.repository.SalaryRepository;
import com.dbl.nsl.erp.repository.eRepository;

import io.jsonwebtoken.lang.Arrays;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
//@RequestMapping("/api/test")
public class eController {

	@Autowired
	eRepository erepository;

	@Autowired
	DepartmnetRepository departmentRepository;

	@Autowired
	SalaryRepository salaryRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Autowired
	ExperienceRepository experienceRepository;

	@Autowired
	EducationRepository educationRepository;

	@Autowired
	PermanentAddressRepository permanentAddressRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	CertificationRepository certificationRepository;

	@PostMapping("/employee/add")
	@PreAuthorize("hasRole('ADMIN')")
	public Employee createEmployee(@RequestBody Employee employee) {

//    	Employee employee2 = new Employee(7l, "a", "b", "c", "d", "e", "f", "g", "h",
//    			3l, "j", "k", 4l, "i" );
//    	
//    	
//    	PresentAddress presentAddress = new PresentAddress("a", "b", 1L, "c", "d", "e");
//    	presentAddress.setEmployee(employee2);
//    	employee2.setPresentAddress(presentAddress);
//    	
//    	PermanentAddress permanentAddress = new PermanentAddress("aaa", "bbb", 1L, "ccc", "ddd", "eee");
//    	permanentAddress.setEmployee(employee2);
//    	employee2.setPermanentAddress(permanentAddress);
//    	
//    	Department department1 = new Department(6000l, "aa", "bb", "cc");
//    	departmentRepository.save(department1);
//    	
//    	Designation designation1 = new Designation(3l, "designer");
//    	designationRepository.save(designation1);
//    	
//    	Salary salary = new Salary(1L, 30000l, 20000l);
//    	salaryRepository.save(salary);
//    	
//    	employee2.getDepartments().add(department1);
//    	employee2.setPermanentAddress(permanentAddress);
//    	employee2.setPresentAddress(presentAddress);
//    	employee2.setSalary(salary);
//    	employee2.getDesignation().add(designation1);
//    	
//        return erepository.save(employee2);
		return erepository.save(employee);
	}

	@GetMapping("/employee/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Employee> getEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		return ResponseEntity.ok().body(employee);
	}

	@GetMapping("/employees")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Employee> getAllEmployee() {
		return this.erepository.findAll();
	}

	@PutMapping("/employees/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeNumber,
			@RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

//        employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
//        employee.setFatherName(employeeDetails.getFatherName());
//        employee.setMotherName(employeeDetails.getMotherName());
//        employee.setDoB(employeeDetails.getDoB());
//        employee.setGender(employeeDetails.getGender());
//        employee.setNidNumber(employeeDetails.getNidNumber());
//        employee.setNationality(employeeDetails.getNationality());
		employee.setEmail(employeeDetails.getEmail());
//        employee.setMobileNumber(employeeDetails.getMobileNumber());
//        employee.setEmergencyContact(employeeDetails.getEmergencyContact());
		employee.setActive(employeeDetails.getActive());
		employee.setPermanentAddress(employeeDetails.getPermanentAddress());
		employee.setPresentAddress(employeeDetails.getPresentAddress());
		employee.setDepartment(employeeDetails.getDepartments());
		employee.setDesignation(employeeDetails.getDesignation());
//        employee.setLeave(employeeDetails.getLeave());

		final Employee updatedEmployee = erepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@PutMapping("/employee/{id}/address/permanent")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Employee> updateEmployeePermanentAddress(@PathVariable(value = "id") Long employeeId,
			@RequestBody AddressRequest permanentAddress) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		employee.setPermanent(permanentAddress.getAddress());
		final Employee updatedEmployee = erepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@GetMapping("/employee/{id}/address/permanent")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> getEmployeePermanentAddress(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		String permanentAddress = employee.getPermanent();
		if (permanentAddress == null) {
			throw new ResourceNotFoundException("Address not found");
		}
		return ResponseEntity.ok().body(permanentAddress);
	}

	@PutMapping("/employee/{id}/address/present")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Employee> updateEmployeePresentAddress(@PathVariable(value = "id") Long employeeId,
			@RequestBody AddressRequest presentAddress) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		employee.setPresent(presentAddress.getAddress());
		final Employee updatedEmployee = erepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@GetMapping("/employee/{id}/address/present")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> getEmployeePresentAddress(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		String presentAddress = employee.getPresent();
		if (presentAddress == null) {
			throw new ResourceNotFoundException("Address not found");
		}
		return ResponseEntity.ok().body(presentAddress);
	}

	@DeleteMapping("/employee/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeNumber)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeNumber).orElseThrow(
				() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeNumber));

		erepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@GetMapping("/employee/email/{email}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<String>> getEmployeeByEmail(@PathVariable(value = "email") String email)
			throws ResourceNotFoundException {
		List<Employee> employee = erepository.findByEmail(email);
		List<String> name = new ArrayList<String>();
		employee.stream().forEach((e) -> name.add(e.getFirstName()));
//        	.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + email))
//        return ResponseEntity.ok().body(employee);
		return ResponseEntity.ok().body(name);
	}

	@GetMapping("employee/active")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Employee> getAllActiveEmployee() {
		return this.erepository.findByActiveTrue();
	}

	@PostMapping("/employee/{id}/experience/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Employee> postExperience(@PathVariable(value = "id") Long employeeId,
			@RequestBody Experience experienceDetails) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		experienceDetails.setEmployee(employee);
		experienceRepository.save(experienceDetails);
		return ResponseEntity.ok().body(employee);
	}

	@PutMapping("/employee/{id1}/experience/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Experience> updateExperience(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long experienceId, @RequestBody Experience experienceDetails)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Experience experience = experienceRepository.findById(experienceId)
				.orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

		experience.setCompany(experienceDetails.getCompany());
		experience.setDesignation(experienceDetails.getDesignation());
		experience.setDuration(experienceDetails.getDuration());

		final Experience updatedExperience = experienceRepository.save(experience);
		return ResponseEntity.ok(updatedExperience);
	}

	@GetMapping("/employee/{id1}/experience/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Experience> getExperience(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long experienceId) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Experience experience = experienceRepository.findById(experienceId)
				.orElseThrow(() -> new ResourceNotFoundException("Experience not found"));
		return ResponseEntity.ok().body(experience);
	}

	@GetMapping("/employee/{id1}/experience/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Experience>> getAllExperience(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long experienceId) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

//		List<Experience> experience = experienceRepository.findByEmployeeId(employeeId);
		List<Experience> experiences = employee.getExperience();
		return ResponseEntity.ok().body(experiences);
	}

	@PostMapping("/employee/{id}/education/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Employee> postEducation(@PathVariable(value = "id") Long employeeId,
			@RequestBody Education educationDetails) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		educationDetails.setEmployee(employee);
		educationRepository.save(educationDetails);
		return ResponseEntity.ok().body(employee);
	}

	@PutMapping("/employee/{id1}/education/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Education> updateEducation(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long educationId, @RequestBody Education educationDetails)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Education education = educationRepository.findById(educationId)
				.orElseThrow(() -> new ResourceNotFoundException("Education not found"));

		education.setDegree(educationDetails.getDegree());
		education.setInstitute(educationDetails.getInstitute());
		education.setPassingYear(educationDetails.getPassingYear());
		education.setConcentration(educationDetails.getConcentration());

		final Education updatedEducation = educationRepository.save(education);
		return ResponseEntity.ok(updatedEducation);
	}

	@GetMapping("/employee/{id1}/education/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Education> getEducation(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long educationId) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Education education = educationRepository.findById(educationId)
				.orElseThrow(() -> new ResourceNotFoundException("Education not found"));
		return ResponseEntity.ok().body(education);
	}

	@GetMapping("/employee/{id1}/education/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Education>> getAllEducation(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

//		List<Education> education = educationRepository.findByEmployeeId(employeeId);
		List<Education> educations = employee.getEducation();
		return ResponseEntity.ok().body(educations);
	}

	@PostMapping("/employee/{id}/contact/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Employee> postContact(@PathVariable(value = "id") Long employeeId,
			@RequestBody Contact contactDetails) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		contactDetails.setEmployee(employee);
		contactRepository.save(contactDetails);
		return ResponseEntity.ok().body(employee);
	}

	@PutMapping("/employee/{id1}/contact/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Contact> updateContact(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long contactId, @RequestBody Contact contactDetails)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Contact contact = contactRepository.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

		contact.setContact(contactDetails.getContact());
		contact.setEmergencyContact(contactDetails.getEmergencyContact());
		contact.setRelation(contactDetails.getRelation());

		final Contact updatedContact = contactRepository.save(contact);
		return ResponseEntity.ok(updatedContact);
	}

	@GetMapping("/employee/{id1}/contact/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Contact> getContact(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long contactId) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Contact contact = contactRepository.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
		return ResponseEntity.ok().body(contact);
	}

	@GetMapping("/employee/{id1}/contact/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Contact>> getAllContact(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

//		List<Contact> contact = contactRepository.findByEmployeeId(employeeId);
		List<Contact> contacts = employee.getContact();
		return ResponseEntity.ok().body(contacts);
	}

	@GetMapping("/employee/{id1}/contact/emergency")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<String>> getEmergencyContact(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		List<Contact> contacts = employee.getContact();
		List<String> emergencyContacts = new ArrayList<String>();
		for (Contact contact : contacts) {
			emergencyContacts.add(contact.getEmergencyContact());
		}
		return ResponseEntity.ok().body(emergencyContacts);
	}

	@GetMapping("/employee/{id1}/contact/personal")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<String>> getPersonalContact(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Personal Contact not found"));

		List<Contact> contacts = employee.getContact();
		List<String> personalContacts = new ArrayList<String>();
		for (Contact contact : contacts) {
			personalContacts.add(contact.getContact());
		}
		return ResponseEntity.ok().body(personalContacts);
	}

	@PostMapping("/employee/{id}/certification/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Employee> postCertification(@PathVariable(value = "id") Long employeeId,
			@RequestBody Certification certificationDetails) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		certificationDetails.setEmployee(employee);
		certificationRepository.save(certificationDetails);
		return ResponseEntity.ok().body(employee);
	}

	@PutMapping("/employee/{id1}/certification/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Certification> updateCertification(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long certificationId, @RequestBody Certification certificationDetails)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Certification certification = certificationRepository.findById(certificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Certification not found"));

		certification.setTitle(certificationDetails.getTitle());
		certification.setDuration(certificationDetails.getDuration());

		final Certification updatedCertification = certificationRepository.save(certification);
		return ResponseEntity.ok(updatedCertification);
	}

	@GetMapping("/employee/{id1}/certification/{id2}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Certification> getCertification(@PathVariable(value = "id1") Long employeeId,
			@PathVariable(value = "id2") Long certificationId) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		Certification certification = certificationRepository.findById(certificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Certification not found"));
		return ResponseEntity.ok().body(certification);
	}

	@GetMapping("/employee/{id1}/certification/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Certification>> getAllCertification(@PathVariable(value = "id1") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		List<Certification> certifications = employee.getCertification();
		return ResponseEntity.ok().body(certifications);
	}

	@PutMapping("/employee/{id}/personal")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Employee> updatePersonalInformation(@PathVariable(value = "id") Long employeeId,
			@RequestBody PersonalInformation personalInformationDetails) throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		employee.setFatherName(personalInformationDetails.getFatherName());
		employee.setMotherName(personalInformationDetails.getMotherName());
//		employee.setBirthDate(personalInformationDetails.getBirthDate());

		final Employee updatedemployee = erepository.save(employee);
		return ResponseEntity.ok(updatedemployee);
	}

	@GetMapping("/employee/{id}/personal")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PersonalInformation> getPersonalInformation(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = erepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		PersonalInformation personalInformation = new PersonalInformation();
		
		personalInformation.setFatherName(employee.getFatherName());
		personalInformation.setMotherName(employee.getMotherName());

		return ResponseEntity.ok(personalInformation);
	}
}
