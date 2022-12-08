package com.example.exceptions;

public class NotAManagerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotAManagerException() {
		super("User cannot access a managers process");
	}

}
