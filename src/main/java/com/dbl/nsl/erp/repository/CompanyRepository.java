package com.dbl.nsl.erp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	boolean existsByName(String name);

	@Query( value = "select companies.name from companies join employees_companies on companies.company_id = employees_companies.company_id join employees on employees_companies.employee_id = ?1", nativeQuery = true)
	Set<String> findNameByEmployeeId(Long id);

	@Query( value = "select companies.company_id from companies join employees_companies on companies.company_id = employees_companies.company_id join employees on employees_companies.employee_id = ?1", nativeQuery = true)
	Set<Long> findCompanyIdByEmployeeId(Long employeeId);
	
	@Query( value = "select groups.group_name from groups where group_id = ?1", nativeQuery = true)
	String findCompanyNameByCompanyId(Long companyId);

	@Query(value = "select companies.company_id from companies", nativeQuery = true)
	List<Long> findCompanyId();
}
