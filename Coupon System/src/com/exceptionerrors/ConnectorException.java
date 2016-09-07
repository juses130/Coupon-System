package com.exceptionerrors;

public class ConnectorException extends Exception{

	private static final long serialVersionUID = -5741030238177404250L;

	// Default Constructor
	public ConnectorException(){
		super();
	}
	
	public ConnectorException(String message){
		super(message);
	}
	
}
