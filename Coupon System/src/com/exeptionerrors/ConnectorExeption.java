package com.exeptionerrors;

public class ConnectorExeption extends Exception{

	private static final long serialVersionUID = 1L;

	// Default Constructor
	public ConnectorExeption(){
		super();
	}
	
	public ConnectorExeption(String message){
		super(message);
	}
	
}
