package com.dbl.nsl.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbl.nsl.erp.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	boolean existsByName(String name);

}
