package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.added.functions.DBconnector;
import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dao.interfaces.CouponDAO;
import com.javabeans.Coupon;
import com.javabeans.CouponType;

/**
 * This is Coupon Database DAO Class.
 * Just impelemnts the methods from CouponDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CouponDBDAO implements CouponDAO{

	@Override
	public long createCoupon(Coupon coupon) {
		long id = -1;
		
		// creating ResultSet
		ResultSet rs = null;
		try {
			
			// 1. Adding the new coupon to the COUPON TABLE. 
			
			DBconnector.getCon();
			String sqlQuery = "INSERT INTO coupon (Title, Start_Date, End_Date, " + 
			"Amount, Category, Message, Price, Image, Owner_ID)" + "VALUES(?,?,?,?,?,?,?,?,?)";	
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, coupon.getTitle());
			prep.setDate(2, Date.valueOf(coupon.getStartDate()));
			prep.setDate(3, Date.valueOf(coupon.getEndDate()));
			prep.setInt(4, coupon.getAmount());
			prep.setString(5, coupon.getType().toString());
			prep.setString(6, coupon.getMessage());
			prep.setDouble(7, coupon.getPrice());
			prep.setString(8, coupon.getImage());
			prep.setLong(9, coupon.getOwnerID());
			
			prep.executeUpdate();
			rs = prep.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
			coupon.setId(id);
			prep.close();
			rs.close();
			
			// 2. Adding the new CouponID to the COMPANY_COUPON TABLE.
			
			long compID = coupon.getOwnerID();
			String sqlQuery1 = "INSERT INTO company_coupon (Comp_ID ,Coup_ID) VALUES ("+ compID +  
				"," + coupon.getId() + ");";
			PreparedStatement prep1 = DBconnector.getInstatce().prepareStatement(sqlQuery1);
			prep1.executeUpdate();
			
			// Letting the others (if the asking) that the Coupon Added Succsefully.
			SharingData.setFlag1(true);
			String tostring = coupon.toString();
			SharingData.setVarchar4(tostring);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
		} // finally
		
		return id;

	} // createCoupon - function

	@Override
	public void removeCoupon(Coupon coupon) {
		
		//removeMethod(coupon, "coupon");
		
		removeMethod(coupon, "company_coupon");
		removeMethod(coupon, "customer_coupon");
		
	}

	public void removeCouponOwnerID(long id) {
		removeMethodByOwnerID(id);
	}
	
	@Override
	public void updateCoupon(Coupon coupon) {
		
       try {

    	   DBconnector.getCon();
			
			
			String sql = "UPDATE Coupon SET Title=?, Start_Date=?, End_Date=?, Amount=?, "
					+ "Category=?, Message=?, Price=?, Image=? WHERE Coup_ID=?";
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement (sql);
			prep.setString(1, coupon.getTitle());
			prep.setDate(2, Date.valueOf(coupon.getStartDate()));
			prep.setDate(3, Date.valueOf(coupon.getEndDate()));
			prep.setLong(4, coupon.getAmount());
			prep.setString(5, coupon.getType().toString());
			prep.setString(6, coupon.getMessage());
			prep.setDouble(7, coupon.getPrice());
			prep.setString(8, coupon.getImage());
			prep.setLong(9, coupon.getId());
			
			prep.executeUpdate();
			
		    // Letting the others (if the asking) that the Coupon update Succsefully.
		    SharingData.setFlag1(true);
		    String tostring = coupon.toString();
			SharingData.setVarchar4(tostring);

			} catch (SQLException e) {
			e.printStackTrace();
			}
			finally {
			try {
				
			DBconnector.getInstatce().close();
			} catch (SQLException e) {
			e.printStackTrace();
					} // catch
			} // finally
       
	}

	@Override
	public Coupon getCoupon(long id) {
	
		Coupon coupon = null;
		String title, message, image;
		Date stDate, enDate ;	
		int amount;
		CouponType type = null;
		double price;
		
		DBconnector.getCon();
		
		try {
			String sqlSEL = "SELECT * FROM Coupon WHERE Coup_ID= ?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlSEL);
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
			String customerInfo = coupon.toString();
			SharingData.setVarchar2(customerInfo);

			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		return coupon;
	} // getCoupon - Function

	@Override
	public Collection<Coupon> getAllCoupon() {
		Set<Coupon> coupons = new HashSet<>(); 
		
		DBconnector.getCon();
		
		try {
			String sql = "SELECT coup_id FROM Coupon";
			Statement stat = DBconnector.getInstatce().createStatement();
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
	public Set<Coupon> getCouponByType(CouponType category) {
		
		Set<Coupon> coupons = new HashSet<>();
		
		DBconnector.getCon();
		try {
			String sql = "SELECT coup_id FROM Coupon WHERE Category= '" 
		    + category.toString().toUpperCase() + "'";
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sql);
			//prep.setString(1, category.toString().toUpperCase());
			ResultSet rs = prep.executeQuery(sql);

			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
			
			// Sharing the results as String :)
			String customerInfo = coupons.toString();
			SharingData.setVarchar2(customerInfo);

			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			
		} catch (SQLException e) {
			e.getMessage();
		}
		return coupons;
	}

	private void removeMethod(Coupon coupon, String table) {
		
		IsExistDB.idExistV2Coupon(coupon.getId(), table);
		if(IsExistDB.getAnswer2() == true) {
			PreparedStatement prep = null;
			
			try {
				DBconnector.getCon();
				String sqlDELobject = "DELETE FROM " + table + " WHERE Coup_ID =?";
				prep = DBconnector.getInstatce().prepareStatement(sqlDELobject);
				long id = coupon.getId();
				prep.setLong(1, id);
				prep.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
			
			finally {
				try {
					DBconnector.getInstatce().close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				} // catch
			} // finally
		} // if
	} // removeMethod
	
	private void removeMethodByOwnerID(long id) {
		
		IsExistDB.idExistV2Coupon(id, "coupon");
		if(IsExistDB.getAnswer2() == true) {
			PreparedStatement prep = null;
			
			try {
				DBconnector.getCon();
				String sqlDELobject = "DELETE FROM coupon WHERE Owner_ID =?";
				prep = DBconnector.getInstatce().prepareStatement(sqlDELobject);
			
				prep.setLong(1, id);
				prep.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
			
			finally {
				try {
					DBconnector.getInstatce().close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				} // catch
			} // finally
		} // if
	} // removeMethod

} // Class
