package com.dbl.nsl.erp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	@Query( value = "select groupnames.group_name from groupnames", nativeQuery = true)
	List<String> findAllGroupName();
	
	@Query( value = "select groups.group_name from groups join employees_groups on groups.group_id = employees_groups.group_id join employees on employees_groups.employee_id = ?1", nativeQuery = true)
	Set<String> findGroupNameByEmployeeId(Long employeeId);

	@Query( value = "select groups.group_id from groups join employees_groups on groups.group_id = employees_groups.group_id join employees on employees_groups.employee_id = ?1", nativeQuery = true)
	Set<Long> findGroupIdByEmployeeId(Long employeeId);

	@Query( value = "select groups.group_name from groups where group_id = ?1", nativeQuery = true)
	String findGroupNameByGroupId(Long groupId);

	@Query(value = "select groups.group_id from groups", nativeQuery = true)
	List<Long> findGroupId();

//	@Query( value = "select groups from groups join employees_groups on groups.group_id = employees_groups.group_id join employees on employees_groups.employee_id = ?1", nativeQuery = true)
//	Set<Group> findGroupByEmployeeId(Long employeeId);
}
