package com.dbl.nsl.erp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Department;
import com.dbl.nsl.erp.models.Holiday;
import com.dbl.nsl.erp.repository.HolidayRepository;

@RestController
@RequestMapping("/api/test")
public class HolidayController {
	
	@Autowired
	private HolidayRepository holidayRepository;
	
	@PostMapping("/holiday/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Holiday employeeHoliday(@RequestBody Holiday holiday) {
		return holidayRepository.save(holiday);
    }
	
	@GetMapping("/holiday/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Holiday>> getHolidayById(@PathVariable(value = "id") Long holidayId)
        throws ResourceNotFoundException {
        Optional<Holiday> holiday = holidayRepository.findById(holidayId);
        return ResponseEntity.ok().body(holiday);
    }
    
	@GetMapping("/holiday/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Holiday> getAllDepartment(){
		return this.holidayRepository.findAll();
	}
	
    @PutMapping("/holiday/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Holiday> updateHoliday(@PathVariable(value = "id") Long holidayId,
         @RequestBody Holiday holidayDetails) throws ResourceNotFoundException {
    	 Holiday holiday = holidayRepository.findById(holidayId)
        .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for this id :: " + holidayId));

        holiday.setHolidayTitle(holidayDetails.getHolidayTitle());
        holiday.setHolidayStartDate(holidayDetails.getHolidayStartDate());
        holiday.setHolidayEndDate(holidayDetails.getHolidayEndDate());
        holiday.setHolidayDuration(holidayDetails.getHolidayDuration());
        holiday.setHolidayDescription(holidayDetails.getHolidayDescription());

        final Holiday updatedHoliday= holidayRepository.save(holiday);
        return ResponseEntity.ok(updatedHoliday);
    }
    
    @DeleteMapping("/holiday/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteHoliday(@PathVariable(value = "id") Long holidayId)
         throws ResourceNotFoundException {
        Holiday holiday = holidayRepository.findById(holidayId)
       .orElseThrow(() -> new ResourceNotFoundException("Holiday not found for this id :: " + holidayId));

        holidayRepository.delete(holiday);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
