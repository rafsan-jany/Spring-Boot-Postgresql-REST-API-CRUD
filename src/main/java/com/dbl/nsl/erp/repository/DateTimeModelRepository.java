package com.dbl.nsl.erp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbl.nsl.erp.models.DateTimeModel;

public interface DateTimeModelRepository extends JpaRepository<DateTimeModel, Long> {

	List<DateTimeModel> findAllByDatetimeBetween(Date dateTimeStart, Date dateTimeEnd);

	@Query("select d from DateTimeModel d where d.datetime <= :datetime")
	List<DateTimeModel> findAllWithDatetimeBefore(@Param("datetime") Date datetime);

}
