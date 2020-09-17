package com.dbl.nsl.erp.controllers;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Attendance;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.models.Group;
import com.dbl.nsl.erp.payload.response.GroupHrResponse;
import com.dbl.nsl.erp.repository.AttendanceRepository;
import com.dbl.nsl.erp.repository.DepartmnetRepository;
import com.dbl.nsl.erp.repository.DesignationRepository;
import com.dbl.nsl.erp.repository.GroupRepository;
import com.dbl.nsl.erp.repository.eRepository;

@RestController
@RequestMapping("/api/test")
public class AttendanceController {

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	eRepository employeeRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	DepartmnetRepository departmentRepository;

	@Autowired
	DesignationRepository designationRepository;

	public String attendanceStatus(Long Id, Time time) throws ParseException {

		String inTime = time.toString();

		Optional<Employee> optionalEmployee = employeeRepository.findById(Id);
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			String employeeInTime = employee.getEmployeeInTime();

			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date startTime = format.parse(employeeInTime);
			Date entryTime = format.parse(inTime);
			long difference = entryTime.getTime() - startTime.getTime();
			if (difference > 0) {
				return "late";
			} else if (difference <= 0) {
				return "present";
			} else {
				return "leave";
			}
		}
		throw new RuntimeException("employee not found");
	}

	public Attendance attendanceProperties(Long Id, Attendance attendance) {

		String employeeName = employeeRepository.findNameById(Id);

		Set<String> employeeDesignationSet = designationRepository.findDesignationByEmployeeId(Id);
		String employeeDesignation = String.join(", ", employeeDesignationSet);
		Set<String> employeeDepartmentSet = departmentRepository.findDepartmentByEmployeeId(Id);
		String employeeDepartment = String.join(", ", employeeDepartmentSet);
		Set<String> employeeGroupSet = groupRepository.findGroupNameByEmployeeId(Id);
		String employeeGroup = String.join(",", employeeGroupSet);
		Set<Long> employeeGroupIdSet = groupRepository.findGroupIdByEmployeeName(Id);
		for (Long eid : employeeGroupIdSet) {
			attendance.setGroupId(eid);
		}

		attendance.setEmployeeName(employeeName);
		attendance.setDesignationName(employeeDesignation);
		attendance.setDepartmentName(employeeDepartment);
		attendance.setGroupName(employeeGroup);

		return attendance;

	}

	@PostMapping("/attendances")
	@PreAuthorize("hasRole('ADMIN')")
	public Attendance employeeAttendance(@RequestBody Attendance attendance) throws ParseException {
//		String fixedTime = "09:00:00";

		Optional<Attendance> attended = attendanceRepository.findByEmployeeIdAndDate(attendance.getEmployeeId(),
				attendance.getDate());

		if (attended.isEmpty()) {
			String status = attendanceStatus(attendance.getEmployeeId(), attendance.getInTime());
			attendance = attendanceProperties(attendance.getEmployeeId(), attendance);
			attendance.setStatus(status);

			return attendanceRepository.save(attendance);
		}
		throw new RuntimeException("employee found");
	}

	@GetMapping("/attendances/employee/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<Attendance> getAllByDateBetween(@PathVariable(value = "id") Long employeeNo,
			@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
			@RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) {

		return attendanceRepository.findByEmployeeIdAndDateBetween(employeeNo, startdate, enddate);
	}

	@GetMapping("/attendances/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Attendance> getAllByDate(@RequestParam("group") String groupName,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		return attendanceRepository.findByGroupNameAndDate(groupName, date);
	}

	@GetMapping("/attendances/group-admin")
	@PreAuthorize("hasRole('ADMIN')")
	public List<GroupHrResponse> getAllGroupByDate(
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

		List<Long> groupIds = groupRepository.findGroupId();

		List<GroupHrResponse> groupHrResponseList = new ArrayList<GroupHrResponse>();

		for (Long groupId : groupIds) {
			Long presentEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("present", date, groupId);
			Long lateEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("late", date, groupId);
			Long absentEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("absent", date, groupId);
//			Long sickEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("sick", date, groupId);
//			Long casualEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("casual", date, groupId);
//			Long annualEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("annual", date, groupId);
			Long leaveEmpCount = attendanceRepository.countByStatusAndDateAndGroupId("leave", date, groupId);
			String groupName = groupRepository.findGroupNameByGroupId(groupId);

			Long totalEmp = presentEmpCount + lateEmpCount + absentEmpCount;

			GroupHrResponse groupHrResponse = new GroupHrResponse();
			groupHrResponse.setTotalEmployee(totalEmp);
			groupHrResponse.setTotalPresentEmployee(presentEmpCount);
			groupHrResponse.setTotalLateEmployee(lateEmpCount);
			groupHrResponse.setTotalAbsentEmployee(absentEmpCount);
			groupHrResponse.setGroupName(groupName);
			groupHrResponse.setDate(date);
//			groupHrResponse.setTotalSickLeaveEmployee(sickEmpCount);
//			groupHrResponse.setTotalCasualLeaveEmployee(casualEmpCount);
//			groupHrResponse.setTotalAnnualLeaveEmployee(annualEmpCount);
			groupHrResponse.setTotalLeaveEmployee(leaveEmpCount);
			groupHrResponseList.add(groupHrResponse);
		}
		return groupHrResponseList;
	}

//	@Scheduled(cron = "0 0 1 * * ?") //at night 1 am everyday
//	@Scheduled(cron = "0/50 * * * * ?") // every 50 seconds
	public void publish() {
		System.out.println("publish......");
		Date date = new Date();

		List<Long> employeeIds = employeeRepository.findEmployeeId();

		for (Long employeeId : employeeIds) {
			Attendance attendance = new Attendance();

			attendance = attendanceProperties(employeeId, attendance); // add properties name, department, designation,
																		// group, group id

			attendance.setDate(date);
			attendance.setEmployeeId(employeeId);
			attendance.setInTime(null);
			attendance.setOutTime(null);
			attendance.setStatus("absent");

			attendanceRepository.save(attendance);
		}
	}

	@PutMapping("/attendances")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Attendance> attendanceResponse(@RequestBody Attendance attendanceDetails)
			throws ResourceNotFoundException, ParseException {
		Optional<Attendance> attendance = attendanceRepository
				.findByEmployeeIdAndDate(attendanceDetails.getEmployeeId(), attendanceDetails.getDate());

		if (attendance.isPresent()) {
			Attendance updatedAttendance = attendance.get();

			updatedAttendance.setInTime(attendanceDetails.getInTime());
			updatedAttendance.setOutTime(attendanceDetails.getOutTime());

			String status = attendanceStatus(attendanceDetails.getEmployeeId(), updatedAttendance.getInTime());

			updatedAttendance.setStatus(status);

			final Attendance attendanceResponse = attendanceRepository.save(updatedAttendance);
			return ResponseEntity.ok(attendanceResponse);
		}
		throw new RuntimeException("employee not found");
	}

	@GetMapping("/attendance/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Attendance> getAllAttendance() {
		return this.attendanceRepository.findAll();
	}
}
