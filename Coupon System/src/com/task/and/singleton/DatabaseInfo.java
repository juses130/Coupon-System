package com.task.and.singleton;

/**
 * This is a helper class.
 * Here we can change the Name of the DB, and put our custom Database.
 * </br>
 * As you can see, I (Raziel) have a Online Database, so it is connecting to INTERNET.
 * So if you want to Connect the Database OFFLINE, just change the parameters HERE. 
 * </br>
 * 
 * @author Raziel
 *
 */

public class DatabaseInfo {

	/*
	 * This is for MySql Online
	 */
	
//	private static final String databaseName ="sql6129033";
//	private static final String driverClass = "com.mysql.jdbc.Driver";
//	private static final String userDBname = "sql6129033"; 	
//	private static final String passowrdDB = "cqeQXQ4dRC";
//	private static final String url = "jdbc:mysql://sql6.freemysqlhosting.net:3306/" + databaseName + "?characterEncoding=UTF-8&useSSL=false";

//	private static final String test = "jdbc:mysql://localhost:3306/coupon?user=root&password=12345?characterEncoding=UTF-8&useSSL=false";
	
	/*
	 * This is for MySql LOCAL
	 */

		private static final String databaseName ="coupon";
		private static final String driverClass = "com.mysql.jdbc.Driver";
		private static final String userDBname = "root"; 	
		private static final String passowrdDB = "12345";
		private static final String url = "jdbc:mysql://localhost:3306/" + databaseName + "?characterEncoding=UTF-8&useSSL=false";
	
	private DatabaseInfo() {}

	// This method are Public because Classes such as DBName (com.dbdao) need to access.
	public static String getDBname() {
		return databaseName;
	}
	

	protected static String getUrl() {
		return url;
	}

	protected static String getDriverclass() {
		return driverClass;
	}

	protected static String getUserdbname() {
		return userDBname;
	}

	protected static String getPassowrddb() {
		return passowrdDB;
	}

//	protected static String getTest() {
//		return test;
//	}

}