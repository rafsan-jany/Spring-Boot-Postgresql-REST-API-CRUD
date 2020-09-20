package com.dbl.nsl.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	List<Location> findByCompanyId(Long companyId);

}
