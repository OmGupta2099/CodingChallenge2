package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.Entity.UserData;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Service.UserDataService;

@Component
public class AdminInitializer implements CommandLineRunner {

	@Autowired
	private UserDataService userDataService;

	@Override
	public void run(String... args) throws Exception {
		initializeAdmin();
	}

	private void initializeAdmin() throws UserAlreadyExistsException {
		try {
			UserData existingAdmin = userDataService.getUserById("admin");

			if (existingAdmin != null) {
				userDataService.removeUser(existingAdmin);
			}
		} catch (UserNotFoundException e) {
		}

		UserData admin = new UserData();
		admin.setUserId("admin");
		admin.setName("Admin");
		admin.setRoles("Admin");
		admin.setPassword("admin");

		userDataService.addUser(admin);
	}
}
