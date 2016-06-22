package com.unusedclasses;

import java.sql.*;

public class ConnectionFunctions {
	// This is a Singletons Class.
	
	// 
	private static Connection connection;
	private static boolean conRunningOrNot = false;
	/*
	 * 
	 */
	/* Private Constructor - we can't let the user or the program use a default constructor.
	 * because with a default constructor we can created more then 1 ConnectionPool Object.
	 */
	private ConnectionFunctions() {}
	
	// The program will use getter for getting only 1 instatce.
	public static Connection getInstatce() {
		conRunningOrNot = true;
		return connection;
	}
	
	// Functions
	
	public void getConnection() throws SQLException {
		//if (poolRunningOrNot == false) {
			
		connection = null;
		
				String url = "jdbc:mysql://localhost:3306/world";
				String userDBname = "root";
				String passowrdDB = "12345";
			
				connection = DriverManager.getConnection(url, userDBname, passowrdDB);


	//	} // if - IS NOT WORKING NOW
		
	} // getConnection - Function
	
	public Connection returnConnection() {
		// This function is need to return the connection.
		return connection;
		
	} // returnConnection - Function
	
	public void closeConnection() {
		
			
	}

	// this function is for the checking our poolRunning status.
	public static boolean getPoolRunning() {
		return conRunningOrNot;
	} // getPoolRunning - Function
	
}
