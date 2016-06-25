package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


import com.added.functions.DBconnector;
import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dao.interfaces.CouponDAO;
import com.javabeans.Coupon;
import com.javabeans.CouponType;
import com.javabeans.Customer;

public class CouponDBDAO implements CouponDAO{

	@Override
	public long createCoupon(Coupon coupon) {
		
		long id = -1;
		
		// creating ResultSet
		ResultSet rs = null;
		
		try {
			
			DBconnector.getCon();
			String sqlQuery = "INSERT INTO coupon (Title, Start_Date, End_Date, " + 
			"Amount, Category, Massage, Price, Image)" + "VALUES(?,?,?,?,?,?,?,?)";	
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, coupon.getTitle());
			prep.setDate(2, Date.valueOf(coupon.getStartDate()));
			prep.setDate(3, Date.valueOf(coupon.getEndDate()));
			prep.setInt(4, coupon.getAmount());
			prep.setString(5, coupon.getType().toString());
			prep.setString(6, coupon.getMessage());
			prep.setDouble(7, coupon.getPrice());
			prep.setString(8, coupon.getImage());
			
			prep.executeUpdate();
			rs = prep.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
			coupon.setId(id);
			
			// Letting the others (if the asking) that the Company Added Succsefully.
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
		
		IsExistDB.idExistV2Coupon(coupon.getId());
		if(IsExistDB.getAnswer2() == true) {

			//ResultSet rs = null;
			PreparedStatement prep = null;
			
			try {
				
				DBconnector.getCon();
				String sqlDELobject = "DELETE FROM coupon WHERE Coup_ID =?";
				prep = DBconnector.getInstatce().prepareStatement(sqlDELobject);
				prep.setLong(1, coupon.getId());
				prep.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
			
			finally {
				try {
					DBconnector.getInstatce().close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			} // finally
		}
		
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		
       try {

    	   DBconnector.getCon();
			
			
			String sql = "UPDATE Coupon SET Title=?, Start_Date=?, End_Date=?, Amount=?, "
					+ "Category=?, Massage=?, Price=?, Image=? WHERE Coup_ID=?";
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
		//System.out.println("Check1");
		// TODO: why it's returning Null in the tests?
		Coupon coupon = null;
		String title, message, image;
		Date stDate, enDate ;	
		int amount;
		CouponType type;
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
			
			type = CouponType.valueOf(rs.getString("TYPE"));
			
			message = rs.getString("message");
			
			price = rs.getDouble("Price");
			
			image = rs.getString("image");
			
			
			
			coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, type,  message, price, image);
			//String customerInfo = coupon.toString();
			//SharingData.setVarchar2(customerInfo);

			//System.out.println(customerInfo);
			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			System.out.println("V5");
			
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
	}

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
	}

	@Override
	public Collection<Coupon> getCouponByType() {
		
		return null;
	}

}
