package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.added.functions.DBconnector;
import com.added.functions.DBconnectorV3;
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

	/* this instance is helping us to move between 2 methods of creating coupon.
	 * 1. creating by company. (add new coupon)
	 * 2. creating by customer. (purchase)
	 */
	private static short creatorMethod = 0;
	
	@Override
	public long createCoupon(Coupon coupon) {

		if(creatorMethod == 1) { // if the user is a company 
			createCouponByCompany(coupon);
			return coupon.getId();
		}
		else if (creatorMethod == 2) { // if the user is a customer
			purchaseCouponByCustomer(coupon);
			return coupon.getId();
		}
		else {
			return 0;
		}

	} // createCoupon - function

	@Override
	public void removeCoupon(Coupon coupon) {
		
		removeMethod(coupon, "company_coupon");
		removeMethod(coupon, "customer_coupon");
		removeMethodByCouponID(coupon.getId());
	}

	/**
	 * This fucntion is only for deleting by AdminFacede.
	 * when we delele a Company, and then we can delete the rest of the coupons she have.
	 * 
	 * @param long compID
	 * @author Raziel
	 */
	public void removeCouponOwnerID(long id) {
		removeMethodByOwnerID(id);
	}
	/**
     * Unused Function.
     * We have updateCouponV2 Now.
     * 
     * You can update with this function the all Coupon.
     * and in updateCouponV2 you can update only PRICE and END_DATE.
     * as requsted in the Guide book.
     * 
     * @author Raziel
     */
	@Deprecated
	@Override
	public void updateCoupon(Coupon coupon) {
		
       try {			
			
			String sql = "UPDATE Coupon SET Title=?, Start_Date=?, End_Date=?, Amount=?, "
					+ "Category=?, Message=?, Price=?, Image=? WHERE Coup_ID=?";
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement (sql);
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
			
       
	}

	public Coupon updateCouponV2(Coupon coupon) {
		
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
				
				} catch (SQLException e) {
					SharingData.setExeptionMessage(e.getMessage());
				}
				

	       return couponUP;
		}
	
	@Override
	public Coupon getCoupon(long id) {
	
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
		catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		return coupon;
	} // getCoupon - Function

	public Set<Coupon> getCouponsOfCompany(long compID) {
		
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
	public Collection<Coupon> getAllCoupon() {
		Set<Coupon> coupons = new HashSet<>(); 
		
		
		try {
			String sql = "SELECT coup_id FROM Coupon";
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		return coupons;
	} // getAllCoupon - function

	@Override
	@Deprecated
	public Set<Coupon> getCouponByType(CouponType category) {
		
		Set<Coupon> coupons = new HashSet<>();
		
		try {
			String sql = "SELECT coup_id FROM Coupon WHERE Category= '" 
		    + category.toString().toUpperCase() + "'";
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
			 
			ResultSet rs = prep.executeQuery(sql);

			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
			
			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			
		} catch (SQLException e) {
			e.getMessage();
		}
		return coupons;
	}

	public Set<Coupon> getCouponByTypeV2(String table, String colmun, long id, CouponType category) {
		
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
			SharingData.setExeptionMessage(e.getMessage());
		}
		return coupons;
	}
    
	/**
     * Unused Function.
     * We have getCouponByPriceV2 Now.
     * 
     * @author Raziel
     */
    @Deprecated
	public Set<Coupon> getCouponByPrice(double minPrice, double maxPrice) {
		
		Set<Coupon> coupons = new HashSet<>();
		try {
			String sql = "SELECT * FROM Coupon WHERE Price > " + minPrice + " AND Price < " + maxPrice;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
			
			ResultSet rs = prep.executeQuery(sql);

			// putting them in the Set<Coupon>
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
			
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
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

    public Set<Coupon> getCouponByPriceV2(String table, String colmun, long compID, double maxPrice) {
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
			SharingData.setExeptionMessage(e.getMessage());
		}
		
		return coupons;
    }
    
	private void removeMethod(Coupon coupon, String table) {
		
		IsExistDB.idExistV2Coupon(coupon.getId(), table);
		if(IsExistDB.getAnswer2() == true) {
			PreparedStatement prep = null;
			
			try {
				String sqlDELobject = "DELETE FROM " + table + " WHERE Coup_ID =?";
				prep = DBconnectorV3.getConnection().prepareStatement(sqlDELobject);
				long id = coupon.getId();
				prep.setLong(1, id);
				prep.executeUpdate();
				prep.clearBatch();
				
			} catch (SQLException e) {
				SharingData.setExeptionMessage(e.getMessage());
			} // catch
		} // if
	} // removeMethod
	
	/**
	 * This fucntion is only for deleting by AdminFacede.
	 * when we delele a Company, and then we can delete the rest of the coupons she have.
	 * 
	 * @param long compID
	 * @author Raziel
	 */
	private void removeMethodByOwnerID(long compID) {

			PreparedStatement prep = null;
			
			try {
				String sqlDELobject = "DELETE FROM coupon WHERE Owner_ID =?";
				prep = DBconnectorV3.getConnection().prepareStatement(sqlDELobject);
			
				prep.setLong(1, compID);
				prep.executeUpdate();
				prep.clearBatch();
				
			} catch (SQLException e) {
				SharingData.setExeptionMessage(e.getMessage());
			} // catch
	} // removeMethod

	private void removeMethodByCouponID(long coupID) {
		PreparedStatement prep = null;
		
		try {
			String sqlDELobject = "DELETE FROM coupon WHERE coup_ID =?";
			prep = DBconnectorV3.getConnection().prepareStatement(sqlDELobject);
		
			prep.setLong(1, coupID);
			prep.executeUpdate();
			prep.clearBatch();
			
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		} // catch

	} // removeMethodByCouponID

	private long createCouponByCompany(Coupon coupon) {
		
		long id = -1;
		
		// creating ResultSet
		ResultSet rs = null;
		try {
			
			IsExistDB.idExist(coupon.getOwnerID());
			if(IsExistDB.getAnswer2() == true) {
			
			// 1. Adding the new coupon to the COUPON TABLE. 
			
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
			rs = prep.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
			coupon.setId(id);
//			prep.close();
			prep.clearBatch();
//			rs.close();
			
			
			
			// 2. Adding the new CouponID to the COMPANY_COUPON TABLE.
			
			long compID = coupon.getOwnerID();
			String sqlQuery1 = "INSERT INTO company_coupon (Comp_ID ,Coup_ID) VALUES ("+ compID +  
				"," + coupon.getId() + ");";
			PreparedStatement prep1 = DBconnectorV3.getConnection().prepareStatement(sqlQuery1);
			prep1.executeUpdate();
			prep1.clearBatch();
			
		} // if
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}

		
		return id;
		
	}

	private Coupon purchaseCouponByCustomer(Coupon coupon) {
		Coupon purchasedCoupon = null;
		
		try{
			purchasedCoupon = getCoupon(coupon.getId());
			
			String sqlPurchaseCoupomForCustomer = "INSERT INTO customer_coupon (Cust_id, Coup_id) VALUES (" + SharingData.getIdsShare() + "," + coupon.getId() + ")";
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlPurchaseCoupomForCustomer);
			prep.executeUpdate();
//			prep.close();
//			prep.clearBatch();
		}
		catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}

		
		return purchasedCoupon;
	} // createCouponByCustomer

	public void setCreator(short creator) {
		CouponDBDAO.creatorMethod = creator;
	}
	
} // Class
