package com.whatsapp.SpringDemo.ExceptionClasses;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String msg) {
		super(msg);
	}
}
