package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.UserDataDetails;
import com.example.demo.Entity.TasksInfo;
import com.example.demo.Entity.UserData;
import com.example.demo.Exception.TaskNotFoundException;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Repository.TasksInfoRepository;
import com.example.demo.Repository.UserDataRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDataService implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserDataRepository userRepo;

	@Autowired
	private TasksInfoRepository taskRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserData> userDetail = userRepo.findById(username);
		return userDetail.map(UserDataDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	@Transactional
	public String addUser(UserData userData) throws UserAlreadyExistsException {
		if (userRepo.existsById(userData.getUserId())) {
			throw new UserAlreadyExistsException("User with ID " + userData.getUserId() + " already exists.");
		}

		userData.setPassword(encoder.encode(userData.getPassword()));

		List<TasksInfo> tasks = userData.getTasks();
		if (tasks != null) {
			taskRepo.saveAll(tasks);
		}

		userRepo.save(userData);
		return "User added successfully.";
	}

	public UserData getUserById(String employeeId) throws UserNotFoundException {
		return userRepo.findById(employeeId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + employeeId + " not found."));
	}

	public String addTaskToUser(String userId, String taskId) throws UserNotFoundException, TaskNotFoundException {
		UserData user = userRepo.findByUserId(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " does not exist."));

		TasksInfo task = taskRepo.findByTaskId(taskId)
				.orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " does not exist."));

		List<TasksInfo> tasks = user.getTasks();
		if (tasks == null) {
			tasks = new ArrayList<>();
		}
		tasks.add(task);
		user.setTasks(tasks);
		userRepo.save(user);

		return "Task added to user successfully.";
	}

	public String removeUser(UserData userData) throws UserNotFoundException {
		if (!userRepo.existsById(userData.getUserId())) {
			throw new UserNotFoundException("User with ID " + userData.getUserId() + " does not exist.");
		}
		userRepo.delete(userData);
		return "User removed successfully.";
	}

	public String updateUserDetails(UserData userDetails) throws UserNotFoundException {
		Optional<UserData> existingUser = userRepo.findById(userDetails.getUserId());
		if (existingUser.isPresent()) {
			UserData user = existingUser.get();
			user.setName(userDetails.getName());
			user.setPassword(userDetails.getPassword());
			// Update any other fields as necessary
			userRepo.save(user);
			return "User details updated successfully";
		} else {
			throw new UserNotFoundException("User not found");
		}
	}

	public UserData getUserDetails(String userId) throws UserNotFoundException {
		return userRepo.findByUserId(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
	}
}
