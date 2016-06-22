package com.added.functions;

import java.sql.*;

public class DBconnector {
	
	/**
	 * @author Raziel
	 * This is a Helper Class - Database Connector. 
	 * We using it all over the project classes to set connection
	 * or getting the current of the connection (it will be 'getInsance()' ).
	 */
	
	private static Connection con;
	private static final String url = "jdbc:mysql://localhost:3306/coupon";
	private static final String userDBname = "root";
	private static final String passowrdDB = "12345";

	private DBconnector () {}
	
	public static Connection getInstatce()
	{
		return con;
	}
	
	public static Connection getCon() {
		
		 try {
			con = DriverManager.getConnection(url, userDBname, passowrdDB);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 return con;
	}
	
}
