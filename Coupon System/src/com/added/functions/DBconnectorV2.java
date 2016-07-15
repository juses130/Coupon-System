package com.added.functions;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * This is Version 2 of DataBase Connector.
 * In this version we are using 'mchange-commons' AND 'c3p0'. 
 * 
 * This two external files helping us two create a good Connection Pool.
 * 
 * @author Raziel
 *
 */

public class DBconnectorV2 {

	private static ComboPooledDataSource cpds = null;
	private static final String url = "jdbc:mysql://localhost:3306/coupon?characterEncoding=UTF-8&useSSL=false";
	private static final String driverClass = "com.mysql.jdbc.Driver";
	private static final String userDBname = "root";
	private static final String passowrdDB = "12345";
	
	/**
	 * Open Connection Pool.
	 */
	
	public static void startPool() {
		
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(driverClass);
			cpds.setJdbcUrl(url);
			cpds.setUser(userDBname);
			cpds.setPassword(passowrdDB);
			cpds.setMaxStatements(180);
			
		} catch (PropertyVetoException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		
	} // startPool()
	
	public static Connection getConnection() throws SQLException {

		return cpds.getConnection();
	} // getConnection()
	
	
	
}
