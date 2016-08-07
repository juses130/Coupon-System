package com.dbdao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.added.functions.DBconnectorV3;
import com.exceptionerrors.DaoException;
import com.javabeans.Company;
import com.javabeans.Coupon;

public class CouponFoundInDatabase {
	
//	private Coupon coupon;
	
	protected CouponFoundInDatabase(){}
	
	
	protected boolean couponExistByName (Coupon coupon) throws DaoException {
		
		boolean isExist = false;
		ResultSet rs;
		
		try {
			String sqlQuery = "SELECT * FROM coupon WHERE Title='" + coupon.getTitle() + "'";
			java.sql.Statement stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlQuery);
			rs.next();
			
			if(rs.getRow() != 0) {
				isExist = true;
			}
//			else {
//				throw new DaoException("Denied: Coupon dosen't exist in the Database");
//			}
			
		} catch (SQLException e) {
			throw new DaoException("Error: Checking if the Coupon exist - FAILD (something went wrong)");
		}
		
		return isExist;
		
	} // couponExistByName
	
	protected boolean couponFoundInJoinTables (Coupon coupon, Company company ,CheckCouponBy type) throws DaoException {
		
		boolean isExist = false;
		java.sql.Statement state = null;
		ResultSet rs = null;
		try {
		
		if(type == CheckCouponBy.BY_NAME) {
			
			String sqlQueryByName = "SELECT coupon.* "
					+ "FROM company_coupon, coupon "
					+ "WHERE coupon.title='" + coupon.getTitle() + "' " 
					+ "AND company_coupon.comp_ID=" + company.getId() + " "
					+ "AND company_coupon.Coup_ID = coupon.Coup_id";
			
			
				state = DBconnectorV3.getConnection().createStatement();
				rs = state.executeQuery(sqlQueryByName);
				rs.next();

		} // if
		else if(type == CheckCouponBy.BY_ID) {
			
			String sqlQueryByID = "SELECT coupon.* "
					+ "FROM company_coupon, coupon "
					+ "WHERE coupon.coup_id=" + coupon.getId() + " "
					+ "AND company_coupon.comp_ID=" + company.getId() + " "
					+ "AND company_coupon.Coup_ID = coupon.Coup_id";
			
			state = DBconnectorV3.getConnection().createStatement();
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
	
    protected boolean couponExistByID (Coupon coupon) throws DaoException {
		
		boolean isExist = false;
		ResultSet rs;
		
		try {
			String sqlQuery = "SELECT * FROM coupon WHERE coup_id=" + coupon.getId();
		java.sql.Statement stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlQuery);
			rs.next();
			
			if(rs.getRow() != 0) {
				isExist = true;
			}
//			else {
//				throw new DaoException("Denied: Coupon dosen't exist in the Database");
//			}
			
		} catch (SQLException e) {
			throw new DaoException("Error: Checking if the Coupon exist - FAILD (something went wrong)");
		}
		return isExist;
		
	} // couponExistByID
    
    protected boolean purchasedBefore(long coupID, long custID) throws DaoException {
    	
		boolean isExist = false;
		
		try {
			
			String sqlQueryByID = "SELECT coupon.* "
					+ "FROM customer_coupon, coupon "
					+ "WHERE coupon.coup_id=" + coupID + " "
					+ "AND customer_coupon.cust_id=" + custID + " "
					+ "AND customer_coupon.Coup_ID = coupon.Coup_id";
			
			java.sql.Statement state = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = state.executeQuery(sqlQueryByID);
			rs.next();
			
			if(rs.getRow() != 0) {
				
				isExist = true;
				
			}// if
		} catch (SQLException e) {
			throw new DaoException();
		}
    	
		return isExist;
    } // purchasedBefore

}

