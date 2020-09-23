package com.dbl.nsl.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dbl.nsl.erp.models.Education;

public interface EducationRepository extends JpaRepository<Education, Long>{

//	List<Education> findByEmployeeId(Long employeeId);

}
