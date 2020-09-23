package com.dbl.nsl.erp.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Attendance;
import com.dbl.nsl.erp.models.Employee;
import com.dbl.nsl.erp.payload.response.GroupHrResponse;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByEmployeeIdAndDateBetween(Long employeeId, Date dateTimeStart, Date dateTimeEnd);

	List<Attendance> findAllByDate(Date date);

	List<Attendance> findByGroupNameAndDate(String groupName, Date date);

	Long countByStatus(String present);

	Long countByStatusAndDateAndCompanyId(String status, Date date, Long companyId);

	@Query( value = "select attendances.employee_id from attendances where group_name = ?1 and date = ?2", nativeQuery = true)
	List<Long> findEmployeeIdByGroupNameAndDate(String groupName, Date date);

	Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, Date date);

	Optional<Attendance> findByEmployeeId(Long employeeId);

	List<Attendance> findByCompanyNameAndDate(String companyName, Date date);

	List<Attendance> findByCompanyIdAndDate(Long companyId, Date date);

	List<Attendance> findByDepartmentNameAndDate(String departmentName, Date date);
}
