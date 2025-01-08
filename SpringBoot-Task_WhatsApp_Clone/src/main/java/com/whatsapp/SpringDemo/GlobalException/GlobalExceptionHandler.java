package com.whatsapp.SpringDemo.GlobalException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.whatsapp.SpringDemo.ExceptionClasses.GroupNotFoundException;
import com.whatsapp.SpringDemo.ExceptionClasses.UserNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Map <String,String>> userNotFound(UserNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "User not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Map <String,String>> groupNotFound(GroupNotFoundException exc){
	
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Group not found");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> fieldErrors = new HashMap<>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        fieldErrors.put(error.getField(), error.getDefaultMessage());
	    }
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("error", "Validation failed");
	    response.put("message", "Invalid input data");
	    response.put("details", fieldErrors);
	    
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	   
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map <String,String>> exceptionHandeler(Exception exc){
		HashMap<String ,String> error = new HashMap<>();
		
		error.put("error", "Internal server Error");
		error.put("message",exc.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
