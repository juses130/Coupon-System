package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.added.functions.DBconnectorV3;
import com.dao.interfaces.CouponDAO;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.ClientType;
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
	public Coupon createCoupon(Coupon coupon) throws DaoExeption{
		//TODO: what to do here?
		return coupon;
	} // createCoupon - function

	@Override
	public void removeCoupon(Coupon coupon, ClientType client) throws DaoExeption{
		// check if the coupon exist
		if (existOrNotByID(coupon) == true) {
			if(client == ClientType.ADMIN) {
				removeMethod(coupon, ClientType.ADMIN);
			}
			else if(client == ClientType.COMPANY) {
				removeMethod(coupon, ClientType.COMPANY);
			}
		}
		else {
				throw new DaoExeption("Error: Removing Coupon - FAILED (Coupon is not exist in the DataBase)");
		} // else
	}
	
	@Override
	public Coupon updateCoupon(Coupon coupon) throws DaoExeption{
		
		if(existOrNotByID(coupon) == true) {

		       try {
					
					String sql = "UPDATE Coupon SET End_Date=?, Amount=?, Message=?, Price=? WHERE Coup_ID=?";
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement (sql);
					
					// set all the Coupon to the new one.
					coupon.setEndDate(coupon.getEndDate());
					coupon.setAmount(coupon.getAmount());
					coupon.setMessage(coupon.getMessage());
					coupon.setPrice(coupon.getPrice());
					
					prep.setDate(1, Date.valueOf(coupon.getEndDate()));
					prep.setLong(2, coupon.getAmount());
					prep.setString(3, coupon.getMessage());
					prep.setDouble(4, coupon.getPrice());
					prep.setLong(5, coupon.getId());
					
					prep.executeUpdate();
					
					} catch (SQLException | FiledErrorException e) {
						throw new DaoExeption("Error: Updating Coupon - FAILED (something went wrong)");
					}

		       return coupon;
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
	public Set<Coupon> getCoupons(long id, ClientType client) throws DaoExeption{
		Set<Coupon> coupons = new HashSet<>();
		
		if(client == ClientType.COMPANY) {
			coupons = getAllCouponsOfCompany(id);
			return coupons;
		}
		else if(client == ClientType.CUSTOMER) {
			coupons = getAllCouponsOfCustomer(id);
			return coupons;
		}
		else if(client == ClientType.ADMIN) {
			coupons = getAllCouponsByAdmin();
			return coupons;

		}
		else {
			throw new DaoExeption("Error: Getting Coupons - FAILED (Unidentified user)");
		}
	} // getAllCoupons

	@Override
	public Set<Coupon> getCouponByType(long id, CouponType category,ClientType client) throws DaoExeption {
		
		Set<Coupon> coupons = new HashSet<>();
		
		if(client == ClientType.ADMIN) {
			
			try {
				String sql = "SELECT * FROM coupon WHERE Category='"+ category.toString() + "'";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					/* I am aware that I could write all this in a single line 
					 * through the Constructor, but in this form it looks like 
					 * more readable in my opinion. 
					 */
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
					
					
				}
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Get Coupons By Type (Admin) - FAILED (something went wrong)");
			}
			return coupons;
		}
		else if(client == ClientType.COMPANY) {
			try {
				String sql = "SELECT coupon.* "
						+ "FROM company_coupon "
						+ "LEFT JOIN coupon USING (coup_id)"
						+ "WHERE company_coupon.Comp_ID=" + id
						+ " AND company_coupon.Comp_ID IS NOT NULL "
						+ "AND company_coupon.Coup_ID = coupon.Coup_id "
						+ "AND coupon.Category='" +  category.toString() + "'";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Get Coupons By Type (Admin) - FAILED (something went wrong)");
			}
			return coupons;
			
			
		} // else if - COMPANY
		else if (client == ClientType.CUSTOMER) {
			
			try {
				String sql = "SELECT coupon.* "
						+ "FROM customer_coupon "
						+ "LEFT JOIN coupon USING (coup_id)"
						+ "WHERE customer_coupon.Cust_ID=" + id
						+ " AND customer_coupon.Cust_ID IS NOT NULL "
						+ "AND customer_coupon.coup_id = coupon.Coup_id "
						+ "AND coupon.Category='" +  category.toString() + "'";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Get Coupons By Type (Admin) - FAILED (something went wrong)");
			} // catch
			return coupons;
			
		} // else if - CUSTOMER
		else {
			throw new DaoExeption("Error: Getting Coupons by Type (Category) - FAILD (Unidentified user)");
		} // getCouponByType

	}
    
    @Override
	public Set<Coupon> getCouponByPrice(long id, double price,ClientType client) throws DaoExeption {
    	Set<Coupon> coupons = new HashSet<>();
		
		if(client == ClientType.ADMIN) {
			
			try {
				String sql = "SELECT * FROM coupon WHERE Price <= " + price;
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Get Coupons By Max Price (Admin) - FAILED (something went wrong)");
			}
			return coupons;
		}
		else if(client == ClientType.COMPANY) {
			try {
				
				String sql = "SELECT coupon.* "
						+ "FROM company_coupon "
						+ "LEFT JOIN coupon USING (coup_id) "
						+ "WHERE company_coupon.comp_ID =" + id + " "
						+ "AND company_coupon.Coup_ID IS NOT NULL "
						+ "AND coupon.Price <= " + price;
				
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sql);
				ResultSet rs = prep.executeQuery();
				
				while(rs.next()) {
					Coupon coupon = new Coupon();
					
					coupon.setId(rs.getLong("coup_id"));
					coupon.setTitle(rs.getString("Title"));
					coupon.setStartDate(rs.getDate("start_date").toLocalDate());
					coupon.setEndDate(rs.getDate("end_date").toLocalDate());
					coupon.setAmount(rs.getInt("amount"));
					coupon.setMessage(rs.getString("Message"));
					coupon.setPrice(rs.getDouble("Price"));
					coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoExeption("Error: Get Coupons By Max Price (Company) - FAILED (something went wrong)");
			}
			return coupons;
		}
//		else if (client == ClientType.CUSTOMER) {
//			
//		}
		else {
			throw new DaoExeption("Error: Getting all Coupons by Max Price (Customer) - FAILD");
		} // getCouponByType
		
    }
    
	private void removeMethod(Coupon coupon, ClientType client) throws DaoExeption{
		
	if(client == ClientType.ADMIN) {
		/*
		 * NOTE! Deleting coupon By the admin will delette the coupons from ALL Tables.
		 */
		String sqlDELid = "DELETE coupon.*, company_coupon.*, customer_coupon.* "
				+ "FROM coupon "
				+ "LEFT JOIN company_coupon USING (coup_id) "
				+ "LEFT JOIN customer_coupon USING (coup_id) "
				+ "WHERE coupon.coup_id=" + coupon.getId()
				+ " AND coupon.coup_id IS NOT NULL";
		
		PreparedStatement prep;
		try {
			prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid);
			prep.executeUpdate();
			prep.clearBatch();

			
		} catch (SQLException e) {
			throw new DaoExeption("Error: Removing Coupon - FAILED");
		}
		
	}
	else if(client == ClientType.COMPANY) {
			
			/*
			 * NOTE! we are not deleting coupons from customer_coupon.
			 * The business logic says that the Cusotmer will lose his coupon
			 * ONLY when the coupon END-DATE will Expired.
			 */
			
			String sqlDELid = "DELETE coupon.*, company_coupon.*  "
					+ "FROM company_coupon "
					+ "LEFT JOIN coupon USING (coup_id) "
					+ "WHERE company_coupon.Comp_ID=" + coupon.getOwnerID()
					+ " AND company_coupon.Comp_ID IS NOT NULL";
			
			PreparedStatement prep;
			try {
				prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid);
				prep.executeUpdate();
				prep.clearBatch();

				
			} catch (SQLException e) {
				throw new DaoExeption("Error: Removing Coupon - FAILED");
			}
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
		/* We set the ID for the existOrNot check method.
		 * and if the coupon exist - we don't need to get the ID or setting it again. 
		 * we just gonna put it in the constructor below.
		 */
		// we have 3 access option of get method.
		if(client == ClientType.ADMIN) {
			try {
				coupon.setId(id);

			if(existOrNotByID(coupon) == true) {
				String title, message, image;
				Date stDate, enDate ;	
				int amount;
				CouponType category = null;
				double price;
				long ownerID = -1;
				
				
					
					String sqlSEL = "SELECT * FROM Coupon WHERE Coup_ID= ?" ;
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
					prep.setLong(1, id);
					
					ResultSet rs = prep.executeQuery();
					
					while(rs.next()) {
						
						title = rs.getString("Title");
						stDate = rs.getDate("start_date");
						enDate = rs.getDate("end_date");
						amount = rs.getInt("amount");
						category = CouponType.valueOf(rs.getString("Category").toUpperCase());
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("Owner_ID");

						coupon =  new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category, message, price, image, ownerID);
					}

				
				return coupon;
			} // if - exist
			else {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (something went wrong)");
			}
		} // if - admin
		else if (client == ClientType.COMPANY) {
			try {
				coupon.setId(id);
			if(existOrNotByID(coupon) == true) {
				String title, message, image;
				Date stDate, enDate ;	
				int amount;
				CouponType category = null;
				double price;
				long ownerID = -1;
				
				
					
					String sqlSELCoupByCompany = "SELECT coupon.Coup_ID, title, Start_Date, End_Date, Amount, category, message, price, image, Owner_ID from coupon, company_coupon where coupon.Coup_ID=? = company_coupon.Coup_ID";
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSELCoupByCompany);
					prep.setLong(1, id);
					ResultSet rs = prep.executeQuery();
					
					while(rs.next()) {
						title = rs.getString("Title");
						stDate = rs.getDate("start_date");
						enDate = rs.getDate("end_date");
						amount = rs.getInt("amount");
						category = CouponType.valueOf(rs.getString("Category").toUpperCase());
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("Owner_ID");
						
						coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category,  message, price, image, ownerID);
						System.out.println(coupon.toString());
					}
				
				return coupon;
			} // if - exist
			else {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (something went wrong)");
			}
		} // else - COMPANY
		else if (client == ClientType.CUSTOMER) {
			
			try {
				coupon.setId(id);
			if(existOrNotByID(coupon) == true) {
				if(isCouponInStock(coupon) == true) {
					
					String title, message, image;
					Date stDate, enDate ;	
					int amount;
					CouponType type = null;
					double price;
					long ownerID, coupID;
				
											
						String sqlSelCoupAfterCompnay = "SELECT * FROM coupon WHERE coup_id= ?" ;
						PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSelCoupAfterCompnay);
						prep.setLong(1, id);
						ResultSet rs = prep.executeQuery();
						rs.next();

						coupID = rs.getLong("coup_id");
						title = rs.getString("Title");
						stDate = rs.getDate("start_date");
						enDate = rs.getDate("end_date");
						amount = rs.getInt("amount");
						type = CouponType.valueOf(rs.getString("Category").toUpperCase());
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("owner_id");
						
						coupon = new Coupon(coupID, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, type, message, price, image, ownerID);

					
					return coupon;
				} // if - isCouponInStock
				else {
					throw new DaoExeption("Error: Purchase Coupon - FAILD (The coupon out of stock)");
				}
				
			} // if - exist
			else {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Getting Coupon By ID - FAILED (something went wrong)");
			}
		}
		else {
			throw new DaoExeption("Error: Getting Coupon - FAILED (Unidentified user)");
		}
		
    }
    
    private boolean isCouponInStock(Coupon coupon) throws DaoExeption {
    	
    	boolean inStock = false;
    	
    	
    	
		try {
			String sqlQuery = "SELECT coupon.* "
	    			+ "FROM coupon "
	    			+ "WHERE coupon.Coup_id =" + coupon.getId() + " "
	    			+ "AND coupon.Amount > 0";
	    	
			PreparedStatement prep;
			prep = DBconnectorV3.getConnection().prepareStatement(sqlQuery);
			ResultSet rs = prep.executeQuery();
			rs.next();
			if(rs.getRow() != 0) {
				inStock = true;
			} // if - hasrow
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoExeption("Error: Checking If Coupon In Stock - FAILED (something went wrong)");
			
		} // catch
    	
    	return inStock;
    	
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
				
				coupon.setId(rs.getLong("coup_id"));
				coupon.setTitle(rs.getString("Title"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
				coupon.setMessage(rs.getString("Message"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("image"));
				coupon.setOwnerID(rs.getLong("owner_ID"));

				// adding the current coupon to the collection
				coupons.add(coupon);
			}
		} catch (SQLException | FiledErrorException e) {
			throw new DaoExeption("Error: Getting Coupons of Company - FAILED (something went wrong)");
		}
		return coupons;
	} // getCoupon - Function
	
	private Set<Coupon> getAllCouponsOfCustomer(long custID) throws DaoExeption{
        Set<Coupon> coupons = new HashSet<>(); 
		try {
			String sql = "SELECT coupon.* "
					+ "FROM customer_coupon "
					+ "LEFT JOIN coupon USING (coup_id) "
					+ "WHERE customer_coupon.cust_id =" + custID + " "
					+ "AND customer_coupon.Coup_ID = coupon.Coup_id "
					+ "AND customer_coupon.Coup_ID IS NOT NULL";
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				
				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong("coup_id"));
				coupon.setTitle(rs.getString("Title"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
				coupon.setMessage(rs.getString("Message"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("image"));
				coupon.setOwnerID(rs.getLong("owner_ID"));
				
				// adding the current coupon to the collection
				coupons.add(coupon);
			}
		} catch (SQLException | FiledErrorException e) {
			throw new DaoExeption("Error: Getting Coupons of Company - FAILED (something went wrong)");
		}
		return coupons;
	} // getCoupon - Function

	private Set<Coupon> getAllCouponsByAdmin() throws DaoExeption{
       
		Set<Coupon> coupons = new HashSet<>(); 
		
        try {
			String sql = "SELECT * FROM coupon";
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				
				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong("coup_id"));
				coupon.setTitle(rs.getString("Title"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(CouponType.valueOf(rs.getString("Category").toUpperCase()));
				coupon.setMessage(rs.getString("Message"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("image"));
				coupon.setOwnerID(rs.getLong("owner_ID"));
				
				// adding the current coupon to the collection
				coupons.add(coupon);
			}
		} catch (SQLException | FiledErrorException e) {
			throw new DaoExeption("Error: Getting Coupons of Company - FAILED (something went wrong)");
		}
		return coupons;
	}
	
} // Class
