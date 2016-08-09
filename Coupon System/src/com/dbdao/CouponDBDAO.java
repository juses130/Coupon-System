package com.dbdao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.dao.interfaces.CouponDAO;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.facade.ClientType;
import com.javabeans.Company;
import com.javabeans.Coupon;
import com.javabeans.CouponType;
import com.task.and.singleton.DBconnectorV3;


/**
 * This is Coupon Database DAO Class.
 * Just implements the methods from CouponDAO in 'com.dao.intefaces' package. </br>
 * @author Raziel
 *
 */

public class CouponDBDAO implements CouponDAO{
	
	// Attributes
	private CouponFoundInDatabase existInDB = new CouponFoundInDatabase(); 
	
	@Override
	public Coupon createCoupon(Coupon coupon, Company company) throws DaoException{

		// We need to Check if the Company owns this coupon before.
		if(existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_ID) == true 
				|| existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_NAME) == true) {
			throw new DaoException("Error: Creating Coupon By Company - FAILED (You can create only ONE coupon with the same name!)");
		} // if
		else {
			try{	
				String sqlAddCoupon = "INSERT INTO coupon (Title, Start_Date, End_Date, " + 
						"Amount, Category, Message, Price, Image, Owner_ID)" + "VALUES(?,?,?,?,?,?,?,?,?)";	
				
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlAddCoupon, Statement.RETURN_GENERATED_KEYS);
				prep.setString(1, coupon.getTitle());
				prep.setDate(2, Date.valueOf(coupon.getStartDate()));
				prep.setDate(3, Date.valueOf(coupon.getEndDate()));
				prep.setInt(4, coupon.getAmount());
				prep.setString(5, coupon.getCategory().toString());
				prep.setString(6, coupon.getMessage());
				prep.setDouble(7, coupon.getPrice());
				prep.setString(8, coupon.getImage());
				prep.setLong(9, coupon.getOwnerID());
				
				prep.executeUpdate();
				ResultSet rs = prep.getGeneratedKeys();
				rs.next();
				long id = rs.getLong(1);
				coupon.setId(id);
				
				prep.clearBatch();
			} // try
			catch (SQLException | NullPointerException | FiledErrorException e) {
				throw new DaoException("Error: Creating Coupon By Company- FAILED (something went wrong..)");
			} // catch
			return coupon;
		} // else - isExistInJoinTables
	} // createCoupon 

	@Override
	public void removeCoupon(Coupon coupon, ClientType client) throws DaoException{
		// check if the coupon exist
		
		if (existInDB.couponExistByID(coupon) == true) {
			if(client == ClientType.ADMIN) {
				removeMethod(coupon, ClientType.ADMIN);
			} // if - ADMIN
			else if(client == ClientType.COMPANY) {
				removeMethod(coupon, ClientType.COMPANY);
			} // else if - COMPANY
		} // if - existOrNotByID
		else {
				throw new DaoException("Error: Removing Coupon - FAILED (Coupon is not exist in the DataBase)");
		} // else - existOrNotByID
	} // removeCoupon
	
	@Override
	public Coupon updateCoupon(Coupon coupon) throws DaoException{
		
		if(existInDB.couponExistByID(coupon) == true) {

		       try {
					
					String sql = "UPDATE coupon SET end_Date=?, amount=?, message=?, price=? WHERE coup_id=?";
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
						throw new DaoException("Error: Updating Coupon - FAILED (something went wrong)");
					} // catch

		       return coupon;
		} // if - exist
		else {
			throw new DaoException("Error: Updating Coupon - FAILED (Coupon dosen't exist in the DataBase)");
		} // else - exist

		} // updateCoupon
	
	@Override
	public Coupon getCoupon(long id, ClientType client) throws DaoException{

		Coupon coupon = getCouponMethod(id, client);
		return coupon;
	} // getCoupon - Function
