package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.models.Group;
import com.dbl.nsl.erp.repository.GroupRepository;

@RestController
@RequestMapping("/api/test")
public class GroupController {
	@Autowired
	private GroupRepository groupRepository;

	@PostMapping("/group/save")
	@PreAuthorize("hasRole('ADMIN')")
	public Group employeeGroup(@RequestBody Group group) {
		return groupRepository.save(group);
	}

}
