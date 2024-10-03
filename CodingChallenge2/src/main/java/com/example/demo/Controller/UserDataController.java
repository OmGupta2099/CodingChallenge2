package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.demo.AuthRequest;
import com.example.demo.JwtService;
import com.example.demo.TaskRequest;
import com.example.demo.Entity.UserData;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Exception.TaskNotFoundException;
import com.example.demo.Service.UserDataService;

@RestController
public class UserDataController {

	@Autowired
	private UserDataService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<String> addNewUser(@RequestBody UserData userInfo) {
		try {
			String response = service.addUser(userInfo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				String token = jwtService.generateToken(authRequest.getUsername());
				return new ResponseEntity<>(token, HttpStatus.OK);
			} else {
				throw new UsernameNotFoundException("Invalid user request!");
			}
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/tasksToUser")
	@PreAuthorize("hasAuthority('User')")
	public ResponseEntity<String> addTaskToUser(@RequestBody TaskRequest tasker) {
		try {
			String response = service.addTaskToUser(tasker.getUserId(), tasker.getTaskId());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (UserNotFoundException | TaskNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserData userDetails) {
		try {
			String response = service.updateUserDetails(userDetails);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/userProfile")
	public ResponseEntity<?> getUserDetails(@RequestParam("userId") String userId) {
		try {
			UserData user = service.getUserDetails(userId);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/removeUser")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<String> removeUser(@RequestBody UserData userInfo) {
		try {
			String response = service.removeUser(userInfo);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
