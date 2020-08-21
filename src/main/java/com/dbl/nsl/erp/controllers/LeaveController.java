package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.models.Leave;
import com.dbl.nsl.erp.repository.LeaveRepository;

@RestController
@RequestMapping("/api/test")
public class LeaveController {
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@PostMapping("/leave/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Leave employeeLeave(@RequestBody Leave leave) {
		return leaveRepository.save(leave);
    }
}
