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
import com.dbl.nsl.erp.models.Ssc;
import com.dbl.nsl.erp.repository.SscRepository;

@RestController
@RequestMapping("/api/test")
public class SscController {
	@Autowired
	private SscRepository sscRepository;
	
    @PostMapping("/ssc/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Ssc employeeSsc(@RequestBody Ssc ssc) {
		return sscRepository.save(ssc);
    }
    
    @GetMapping("/ssc/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Ssc>> getSscById(@PathVariable(value = "id") Long sscId)
        throws ResourceNotFoundException {
        Optional<Ssc> ssc = sscRepository.findById(sscId);
        return ResponseEntity.ok().body(ssc);
    }
    
	@GetMapping("/ssc/findall")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Ssc> getAllSsc(){
		return this.sscRepository.findAll();
	}
	
    @PutMapping("/ssc/edit/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Ssc> updateSsc(@PathVariable(value = "id") Long sscId,
         @RequestBody Ssc sscDetails) throws ResourceNotFoundException {
         Ssc ssc = sscRepository.findById(sscId)
        .orElseThrow(() -> new ResourceNotFoundException("SSC not found for this id :: " + sscId));

        ssc.setId(sscDetails.getId());
        ssc.setSchoolName(sscDetails.getSchoolName());
        ssc.setEmployee(sscDetails.getEmployee());

        final Ssc updatedSsc = sscRepository.save(ssc);
        return ResponseEntity.ok(updatedSsc);
    }
    
    @DeleteMapping("/ssc/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Map<String, Boolean> deleteSsc(@PathVariable(value = "id") Long sscId)
         throws ResourceNotFoundException {
        Ssc ssc = sscRepository.findById(sscId)
       .orElseThrow(() -> new ResourceNotFoundException("SSC not found for this id :: " + sscId));

        sscRepository.delete(ssc);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
