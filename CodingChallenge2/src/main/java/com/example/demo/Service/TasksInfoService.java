package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.TasksInfo;
import com.example.demo.Exception.TaskAlreadyExistsException;
import com.example.demo.Exception.TaskNotFoundException;
import com.example.demo.Repository.TasksInfoRepository;

@Service
public class TasksInfoService {

	@Autowired
	private TasksInfoRepository tasksRepo;

	public List<TasksInfo> getAllTasks() {
		return tasksRepo.findAll();
	}

	public TasksInfo getTaskById(String taskId) throws TaskNotFoundException {
		return tasksRepo.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found."));
	}

	public String addTask(TasksInfo task) throws TaskAlreadyExistsException {
		if (tasksRepo.existsById(task.getTaskId())) {
			throw new TaskAlreadyExistsException("Task with ID " + task.getTaskId() + " already exists.");
		}
		tasksRepo.save(task);
		return "Task added successfully";
	}

	public String updateTask(TasksInfo taskDetails) throws TaskNotFoundException {
		Optional<TasksInfo> existingTask = tasksRepo.findById(taskDetails.getTaskId());
		if (existingTask.isPresent()) {
			TasksInfo task = existingTask.get();
			task.setTitle(taskDetails.getTitle());
			task.setDescription(taskDetails.getDescription());
			task.setDueDate(taskDetails.getDueDate());
			task.setPriority(taskDetails.getPriority());
			task.setStatus(taskDetails.getStatus());
			tasksRepo.save(task);
			return "Task updated successfully";
		} else {
			throw new TaskNotFoundException("Task with ID " + taskDetails.getTaskId() + " not found.");
		}
	}

	public String deleteTask(String taskId) throws TaskNotFoundException {
		if (tasksRepo.existsById(taskId)) {
			tasksRepo.deleteById(taskId);
			return "Task deleted successfully";
		}
		throw new TaskNotFoundException("Task with ID " + taskId + " not found.");
	}
}
