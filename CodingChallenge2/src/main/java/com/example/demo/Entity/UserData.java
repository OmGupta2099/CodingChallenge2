package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class UserData {

	@Id
	private String userId;

	@NotBlank
	private String name;

	@NotBlank
	private String password;
	private String roles = "User";

	@OneToMany(cascade = CascadeType.ALL)
	private List<TasksInfo> tasks = new ArrayList<TasksInfo>();

	public UserData() {
	}

	public UserData(String userId, String name, String password, List<TasksInfo> tasks) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.tasks = tasks;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TasksInfo> getTasks() {
		return tasks;
	}

	public void setTasks(List<TasksInfo> tasks) {
		this.tasks = tasks;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
