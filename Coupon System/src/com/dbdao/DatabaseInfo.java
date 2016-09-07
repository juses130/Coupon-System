package com.dbdao;

public class DatabaseInfo {

	/*
	 * This is for MySql Online
	 */
	
	private static final String databaseName ="a2151198_coupon";
	private static final String url = "jdbc:mysql://mysql1.000webhost.com:3306/" + databaseName;
	private static final String driverClass = "com.mysql.jdbc.Driver";
	private static final String userDBname = "a2151198_gta570"; 	
	private static final String passowrdDB = "abc12345";
	
	/*
	 * This is for MySql LOCAL
	 */
//	private static final String databaseName ="coupon";
//	private static final String url = "jdbc:mysql://localhost:3306/" + databaseName + "?characterEncoding=UTF-8&useSSL=false";
//	private static final String driverClass = "com.mysql.jdbc.Driver";
//	private static final String userDBname = "root"; 	
//	private static final String passowrdDB = "12345";

	
	private DatabaseInfo() {}

	protected static String getDBname() {
		return databaseName;
	}
	

	public static String getUrl() {
		return url;
	}

	public static String getDriverclass() {
		return driverClass;
	}

	public static String getUserdbname() {
		return userDBname;
	}

	public static String getPassowrddb() {
		return passowrdDB;
	}

}