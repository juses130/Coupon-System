package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import com.added.functions.DBconnectorV3;
import com.dao.interfaces.CouponDAO;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.ClientType;
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
	// TODO: TO add clientType for creating (admin no need, customer to customer_coupon and company to coupon + company_coupon)
	@Override
	public Coupon createCoupon(Coupon coupon) throws DaoExeption{

		if(existOrNotByName(coupon) == false) {

			long id = -1;
			try {

				String sqlQuery = "INSERT INTO coupon (Title, Start_Date, End_Date, " + 
				"Amount, Category, Message, Price, Image, Owner_ID)" + "VALUES(?,?,?,?,?,?,?,?,?)";	
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
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
				ResultSet rs = prep.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				coupon.setId(id);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new DaoExeption("Error: Creating New Coupon - FAILED (something went wrong)");
			}
			return coupon;
		}
		else {
			throw new DaoExeption("Error: Creating New CouponID - FAILED (Coupon is already exist in the DataBase)");
		}
	} // createCoupon - function

	@Override
	public void removeCoupon(Coupon coupon) throws DaoExeption{
		// check if the company exist
		if (existOrNotByID(coupon) == true) {
			removeMethod(coupon);
		}
		else {
				throw new DaoExeption("Error: Removing Coupon - FAILED (Coupon is not exist in the DataBase)");
		} // else
	}
	
	@Override
	public Coupon updateCoupon(Coupon coupon) throws DaoExeption{
		
		if(existOrNotByID(coupon) == true) {
			// copy all the coupon to new Coupon Object.
			Coupon couponUP = null;

		       try {
		    	   couponUP = getCoupon(coupon.getId(), ClientType.COMPANY);
					
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
						throw new DaoExeption("Error: Updating Coupon - FAILED (something went wrong)");
					}

		       return couponUP;
		} // if - exist
		else {
			throw new DaoExeption("Error: Updating Coupon - FAILED (Coupon dosen't exist in the DataBase)");
		} // else - exist

		}
	
	@Override
	public Coupon getCoupon(long id, ClientType client) throws DaoExeption{
		Coupon coupon = getCouponMethod(id, client);
		return coupon;
	} // getCoupon - Function

	@Override
	public Collection<Coupon> getAllCoupons(long id, ClientType client) throws DaoExeption{
		Set<Coupon> coupons = new HashSet<>();
		
		if(client == ClientType.COMPANY) {
			coupons = getAllCouponsOfCompany(id);
			return coupons;
		}
		else if(client == ClientType.CUSTOMER) {
			
		}
		else if(client == ClientType.ADMIN) {
			
		}
		else {
			throw new DaoExeption("Error: Getting Coupons - FAILED (Unidentified user)");
		}
		
		 
		
		//TODO: check if you gonna use this for all or you will make a flag client..?
		try {
			String sql = "SELECT * FROM Coupon";
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
//				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			throw new DaoExeption("Error: Getting ALL Coupons - FAILED (something went wrong)");
		}
		return coupons;
	} // getAllCoupon - function

	@Override
	public Set<Coupon> getCouponByType(String table, String colmun, long id, CouponType category) throws DaoExeption {
		//TODO: check this function
		Set<Coupon> coupons = new HashSet<>();
		
		try {
			String sql = "SELECT coupon.Coup_id FROM coupon JOIN " + table + " ON " + table + ".Coup_ID = coupon.Coup_id WHERE " + table + "." + colmun + "= " + id + " AND "
					+ "coupon.Category='" + category.toString().toUpperCase() + "'"; 
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
			 
			ResultSet rs = prep.executeQuery(sql);
			
			while (rs.next()) {
//				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			throw new DaoExeption("Error: Get Coupons By Type - FAILED (something went wrong)");
		}
		return coupons;
	}
    
    @Override
    public Set<Coupon> getCouponByPrice(String table, String colmun, long compID, double maxPrice) throws DaoExeption{
    	Set<Coupon> coupons = new HashSet<>();
		try {
			String sql = "SELECT coupon.Coup_id from coupon JOIN " + table + " ON " + table + ".Coup_ID= coupon.Coup_id WHERE " + table + "." + colmun + "=" + compID + " AND coupon.Price <= " + maxPrice;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
			ResultSet rs = prep.executeQuery(sql);

			// putting them in the Set<Coupon>
			while (rs.next()) {
				
//				if(getCoupons(rs.getLong(1)).getPrice() <= maxPrice) {
//					coupons.add(getCoupon(rs.getLong(1)));
//				}
				
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
			throw new DaoExeption("Error: Removing Coupon - FAILED");
		}
	} // removeMethod

    private boolean existOrNotByID(Coupon coupon) throws DaoExeption {
		
    	boolean answer = false;
    	if (coupon.getId() > 0) {
    		try {
        		
        		String sqlName = "SELECT coup_ID FROM coupon WHERE "
        		+ "coup_ID= " + "'" + coupon.getId() + "'";
        		
        		Statement stat = DBconnectorV3.getConnection().createStatement();
        		ResultSet rs = stat.executeQuery(sqlName);
    			rs.next();
    					   
    			if (rs.getRow() != 0) {
    				answer = true;
    			} // if
    		} catch (SQLException e) {
    			throw new DaoExeption("Error: cannot make sure if the coupon is in the DataBase");
    		}
    		return answer;
    	}
    	else {
    	throw new DaoExeption("Error: Detecting CouponID - FAILED (ID cannot contain Zero!)");
    	} // else
	}

    private boolean existOrNotByName(Coupon coupon) throws DaoExeption {
 		boolean answer = false;
 		   
 		  try {
 				String sqlName = "SELECT title FROM coupon WHERE "
 				+ "title= '" + coupon.getTitle() + "'";
 				Statement stat = DBconnectorV3.getConnection().createStatement();
 				ResultSet rs = stat.executeQuery(sqlName);
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

    private Coupon getCouponMethod(long id, ClientType client) throws DaoExeption {
		Coupon coupon = new Coupon();
		coupon.setId(id);
		
		// we have 2 access option
		if(client == ClientType.ADMIN) {
			
			if(existOrNotByID(coupon) == true) {
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
					
					while(rs.next()) {
						
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

				}
				catch (SQLException | FiledErrorException e) {
					throw new DaoExeption("Error: Getting Coupon By ID - FAILED (something went wrong)");
				}
				return coupon;
			} // if - exist
			else {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
		} // if - admin
		else if (client == ClientType.COMPANY) {
			
			if(existOrNotByID(coupon) == true) {
				String title, message, image;
				Date stDate, enDate ;	
				int amount;
				CouponType type = null;
				double price;
				
				try {
					
					String sqlSELCoupByCompany = "SELECT coup_id FROM Company_Coupon WHERE comp_id= ?" ;
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSELCoupByCompany);
					prep.setLong(1, id);
					ResultSet rs = prep.executeQuery();
					rs.next();
					long coupID = rs.getLong(1);
					prep.clearBatch();
					
					String sqlSelCoupAfterCompnay = "SELECT * FROM coupon WHERE coup_id= ?" ;
					prep = DBconnectorV3.getConnection().prepareStatement(sqlSelCoupAfterCompnay);
					prep.setLong(1, coupID);
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
					throw new DaoExeption("Error: Getting Coupon By ID - FAILED (something went wrong)");
				}
				return coupon;
			} // if - exist
			else {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
		} // else - COMPANY
		else if (client == ClientType.CUSTOMER) {
			//TODO: make this part work and be secure!
			if(existOrNotByID(coupon) == true) {
				String title, message, image;
				Date stDate, enDate ;	
				int amount;
				CouponType type = null;
				double price;
				
				try {
					
					String sqlSELCoupByCompany = "SELECT coup_id FROM Company_Coupon WHERE comp_id= ?" ;
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSELCoupByCompany);
					prep.setLong(1, id);
					ResultSet rs = prep.executeQuery();
					rs.next();
					long coupID = rs.getLong(1);
					prep.clearBatch();
					
					String sqlSelCoupAfterCompnay = "SELECT * FROM coupon WHERE coup_id= ?" ;
					prep = DBconnectorV3.getConnection().prepareStatement(sqlSelCoupAfterCompnay);
					prep.setLong(1, coupID);
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
					throw new DaoExeption("Error: Getting Coupon By ID - FAILED (something went wrong)");
				}
				return coupon;
			} // if - exist
			else {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
		}
		else {
			throw new DaoExeption("Error: Getting Coupon - FAILED (Unidentified user)");
		}
		
    }

	/**
	 * <p>getAllCouponsOfCompany</p>
	 * 
	 * This is my add on. 
	 * it will bring all the coupons of the specefic company using owner ID for searching in the DataBase. (ownerID = company id).
	 * @param compID
	 * @return
	 * @throws DaoExeption
	 * 
	 * @author Raziel
	 */
	private Set<Coupon> getAllCouponsOfCompany(long compID) throws DaoExeption{
        Set<Coupon> coupons = new HashSet<>(); 
		try {
			String sql = "SELECT * FROM Coupon WHERE owner_ID=" + compID;
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				
				Coupon coupon = new Coupon();
				
				String title, message, image;
				Date stDate, enDate ;	
				int amount;
				CouponType type = null;
				double price;
				long id;
				
				id = rs.getLong("coup_id");
				title = rs.getString("Title");
				stDate = rs.getDate("start_date");
				enDate = rs.getDate("end_date");
				amount = rs.getInt("amount");
				type = CouponType.valueOf(rs.getString("Category").toUpperCase());
				message = rs.getString("Message");
				price = rs.getDouble("Price");
				image = rs.getString("image");

				coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, type,  message, price, image);

				// adding the current coupon to the collection
				coupons.add(coupon);
			}
		} catch (SQLException | FiledErrorException e) {
			throw new DaoExeption("Error: Getting Coupons of Company - FAILED (something went wrong)");
		}
		return coupons;
	} // getCoupon - Function
	
	
	
} // Class
