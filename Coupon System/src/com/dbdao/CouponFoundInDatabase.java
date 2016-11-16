package com.dbdao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.beans.Company;
import com.beans.Coupon;
import com.exceptionerrors.DaoException;
import com.task.and.singleton.DBconnector;
import com.task.and.singleton.DatabaseInfo;

/**
 * This is a helper <b>Protected</b> Class.</br>
 * The system will use this class to Check if the {@code Coupon} in the question is exist in the Database.</br>
 * The access to this class is <b>ONLY</b> from the DBDAO that located in: 'com.dbdao'.
 * 
 * @author Raziel
 *
 */

public class CouponFoundInDatabase {
		
	protected CouponFoundInDatabase(){}
	
	/**
	 * Check if the {@code Coupon} exist in the Database by its Name  {@code String}
	 * 
	 * @param coupon a {@code Coupon} Object
	 * @return {@code true} if the {@code Coupon} was found in the Database, otherwise {@code false}.
	 * @throws DaoException
	 */
	protected boolean couponExistByName (Coupon coupon) throws DaoException {
		
		boolean isExist = false;
		try {
			String sqlQuery = "SELECT * FROM " + DatabaseInfo.getDBname() + ".coupon WHERE Title='" + coupon.getTitle() + "'";
			java.sql.Statement stat = DBconnector.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sqlQuery);
			rs.next();
			
			if(rs.getRow() != 0) {
				isExist = true;
			} // if
			
		} catch (SQLException e) {
			throw new DaoException("Error: Checking if the Coupon exist - FAILD (something went wrong)");
		} // catch
		return isExist;
	} // couponExistByName
	
	/**
	 * Check if the {@code Coupon} exist in the <b>Join Tables</b> (in the Database) of the specific {@code Company}.</br>
	 * We this method we can check and compare the data to the Database by 
	 * The {@code Coupon} Name {@code String} OR by the 
	 * {@code Coupon} ID {@code long}.
	 * 
	 * @param coupon a {@code Coupon} Object
	 * @param company a {@code Company} Object
	 * @param type a {@code CheckCouponBy} Enum
	 * @return  {@code true} if the {@code Coupon} was found in the Join Tables, otherwise  {@code false}.  
	 * @throws DaoException
	 */
	
	protected boolean couponFoundInJoinTables (Coupon coupon, Company company ,CheckCouponBy type) throws DaoException {
		
		boolean isExist = false;
		java.sql.Statement state = null;
		ResultSet rs = null;
		try {
		
		if(type == CheckCouponBy.BY_NAME) {
			
			String sqlQueryByName = "SELECT " + DatabaseInfo.getDBname() + ".coupon.* "
					+ "FROM " + DatabaseInfo.getDBname() + ".company_coupon, coupon "
					+ "WHERE " + DatabaseInfo.getDBname() + ".coupon.title='" + coupon.getTitle() + "' " 
					+ "AND " + DatabaseInfo.getDBname() + ".company_coupon.comp_ID=" + company.getId() + " "
					+ "AND " + DatabaseInfo.getDBname() + ".company_coupon.Coup_ID = coupon.Coup_id";

				state = DBconnector.getConnection().createStatement();
				rs = state.executeQuery(sqlQueryByName);
				rs.next();
		} // if
		else if(type == CheckCouponBy.BY_ID) {
			
			String sqlQueryByID = "SELECT " + DatabaseInfo.getDBname() + ".coupon.* "
					+ "FROM " + DatabaseInfo.getDBname() + ".company_coupon, coupon "
					+ "WHERE " + DatabaseInfo.getDBname() + ".coupon.coup_id=" + coupon.getId() + " "
					+ "AND " + DatabaseInfo.getDBname() + ".company_coupon.comp_ID=" + company.getId() + " "
					+ "AND " + DatabaseInfo.getDBname() + ".company_coupon.Coup_ID = coupon.Coup_id";
			
			state = DBconnector.getConnection().createStatement();
			rs = state.executeQuery(sqlQueryByID);
			rs.next();
		} // else if
		
		if(rs.getRow() != 0) {
			isExist = true;
		} // if
		
		} catch (SQLException e) {
			throw new DaoException("Error: Checking it the Coupon exist in the Joins - FAILD");
		} // catch		
		return isExist;
	} // couponFoundInJoinTables
	
	/**
	 * Check if the {@code Coupon} exist in the Database by its ID {@code long}.
	 * 
	 * @param coupon
	 * @return  {@code true} if {@code Coupon} was found in the Database, otherwise {@code false}.
	 * @throws DaoException
	 */
	
    protected boolean couponExistByID (Coupon coupon) throws DaoException {
		
		boolean isExist = false;
		try {
			String sqlQuery = "SELECT * "
					+ " FROM  " + DatabaseInfo.getDBname() + ".coupon "
					+ " WHERE coup_id=" + coupon.getId();
		java.sql.Statement stat = DBconnector.getConnection().createStatement();
		ResultSet rs = stat.executeQuery(sqlQuery);
			rs.next();
			
			if(rs.getRow() != 0) {
				isExist = true;
			}// if
			
		} catch (SQLException e) {
			throw new DaoException("Error: Checking if the Coupon exist - FAILD (something went wrong)");
		}// if
		return isExist;
	} // couponExistByID
    
    /**
     * Check if the {@code Coupon} exist in the Customer Join Tables (in the Database). Comparing between the Coupon ID {@code long}
     * and the Customer ID {@code long}.
     * 
     * @param coupID a {@code long} Coupon ID
     * @param custID a {@code long} Customer ID
     * @return {@code true} if the Coupon ID {@code long} was found in the Customer Join Tables
     *  otherwise  {@code false}.	
     * @throws DaoException
     */
    
    protected boolean purchasedBefore(long coupID, long custID) throws DaoException {
    	
		boolean isExist = false;
		try {
			// TOOD: add here the databaseInfo
			String sqlQueryByID = "SELECT coupon.* "
					+ "FROM customer_coupon, coupon "
					+ "WHERE coupon.coup_id=" + coupID + " "
					+ "AND customer_coupon.cust_id=" + custID + " "
					+ "AND customer_coupon.Coup_ID = coupon.Coup_id";
			
			java.sql.Statement state = DBconnector.getConnection().createStatement();
			ResultSet rs = state.executeQuery(sqlQueryByID);
			rs.next();
			
			if(rs.getRow() != 0) {
				
				isExist = true;
				
			}// if
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error: Checking Coupon before Purchase - FAILD (something went wrong)");
		} // catch
		return isExist;
    } // purchasedBefore

} // Class

