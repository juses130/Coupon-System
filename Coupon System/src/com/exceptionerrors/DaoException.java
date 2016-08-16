package com.exceptionerrors;

public class DaoException extends Exception{
	
	private static final long serialVersionUID = 1L;

	// Default Constructor
	public DaoException(){
		super();
	}
	
	public DaoException(String message){
		super(message);
	}
	
}
