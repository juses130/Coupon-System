package com.exeptionerrors;

public class DaoExeption extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Default Constructor
	public DaoExeption(){
		super();
	}
	
	public DaoExeption(String message){
		super(message);
	}
	
}
