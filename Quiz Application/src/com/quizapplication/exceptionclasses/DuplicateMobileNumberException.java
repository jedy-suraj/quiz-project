package com.quizapplication.exceptionclasses;

public class DuplicateMobileNumberException extends RuntimeException{

	/*
	 * This is an custom exception class 
	 * 
	 * This exceptions class is used to throw exception whenever user 
	 * tries to register using mobile number that is already registered.
	 * 
	 * Here constructor is used to call contructor of parent class.
	 * 
	 */
	public DuplicateMobileNumberException() {
		super("Mobile number already exist. Please try to log in else use another number");
	}
}
