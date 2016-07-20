package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.added.functions.DBconnectorV3;
import com.added.functions.SharingData;
import com.dao.interfaces.CouponDAO;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.javabeans.Company;
import com.javabeans.Coupon;
import com.javabeans.CouponType;
import com.javabeans.Customer;
import com.mysql.jdbc.SQLError;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import sun.net.dns.ResolverConfigurationImpl;

/**
 * This is Coupon Database DAO Class.
 * Just impelemnts the methods from CouponDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CouponDBDAO implements CouponDAO{
	
	@Override
	public Coupon createCoupon(Coupon coupon) throws DaoExeption{

		purchaseCouponByCustomer(coupon);
		return coupon;

	} // createCoupon - function

	@Override
	public void removeCoupon(Coupon coupon) throws DaoExeption{
		// check if the company exist
		if (existOrNotByID(coupon) == true) {
			removeMethod(coupon);
		}
		else {
				throw new DaoExeption("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else
	}
	
	@Override
	public Coupon updateCoupon(Coupon coupon) throws DaoExeption{
		
		// copy all the coupon to new Coupon Object.
		Coupon couponUP = null;

	       try {
	    	   couponUP = getCoupon(coupon.getId());
				
				String sql = "UPDATE Coupon SET End_Date=?, Amount=?, Message=?, Price=? WHERE Coup_ID=?";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement (sql);
				
				// set all the Coupon to the new one.
				couponUP.setEndDate(coupon.getEndDate());
				couponUP.setAmount(coupon.getAmount());
				couponUP.setMessage(coupon.getMessage());
				couponUP.setPrice(coupon.getPrice());
				
				prep.setDate(1, Date.valueOf(couponUP.getEndDate()));
				prep.setLong(2, couponUP.getAmount());
				prep.setString(3, couponUP.getMessage());
				prep.setDouble(4, couponUP.getPrice());
				prep.setLong(5, couponUP.getId());
				
				prep.executeUpdate();
				
				} catch (SQLException | FiledErrorException e) {
					SharingData.setExeptionMessage(e.getMessage());
				}
				

	       return couponUP;
		}
	
	@Override
	public Coupon getCoupon(long id) throws DaoExeption{
	
		Coupon coupon = null;
		String title, message, image;
		Date stDate, enDate ;	
		int amount;
		CouponType type = null;
		double price;
		
		try {
			
			String sqlSEL = "SELECT * FROM Coupon WHERE Coup_ID= ?" ;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			
			ResultSet rs = prep.executeQuery();
			rs.next();
			
			title = rs.getString("Title");
			stDate = rs.getDate("start_date");
			enDate = rs.getDate("end_date");
			amount = rs.getInt("amount");
			type = CouponType.valueOf(rs.getString("Category").toUpperCase());
			message = rs.getString("Message");
			price = rs.getDouble("Price");
			image = rs.getString("image");

			coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, type,  message, price, image);
			

		}
		catch (SQLException | FiledErrorException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		return coupon;
	} // getCoupon - Function

	public Set<Coupon> getCouponsOfCompany(long compID) throws DaoExeption{
		
        Set<Coupon> coupons = new HashSet<>(); 
				
		try {
			String sql = "SELECT * FROM Coupon WHERE owner_ID=" + compID;
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		return coupons;
	} // getCoupon - Function
	
	@Override
	public Collection<Coupon> getAllCoupon() throws DaoExeption{
		Set<Coupon> coupons = new HashSet<>(); 
		
		
		try {
			String sql = "SELECT coup_id FROM Coupon";
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupons;
	} // getAllCoupon - function

	@Override
	public Set<Coupon> getCouponByType(String table, String colmun, long id, CouponType category) throws DaoExeption {
		
		Set<Coupon> coupons = new HashSet<>();
		
		try {
			String sql = "SELECT coupon.Coup_id FROM coupon JOIN " + table + " ON " + table + ".Coup_ID = coupon.Coup_id WHERE " + table + "." + colmun + "= " + id + " AND "
					+ "coupon.Category='" + category.toString().toUpperCase() + "'"; 
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
			 
			ResultSet rs = prep.executeQuery(sql);
			
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return coupons;
	}
    
	/**
	 * 
	 * Function Version 2 of getCouponByPrice.
	 * it's saving code writing and we can use this function 
	 * for Customer needs and Company needs.
	 * 
	 * I'ts checking the prices before putting it in the SET<>
	 * 
	 * @param table (Customer_Coupom OR Company_Coupon)
	 * @param column (Comp_ID OR Cust_ID)
	 * @param long (compID)
	 * @param maxPrice (double)
	 * @return - Set<Coupon> 
	 */

    @Override
    public Set<Coupon> getCouponByPrice(String table, String colmun, long compID, double maxPrice) throws DaoExeption{
    	Set<Coupon> coupons = new HashSet<>();
		try {
			String sql = "SELECT coupon.Coup_id from coupon JOIN " + table + " ON " + table + ".Coup_ID= coupon.Coup_id WHERE " + table + "." + colmun + "=" + compID + " AND coupon.Price <= " + maxPrice;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery(sql);

			// putting them in the Set<Coupon>
			while (rs.next()) {
				
				if(getCoupon(rs.getLong(1)).getPrice() <= maxPrice) {
					coupons.add(getCoupon(rs.getLong(1)));
				}
				
			} // while loop
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return coupons;
    }
    
	private void removeMethod(Coupon coupon) throws DaoExeption{
		
		String sqlDELid1 = "DELETE FROM coupon WHERE Coup_ID =" + coupon.getId();
		String sqlDELid2 = "DELETE FROM company_coupon WHERE Coup_ID =" + coupon.getId();
		String sqlDELid3 = "DELETE FROM company_coupon WHERE Coup_ID =" + coupon.getId();

		PreparedStatement prep;
		try {
			prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid1);
			prep.executeUpdate();
			prep.clearBatch();
			prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid2);
			prep.executeUpdate();
			prep.clearBatch();
			prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid3);
			prep.executeUpdate();
		} catch (SQLException e) {
			throw new DaoExeption("Error: Removing Company - FAILED");
		}
	} // removeMethod

    private boolean existOrNotByID(Coupon coupon) throws DaoExeption {
		
		Statement stat = null;
		ResultSet rs = null;
		boolean answer = false;
		String sqlName = "SELECT coup_ID FROM coupon WHERE "
		+ "coup_ID= " + "" + coupon.getId() + "";
		
		try {
			stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
					   
			if (rs.getRow() != 0) {
				answer = true;
			} // if
		} catch (SQLException e) {
			throw new DaoExeption("Error: cannot make sure if the coupon is in the DataBase");
		}
		
		   
		return answer;
	}

    private boolean existOrNotByName(Coupon coupon) throws DaoExeption {
		
 	    Statement stat = null;
 		ResultSet rs = null;
 		boolean answer = false;
 		   
 		  try {
 				String sqlName = "SELECT coup_ID FROM coupon WHERE "
 				+ "coup_ID= '" + coupon.getTitle() + "'";
 				stat = DBconnectorV3.getConnection().createStatement();
 				rs = stat.executeQuery(sqlName);
 				rs.next();
 			   
 				if (rs.getRow() != 0) {
 					answer = true;
 				} // if
 	            } catch (SQLException e) {
 	 	   			throw new DaoExeption("Error: cannot make sure if the coupon is in the DataBase");
// 		        e.printStackTrace();
 	            } // catch
 		  return answer;
 	}
	
	private Coupon purchaseCouponByCustomer(Coupon coupon) throws DaoExeption {
		Coupon purchasedCoupon = null;
		
		if(existOrNotByID(coupon) == true) {
			try{
				purchasedCoupon = getCoupon(coupon.getId());
				// We need to Check if the customer purchased this coupon before.
				if(previouslyPurchased(coupon) == false) {
					
					String sqlPurchaseCoupomForCustomer = "INSERT INTO customer_coupon (Cust_id, Coup_id) VALUES (" + SharingData.getIdsShare() + "," + coupon.getId() + ")";
					PreparedStatement prep1 = DBconnectorV3.getConnection().prepareStatement(sqlPurchaseCoupomForCustomer);
					prep1.executeUpdate();
				} // if - previouslyPurchased
				else {
					throw new DaoExeption("Error: Purchase Coupon - FAILED (You can buy the coupon only once)");
				} // else - previouslyPurchased
			} // try
			catch (SQLException | NullPointerException e) {
				throw new DaoExeption("Error: Purchase Coupon - FAILED");			
			} // catch
		return purchasedCoupon;
		}
		else {
			throw new DaoExeption("Error: Purchase Coupon - FAILED (Coupon dosen't exist in the DataBase)");
		}
	} // createCouponByCustomer

	/**
	 * 
	 * This methods will help us to make sure that the customer didn't bought the current coupon in the past.
	 * 
	 * @param coupon
	 * @return boolean
	 * 
	 * @author Raziel
	 * @throws DaoExeption 
	 */
	
	private boolean previouslyPurchased(Coupon coupon) throws DaoExeption {

		boolean answer = false;
		
		try {
			
			Statement stat = null;
			
			String sqlName = "SELECT cust_id FROM customer_coupon WHERE coup_id=" + coupon.getId();
			stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sqlName);
			rs.next();
					   
			if (rs.getRow() != 0) {
				answer = true;
			} // if
		} catch (SQLException e) {
			e.printStackTrace();
//			throw new DaoExeption("Error: cannot make sure if the coupon is in the DataBase");
		}
		
		return answer;
		
	}

	
} // Class
