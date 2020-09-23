package com.dbl.nsl.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbl.nsl.erp.models.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long>{

//	List<Experience> findByEmployeeId(Long employeeId);

}
