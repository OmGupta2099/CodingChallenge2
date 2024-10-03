package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Entity.TasksInfo;
import com.example.demo.Entity.TasksInfo.Priority;
import com.example.demo.Entity.TasksInfo.Status;
import com.example.demo.Exception.TaskAlreadyExistsException;
import com.example.demo.Exception.TaskNotFoundException;
import com.example.demo.Service.TasksInfoService;

@SpringBootTest
class CodingChallenge2ApplicationTests {

	@Autowired
	private TasksInfoService tasksInfoService;

	@Test
	void addTask() throws TaskAlreadyExistsException {
		TasksInfo task = new TasksInfo();
		task.setTaskId("6");
		task.setTitle("Testing API");
		task.setDescription("Check API in Junit");
		task.setPriority(Priority.Medium);
		task.setStatus(Status.Completed);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.SEPTEMBER, 17);
		Date dueDate = calendar.getTime();
		task.setDueDate(dueDate);
		String response = tasksInfoService.addTask(task);
		assertEquals("Task added successfully", response, "Task should be added successfully");
	}

	@Test
	void deleteTask() throws TaskNotFoundException {
		String response = tasksInfoService.deleteTask("6");
		assertEquals("Task deleted successfully", response, "Task should be deleted successfully");
	}

	@Test
	void getAllTasks() {
		List<TasksInfo> actualTaskList = tasksInfoService.getAllTasks();
		int listSize = actualTaskList.size();

		assertEquals(5, listSize);
	}

	@Test
	void getTaskById() throws TaskNotFoundException {
		TasksInfo actualTask = tasksInfoService.getTaskById("1");
		assertEquals("CRUD of coding challenge", actualTask.getTitle(), "Task title should match the expected value");
	}

	@Test
	void updateTask() throws TaskNotFoundException {
		TasksInfo taskDetails = new TasksInfo();
		taskDetails.setTaskId("333");
		taskDetails.setTitle("CRUD of coding challenge");
		taskDetails.setDescription("Create CRUD API for given challenge");
		taskDetails.setPriority(Priority.High);
		taskDetails.setStatus(Status.Completed);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, Calendar.SEPTEMBER, 30);
		Date dueDate = calendar.getTime();
		taskDetails.setDueDate(dueDate);
		String response = tasksInfoService.updateTask(taskDetails);
		assertEquals("Task updated successfully", response, "Task should be updated successfully");
	}

}
