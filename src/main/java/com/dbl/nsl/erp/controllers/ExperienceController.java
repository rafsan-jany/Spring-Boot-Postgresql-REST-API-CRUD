package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.models.Experience;
import com.dbl.nsl.erp.models.Location;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.ExperienceRepository;
import com.dbl.nsl.erp.repository.LocationRepository;

@RestController
//@RequestMapping("/api/test")
public class ExperienceController {
	@Autowired
	ExperienceRepository experienceRepository;
	
	@PostMapping("/experience/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> postExperience(@RequestBody Experience experience) {
//		if (locationRepository.existsByName(location.getAddress())) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Company already exists"));
//		}
		experienceRepository.save(experience);
		return ResponseEntity.ok(new MessageResponse("experience registered successfully"));
    }

}
