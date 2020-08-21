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
import com.dbl.nsl.erp.models.Holiday;
import com.dbl.nsl.erp.models.LeaveConsume;
import com.dbl.nsl.erp.repository.LeaveConsumeRepository;

@RestController
@RequestMapping("/api/test")
public class LeaveConsumeController {
	
	@Autowired
	private LeaveConsumeRepository leaveConsumeRepository;
	
	@PostMapping("/leaveconsume/save")
    @PreAuthorize("hasRole('ADMIN')")
    public LeaveConsume employeeLeaveConsume(@RequestBody LeaveConsume leaveConsume) {
		return leaveConsumeRepository.save(leaveConsume);
    }
	
	@GetMapping("/leaveconsume/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<LeaveConsume>> getHolidayById(@PathVariable(value = "id") Long leaveConsumeId)
        throws ResourceNotFoundException {
        Optional<LeaveConsume> leaveConsume = leaveConsumeRepository.findById(leaveConsumeId);
        return ResponseEntity.ok().body(leaveConsume);
    }
    
	@GetMapping("/leaveconsume/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<LeaveConsume> getAllDepartment(){
		return this.leaveConsumeRepository.findAll();
	}
	
    @PutMapping("/leaveconsume/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeaveConsume> updateLeaveConsume(@PathVariable(value = "id") Long leaveConsumeId,
         @RequestBody LeaveConsume leaveConsumeDetails) throws ResourceNotFoundException {
    	 LeaveConsume leaveConsume = leaveConsumeRepository.findById(leaveConsumeId)
        .orElseThrow(() -> new ResourceNotFoundException("Leave Consume not found for this id :: " + leaveConsumeId));
    	 
    	leaveConsume.setStartDate(leaveConsumeDetails.getStartDate());
    	leaveConsume.setIsAccepted(leaveConsumeDetails.getIsAccepted());
    	leaveConsume.setEmployee(leaveConsumeDetails.getEmployee());

        final LeaveConsume updatedLeaveConsume= leaveConsumeRepository.save(leaveConsume);
        return ResponseEntity.ok(updatedLeaveConsume);
    }
    
    @DeleteMapping("/leaveconsume/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteLeaveConsume(@PathVariable(value = "id") Long leaveConsumeId)
         throws ResourceNotFoundException {
        LeaveConsume leaveConsume = leaveConsumeRepository.findById(leaveConsumeId)
       .orElseThrow(() -> new ResourceNotFoundException("Leave Consume not found for this id :: " + leaveConsumeId));

        leaveConsumeRepository.delete(leaveConsume);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
