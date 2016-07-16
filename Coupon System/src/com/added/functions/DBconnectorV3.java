package com.added.functions;

import java.sql.*;

/**
 * This is Version 3 of DataBase Connector.
 * The last one (DBconnectorV2) was stuck or hang by the firewall.
 * 
 * This two external files helping us two create a good Connection Pool.
 * 
 * @author Raziel
 *
 */

public class DBconnectorV3 {

	private static final String url = "jdbc:mysql://localhost:3306/coupon?characterEncoding=UTF-8&useSSL=false";
//	private static final String url = "jdbc:mysql://localhost:3306/coupon";
	private static final String driverClass = "com.mysql.jdbc.Driver";
	private static final String userDBname = "root";
	private static final String passowrdDB = "12345";
	private static Connection con = null;
	
	// private constructor
		private DBconnectorV3(){}

		public static void startPool() {
			
			try {
				con = DriverManager.getConnection(url, userDBname, passowrdDB);
				Class.forName(driverClass);
			} catch (SQLException | ClassNotFoundException e) {
				SharingData.setExeptionMessage(e.getMessage());
			}
			
			
		}
		
		public static Connection getConnection() {
			return con;	
		}
		
}
