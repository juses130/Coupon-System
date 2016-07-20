package com.exeptionerrors;

/**
 * 
 * 
 * 
 * @author Raziel
 *
 */

public class NotFoundInDB extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// constructor
	public NotFoundInDB(){
		super();
	}
	
	public NotFoundInDB(String message) {
		super(message);
	}
	
}
