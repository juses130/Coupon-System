package com.exceptionerrors;

// all the errors will send the messages from here.
public class LoginException extends Exception{

	private static final long serialVersionUID = 1L;

	// Default Constructor
	public LoginException(){
		super();
	}
	
	public LoginException(String message){
		super(message);
	}
	
	
	
}
