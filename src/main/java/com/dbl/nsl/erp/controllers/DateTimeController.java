package com.dbl.nsl.erp.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.repository.DateTimeModelRepository;
import com.dbl.nsl.erp.models.DateTimeModel;

@RestController
@RequestMapping("/api/test")
public class DateTimeController {

	@Autowired
	DateTimeModelRepository dateTimeRepository;

	@PostMapping("/postdatetime")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String postDateTimeMode(@RequestBody DateTimeModel datetime) {
		dateTimeRepository.save(datetime);
		return "Done!";
	}

	@GetMapping("/getdatetime")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Iterable<DateTimeModel> getDateTimeModel() {
		return dateTimeRepository.findAll();
	}

	@GetMapping("/getallbydatetimebetween")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<DateTimeModel> getAllByDatetimeBetween(
			@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startdate,
			@RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date enddate) {

		return dateTimeRepository.findAllByDatetimeBetween(startdate, enddate);
	}

	@GetMapping("/getallwithdatetimebefore")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<DateTimeModel> getAllWithDatetimeBefore(
			@RequestParam("datetime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date datetime) {

		return dateTimeRepository.findAllWithDatetimeBefore(datetime);
	}
}
