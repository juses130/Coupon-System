package com.added.functions;

import java.sql.*;

import ExeptionErrors.ConnectorExeption;
import ExeptionErrors.DaoExeption;

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

		public static void startPool() throws ConnectorExeption{
			

//			con = con = DriverManager.getConnection(url, userDBname, passowrdDB);
			
				try {
					con = DriverManager.getConnection(url, userDBname, passowrdDB);
				} catch (NullPointerException | SQLException e) {
					throw new ConnectorExeption("Error: Connection to the Data Base - FAILED (DataBase User or Password or Database url are not currect!)");
				}
				try {
					Class.forName(driverClass);
				} catch (ClassNotFoundException e) {
					throw new ConnectorExeption("Error: Connection to the Driver - FAILED (check the location of your driver)");
				}

			
			
		}
		
		public static Connection getConnection() {
				return con;
		}
}
