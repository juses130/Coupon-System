package com.exceptionerrors;

public class DaoException extends Exception{
	
	private static final long serialVersionUID = 3581243735148603394L;

	// Default Constructor
	public DaoException(){
		super();
	}
	
	public DaoException(String message){
		super(message);
	}
	
}
