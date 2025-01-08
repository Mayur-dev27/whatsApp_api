package com.whatsapp.SpringDemo.ExceptionClasses;

public class GroupNotFoundException extends RuntimeException{

	public GroupNotFoundException(String msg) {
		super(msg);
	}
}
