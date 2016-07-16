package com.added.functions;

import java.sql.*;

/**
 * @author Raziel
 * This is a Helper Class - Database Connector. 
 * We using it all over the project classes to set connection
 * or getting the current of the connection (it will be 'getInsance()' ).
 * 
 * Update: This function is unsued anymore - use DBconnectorV3.
 */
@Deprecated
public class DBconnector {
	

	
	private static Connection con = null;
	private static final String url = "jdbc:mysql://localhost:3306/coupon";
	private static final String userDBname = "root";
	private static final String passowrdDB = "12345";

	
	private DBconnector () {}
	
	public static Connection getCon() {
		
		 try {
			con = DriverManager.getConnection(url, userDBname, passowrdDB);
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		 return con;
	}
	
	public static Connection getInstatce()
	{
		return con;
	}
	
}
