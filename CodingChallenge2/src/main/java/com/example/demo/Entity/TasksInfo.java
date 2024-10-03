package com.example.demo.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;

@Entity
public class TasksInfo {

	@Id
	private String taskId;

	@NotBlank
	private String title;

	private String description;

	@NotBlank
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	public enum Priority {
		Low, Medium, High
	}

	public enum Status {
		Pending, In_Progress, Completed
	}

	@NotBlank
	private Priority priority;
	@NotBlank
	private Status status;

	public TasksInfo() {
		super();
	}

	public TasksInfo(String title, String description, Date dueDate, Priority priority, Status status) {
		super();
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
