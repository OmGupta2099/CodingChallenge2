package com.example.demo;

public class TaskRequest {

	private String taskId;
	private String userId;

	public TaskRequest(String taskId, String userId) {
		super();
		this.taskId = taskId;
		this.userId = userId;
	}

	public TaskRequest() {
		super();
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
