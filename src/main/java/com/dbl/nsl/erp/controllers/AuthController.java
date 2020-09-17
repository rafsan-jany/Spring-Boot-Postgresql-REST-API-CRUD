package com.dbl.nsl.erp.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.exception.ResourceNotFoundException;
import com.dbl.nsl.erp.models.ERole;
import com.dbl.nsl.erp.models.Role;
import com.dbl.nsl.erp.models.User;
import com.dbl.nsl.erp.payload.request.LoginRequest;
import com.dbl.nsl.erp.payload.request.SignupRequest;
import com.dbl.nsl.erp.payload.request.UpdatePasswordRequest;
import com.dbl.nsl.erp.payload.request.UpdateSignupRequest;
import com.dbl.nsl.erp.payload.response.JwtResponse;
import com.dbl.nsl.erp.payload.response.MessageResponse;
import com.dbl.nsl.erp.repository.RoleRepository;
import com.dbl.nsl.erp.repository.UserRepository;
import com.dbl.nsl.erp.security.jwt.JwtUtils;
import com.dbl.nsl.erp.security.services.UserDetailsImpl;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public Set<Role> getRolesSet(Set<String> strRoles) {
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Role is not found"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(adminRole);

					break;
				case "assistant_admin":
					System.out.println(ERole.ROLE_ADMIN);
					Role assistantAdminRole = roleRepository.findByName(ERole.ROLE_ASSISTANT_ADMIN)
							.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(assistantAdminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Role is not found"));
					roles.add(userRole);
				}
			});
		}
		return roles;
	}

	@PostMapping("/account/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/account/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Username already used"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Email already used"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		roles = getRolesSet(strRoles);

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PutMapping("/account/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable(value = "username") String username,
			@Valid @RequestBody UpdateSignupRequest updateUser) throws ResourceNotFoundException {

//		if (!userRepository.existsByUsername(username)) {
//			return ((BodyBuilder) ResponseEntity.notFound()).body(new MessageResponse("Employee not found"));	
//		}

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		if (!userRepository.existsByEmail(updateUser.getEmail())) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new MessageResponse("Email not found"));
		}

		if (!userRepository.existsByUsernameAndEmail(username, updateUser.getEmail())) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(new MessageResponse("Username and Email mismatch"));
		}

		Set<String> strRoles = updateUser.getNewRole();
		Set<Role> roles = new HashSet<>();
		roles = getRolesSet(strRoles);

		boolean isPasswordMatch = passwordEncoder.matches(updateUser.getPassword(), user.getPassword());
		if (!isPasswordMatch) {
			return ResponseEntity.badRequest().body(new MessageResponse("Old password mismatch"));
		}

		user.setEmail(updateUser.getNewEmail());
		user.setPassword(encoder.encode(updateUser.getNewPassword()));
		user.setRoles(roles);

		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/account/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> getEmployeeByEmployeeNumber(@PathVariable(value = "username") String username)
			throws ResourceNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		return ResponseEntity.ok().body(user);
	}

	@DeleteMapping("/account/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteUserAccount(@PathVariable(value = "username") String username)
			throws ResourceNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PutMapping("/account/{username}/updatepassword")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updatePassword(@PathVariable(value = "username") String username,
			@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) throws ResourceNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

		boolean isPasswordMatch = passwordEncoder.matches(updatePasswordRequest.getPassword(), user.getPassword());
		if (!isPasswordMatch) {
			return ResponseEntity.badRequest().body(new MessageResponse("Old password mismatch"));
		}

		user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
}
