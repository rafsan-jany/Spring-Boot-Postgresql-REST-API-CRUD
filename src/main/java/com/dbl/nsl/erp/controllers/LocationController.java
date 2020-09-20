package com.dbl.nsl.erp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ForbiddenException;
import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.Company;
import com.dbl.nsl.erp.models.Location;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.LocationRepository;

@RestController
//@RequestMapping("/api/test")
public class LocationController {
	@Autowired
	LocationRepository locationRepository;
	
	@PostMapping("/location/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> companyLocation(@RequestBody Location location) {
//		if (locationRepository.existsByName(location.getAddress())) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Company already exists"));
//		}
		locationRepository.save(location);
		return ResponseEntity.ok(new MessageResponse("Location registered successfully"));
    }
    
    @GetMapping("/location/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Location> getLocation(@PathVariable(value = "id") Long locationId)
        throws ResourceNotFoundException {
        Location location = locationRepository.findById(locationId)
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));
        return ResponseEntity.ok().body(location);
    }
    
	@GetMapping("/location/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Location> getAllLocation() throws ForbiddenException {
		return this.locationRepository.findAll();
	}
	
    @PutMapping("/location/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Location> updateLocation(@PathVariable(value = "id") Long locationId,
         @RequestBody Location locationDetails) throws ResourceNotFoundException {
         Location location = locationRepository.findById(locationId)
        .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        location.setAlias(locationDetails.getAlias());
        location.setDistrict(locationDetails.getDistrict());
        location.setThana(locationDetails.getThana());
        location.setAddress(locationDetails.getAddress());
        final Location updatedLocation = locationRepository.save(location);
        return ResponseEntity.ok(updatedLocation);
    }
    
    @DeleteMapping("/location/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteLocation(@PathVariable(value = "id") Long locationId)
         throws ResourceNotFoundException {
        Location location = locationRepository.findById(locationId)
       .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        locationRepository.delete(location);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
