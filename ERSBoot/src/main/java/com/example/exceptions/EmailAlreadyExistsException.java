package com.example.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailAlreadyExistsException() {
		super("The email is already registered");
	}

}
