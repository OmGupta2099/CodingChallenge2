package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.TasksInfo;
import com.example.demo.Exception.TaskAlreadyExistsException;
import com.example.demo.Exception.TaskNotFoundException;
import com.example.demo.Service.TasksInfoService;

@RestController
@RequestMapping("/tasks")
public class TasksInfoController {

	@Autowired
	private TasksInfoService tasksService;

	@GetMapping("/allTasks")
	@PreAuthorize("hasAuthority('User')")
	public ResponseEntity<List<TasksInfo>> getAllTasks() {
		List<TasksInfo> tasks = tasksService.getAllTasks();
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@GetMapping("/searchTask")
	@PreAuthorize("hasAuthority('User')")
	public ResponseEntity<?> getTaskById(@RequestParam("taskId") String id) {
		try {
			TasksInfo task = tasksService.getTaskById(id);
			return new ResponseEntity<>(task, HttpStatus.OK);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/newTask")
	@PreAuthorize("hasAuthority('User')")
	public ResponseEntity<String> addTask(@RequestBody TasksInfo task) {
		try {
			String message = tasksService.addTask(task);
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (TaskAlreadyExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/updateTask")
	@PreAuthorize("hasAuthority('User')")
	public ResponseEntity<String> updateTask(@RequestBody TasksInfo taskDetails) {
		try {
			String message = tasksService.updateTask(taskDetails);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteTask")
	@PreAuthorize("hasAuthority('User')")
	public ResponseEntity<String> deleteTask(@RequestParam("taskId") String id) {
		try {
			String message = tasksService.deleteTask(id);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (TaskNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
