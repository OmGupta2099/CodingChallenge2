package com.example.demo.Exception;

public class TaskAlreadyAllocatedException extends Exception {

	private static final long serialVersionUID = 1L;

	public TaskAlreadyAllocatedException(String message) {
		super(message);
	}
}