// TODO: Im here - check the Code before sending
	@Override
	public Set<Coupon> getCoupons(long id, ClientType client) throws DaoException{
		
		Set<Coupon> coupons = new HashSet<>();
		
		if(client == ClientType.ADMIN) {
			coupons = getAllCouponsByAdmin();
			return coupons;
		} // if - ADMIN
		else if(client == ClientType.COMPANY) {
			coupons = getAllCouponsOfCompany(id);
			return coupons;
		} // else if - COMPANY
		else if(client == ClientType.CUSTOMER) {
			coupons = getAllCouponsOfCustomer(id);
			return coupons;
		} // else if - CUSTOMER
		else {
			throw new DaoException("Error: Getting Coupons - FAILED (Unidentified user)");
		} // else 
	} // getAllCoupons

	@Override
	public Set<Coupon> getCouponByType(long id, CouponType category, ClientType client) throws DaoException {
		
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
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
					
					
				}
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Get Coupons By Type (Admin) - FAILED (something went wrong)");
			}
			if(!coupons.isEmpty()) {
				return coupons;
			}
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
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
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Get Coupons By Type (Admin) - FAILED (something went wrong)");
			}
			if(!coupons.isEmpty()) {
				return coupons;
			}
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
			
			
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
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Get Coupons By Type (Admin) - FAILED (something went wrong)");
			} // catch
			if(!coupons.isEmpty()) {
				return coupons;
			}
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
			
		} // else if - CUSTOMER
		else {
			throw new DaoException("Error: Getting Coupons by Type (Category) - FAILD (Unidentified user)");
		} // getCouponByType

	}
    
    @Override
	public Set<Coupon> getCouponByPrice(long id, double price,ClientType client) throws DaoException {
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
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Get Coupons By Max Price (Admin) - FAILED (something went wrong)");
			}
			if(!coupons.isEmpty()) {
				return coupons;
			}
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
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
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoException("Error: Get Coupons By Max Price (Company) - FAILED (something went wrong)");
			}
			if(!coupons.isEmpty()) {
				return coupons;
			}
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
		}
		else if (client == ClientType.CUSTOMER) {
try {
				
				String sql = "SELECT coupon.* "
						+ "FROM customer_coupon "
						+ "LEFT JOIN coupon USING (coup_id) "
						+ "WHERE customer_coupon.cust_id =" + id + " "
						+ "AND customer_coupon.coup_id IS NOT NULL "
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
					coupon.setCategory((rs.getString("Category")));
					coupon.setImage(rs.getString("image"));
					coupon.setOwnerID(rs.getLong("owner_ID"));
					
					coupons.add(coupon);
				} // while 
				
			} catch (SQLException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoException("Error: Get Coupons By Max Price (Company) - FAILED (something went wrong)");
			}
			if(!coupons.isEmpty()) {
				return coupons;
			}
			else {
				throw new DaoException("Error: No Coupons Found");
			}  // else - Set<> is empty
		}
		else {
			throw new DaoException("Error: Getting all Coupons by Max Price (Customer) - FAILD");
		} // getCouponByType
		
    }
    //
    /**
     * Remove Coupon {@code Coupon} from the underlying database 
     *(or any other persistence storage) with the 3 Access Points. 
     * </br>
     * Admin {@code ClientType}, Company {@code ClientType}, 
     * Customer {@code ClientType}.
     * </p>
     * This is a Private method for removing Coupons by all the access points.</br>
     * By Admin {@code ClientType}: Removing from ALL Tables.</br>
     * By Company {@code ClientType}: Removing from Company Table ONLY. </br>
     * 
     * @param coupon {@code Coupon} Object
     * @param client {@code ClientType} Enum
     * @throws DaoException
     */
	private void removeMethod(Coupon coupon, ClientType client) throws DaoException{
		
	if(client == ClientType.ADMIN) {
		/*
		 * NOTE! Deleting coupon By the admin will delete the coupons from ALL Tables.
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
			throw new DaoException("Error: Removing Coupon - FAILED");
		} // catch
		
	} // if - ADMIN
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
				throw new DaoException("Error: Removing Coupon - FAILED");
			} // catch
		} // if - COMPANY
	} // removeMethod

    private Coupon getCouponMethod(long id, ClientType client) throws DaoException {
		Coupon coupon = new Coupon();
		/* We set the ID for the existOrNot check method.
		 * and if the coupon exist - we don't need to get the ID or setting it again. 
		 * we just gonna put it in the constructor below.
		 */
		// we have 3 access option of get method.
		if(client == ClientType.ADMIN) {
			try {
				coupon.setId(id);

			if(existInDB.couponExistByID(coupon) == true) {
				String title, message, image, category;
				Date stDate, enDate ;	
				int amount;
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
						category = rs.getString("Category");
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("Owner_ID");

						coupon =  new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category, message, price, image, ownerID);
					} // while

				return coupon;
			} // if - exist
			else {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (something went wrong)");
			}
		} // if - admin
		else if (client == ClientType.COMPANY) {
			try {
				coupon.setId(id);
			if(existInDB.couponExistByID(coupon) == true) {
				String title, message, image, category;
				Date stDate, enDate ;	
				int amount;
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
						category = rs.getString("Category");
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("Owner_ID");
						
						coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category,  message, price, image, ownerID);
					}
				
				return coupon;
			} // if - exist
			else {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (something went wrong)");
			}
		} // else - COMPANY
		else if (client == ClientType.CUSTOMER) {
			
			try {
				coupon.setId(id);
			if(existInDB.couponExistByID(coupon) == true) {
					
					String title, message, image, category;
					Date stDate, enDate ;	
					int amount;
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
						category = rs.getString("Category");
						message = rs.getString("Message");
						price = rs.getDouble("Price");
						image = rs.getString("image");
						ownerID = rs.getLong("owner_id");
						
						coupon = new Coupon(coupID, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, category, message, price, image, ownerID);

					return coupon;
				
			} // if - exist
			else {
				throw new DaoException("Error: Getting Coupon By ID - FAILED (Coupon dosen't exist in the DataBase)");
			} // else - exist
			}
			catch (SQLException | FiledErrorException e) {
				e.printStackTrace();
				throw new DaoException("Error: Getting Coupon By ID - FAILED (something went wrong)");
			}
		}
		else {
			throw new DaoException("Error: Getting Coupon - FAILED (Unidentified user)");
		}
		
    }

	/**
	 * <p>getAllCouponsOfCompany</p>
	 * 
	 * This is my add on. 
	 * it will bring all the coupons of the specefic company using owner ID for searching in the DataBase. (ownerID = company id).
	 * @param compID
	 * @return
	 * @throws DaoException
	 * 
	 * @author Raziel
	 */
	private Set<Coupon> getAllCouponsOfCompany(long compID) throws DaoException{
        Set<Coupon> coupons = new HashSet<>(); 
		try {
			String sql = "SELECT * FROM coupon WHERE owner_ID=" + compID;
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				
				Coupon coupon = new Coupon();
				
				coupon.setId(rs.getLong("coup_id"));
				coupon.setTitle(rs.getString("Title"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setCategory(rs.getString("Category"));
				coupon.setMessage(rs.getString("Message"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("image"));
				coupon.setOwnerID(rs.getLong("owner_ID"));

				// adding the current coupon to the collection
				coupons.add(coupon);
			}
		} catch (SQLException | FiledErrorException e) {
			throw new DaoException("Error: Getting Coupons of Company - FAILED (something went wrong)");
		}
		if(!coupons.isEmpty()) {
			return coupons;
		}
		else {
			throw new DaoException("Error: No Coupons Found");
		}  // else - Set<> is empty
	} // getAllCouponsOfCompany
	
	private Set<Coupon> getAllCouponsOfCustomer(long custID) throws DaoException{
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
				coupon.setCategory(rs.getString("Category"));
				coupon.setMessage(rs.getString("Message"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("image"));
				coupon.setOwnerID(rs.getLong("owner_ID"));
				
				// adding the current coupon to the collection
				coupons.add(coupon);
			}
		} catch (SQLException | FiledErrorException e) {
			throw new DaoException("Error: Getting Coupons of Customer - FAILED (something went wrong)");
		}
		if(!coupons.isEmpty()) {
			return coupons;
		}
		else {
			throw new DaoException("Error: No Coupons Found");
		}  // else - Set<> is empty
	} // getAllCouponsOfCustomer

	private Set<Coupon> getAllCouponsByAdmin() throws DaoException{
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
				coupon.setCategory(rs.getString("Category"));
				coupon.setMessage(rs.getString("Message"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("image"));
				coupon.setOwnerID(rs.getLong("owner_ID"));
				
				// adding the current coupon to the collection
				coupons.add(coupon);
			} // while
		} catch (SQLException | FiledErrorException e) {
			e.printStackTrace();
			throw new DaoException("Error: Getting All Coupons By ADMIN - FAILED (something went wrong)");
		} // catch
		if(!coupons.isEmpty()) {
			return coupons;
		} // if - empty
		else {
			throw new DaoException("Error: No Coupons Found");
		}  // else - Set<> is empty
	} // getAllCouponsByAdmin
	
} // Class
