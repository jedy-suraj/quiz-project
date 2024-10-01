package com.quizapplication.exceptionclasses;

public class InvalidChoiceException extends RuntimeException{

	/*
	 * This is an custom exception class 
	 * 
	 * This exceptions class is used to throw exception whenever user 
	 * enter wrong choice.
	 * 
	 * Here constructor is used to call contructor of parent class.
	 * 
	 */
	public InvalidChoiceException(String message) {
		super(message);
	}
}
