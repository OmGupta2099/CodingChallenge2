package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<Object> handleEmployeeNotFoundException(UserNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler({ UserAlreadyExistsException.class })
	public ResponseEntity<Object> handleEmployeeAlreadyExistsException(UserAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}

	@ExceptionHandler({ TaskNotFoundException.class })
	public ResponseEntity<Object> handleAssetNotFoundException(TaskNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler({ TaskAlreadyExistsException.class })
	public ResponseEntity<Object> handleInvalidEmailException(TaskAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler({ TaskAlreadyAllocatedException.class })
	public ResponseEntity<Object> handleAssetAlreadyAllocatedException(TaskAlreadyAllocatedException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An unexpected error occurred: " + exception.getMessage());
	}
}
