package com.dbdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import com.beans.Coupon;
import com.beans.Customer;
import com.dao.interfaces.CustomerDAO;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.task.and.singleton.DBconnector;
import com.task.and.singleton.DatabaseInfo;

/**
 * This is Customer Database DAO Class.
 * Just implements the methods from CustomerDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CustomerDBDAO implements CustomerDAO {
	// Attributes
	private CouponFoundInDatabase existInDB = new CouponFoundInDatabase();
	
	public CustomerDBDAO() {}
	
	@Override
	public boolean login(String userName, String password) throws DaoException {
		
		boolean hasRows = false;
        String sqlName = "SELECT * "
        		+"FROM " + DatabaseInfo.getDBname() + ".customer "
        		+ "WHERE "
				+ "customer.Cust_name= '" + userName + "'" 
        		+ " AND " + "customer.password= '" + password + "' ";
		try {
			Statement stat = DBconnector.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sqlName);
			
			rs.next();
			if (rs.getRow() != 0) {
					hasRows = true;
				} // if
			
		} catch (SQLException | NullPointerException e) {
			
			throw new DaoException("Error: Customer Login - FAILED (something went wrong..)");
		} // catch
	return hasRows;
	} // login
	
	@Override
	public void createCustomer(Customer customer) throws DaoException{
		
		// check if the customer is not exist (Name and ID)
		if (custotmerExistByID(customer.getId()) == false 
				&& custotmerExistByName(customer.getCustName()) == false) {
			try {			
				String sqlQuery = "INSERT INTO " + DatabaseInfo.getDBname() + ".customer (CUST_NAME, PASSWORD) VALUES(?,?)";
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
				
				// now we will put the in their places.
				prep.setString(1, customer.getCustName());
				prep.setString(2, customer.getPassword());
				prep.executeUpdate();

				// This 2 lines will make it run to the next null row. and line 3 will set the ID (next new row).
				ResultSet rs = prep.getGeneratedKeys();
				while(rs.next()) {
					customer.setId(rs.getLong(1));;
				} // while
				prep.clearBatch();
			} catch (SQLException | NullPointerException e) {
				throw new DaoException("Error: Creating New Customer - FAILED (something went wrong)");
			} // catch
		} // if
		else {
			throw new DaoException("Error: Creating Customer - FAILED (Customer is already exist in the DataBase)");
		} // else
	} // createCompany - Function

	@Override
	public Coupon addCoupon(Coupon coupon, long custID) throws DaoException {
		
		if(existInDB.purchasedBefore(coupon.getId(), custID) == false) {
			coupon = addCouponMethod(coupon, custID);
			return coupon;	
		} // if
		else {
			throw new DaoException("Error: Purchase Coupon - FAILED (The coupon has been purchased before)");			
		} // else
	} // addCoupon
	
	@Override
	public void removeCustomer(Customer customer) throws DaoException, FiledErrorException {
		
		DetectionBy detect = customerDetectInDB(customer);

			if(detect == DetectionBy.ID) {
				removeCustomerMethod(customer.getId());
				
			} // if - id
			else if (customerDetectInDB(customer) == DetectionBy.NAME) {
				customer = getCustomer(customer.getCustName());
				removeCustomerMethod(customer.getId());
			} // else if - name
			else {
				throw new DaoException("Error: Removing Customer - FAILED (Customer dosen't exist in the DataBase)");
			} // else
	} // removeCustomer - Function

	@Override
	public void updateCustomer(Customer customer) throws DaoException{
		
		// check if the customer exist
		if (custotmerExistByID(customer.getId()) == true) {
			
		       try {
					String sqlUpdateCustomerTable = "UPDATE " + DatabaseInfo.getDBname() + ".customer "
							+ "SET Cust_name=?, password=? "
							+ "WHERE Cust_ID=?";
					PreparedStatement prep = DBconnector.getConnection().prepareStatement (sqlUpdateCustomerTable);
					prep.setString(1, customer.getCustName());
					prep.setString(2, customer.getPassword());
					prep.setLong(3, customer.getId());
					
					prep.executeUpdate();
					//prep.close();
					
					String sqlUpdateCustomer_CouponTable = "UPDATE customer_coupon SET Cust_ID=? WHERE Cust_ID=?";
					PreparedStatement prep1 = DBconnector.getConnection().prepareStatement(sqlUpdateCustomer_CouponTable);
					prep1.setLong(1, customer.getId());
					prep1.setLong(2, customer.getId());
					prep1.executeUpdate();

					} catch (SQLException e) {
						throw new DaoException("Error - Updating Customer - FAILED (something went wrong..)");
					} // catch
			
		}// if - exist
		else {
			throw new DaoException("Error: Removing Customer - FAILED (Customer is not exist in the DataBase)");
		} // else	
	} // updateCustomer
	
	@Override
	public Customer getCustomer(long custID) throws DaoException{
		
		// check if the customer exist
		if (custotmerExistByID(custID) == true) {
			Customer customer = null;
			String custName = null, password = null;
			
			try {
				String sqlSEL = "SELECT * FROM " + DatabaseInfo.getDBname() + ".customer WHERE Cust_ID= ?" ;
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSEL);
				prep.setLong(1, custID);
				
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
				custName = rs.getString("Cust_name");
				password = rs.getString("password");	
				} // while
				
				customer = new Customer(custID, custName, password);	
				return customer;
			} // try
			catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Getting Customer By ID - FAILED");
			} // catch
		} // if
		else {
			throw new DaoException("Error: Getting Customer - FAILED (Customer is not exist in the DataBase)");
		} // else	
	} // getCustomer - ID long

	@Override
	public Customer getCustomer(String custName) throws DaoException {
		
		Customer customer = null;
		try {

		if(custotmerExistByName(custName) == true) {
			
			String password = null;
			long id = 0;

				String sqlSEL = "SELECT * FROM " + DatabaseInfo.getDBname() + ".customer "
						+ "WHERE cust_name= ?" ;
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSEL);
				prep.setString(1, custName);
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
				password = rs.getString("password");
				id = rs.getLong("cust_id");
				} // while
				
				customer = new Customer(id, custName, password);	
				return customer;
		} // if
		else {
			throw new DaoException("Error: Getting Customer By Name - FAILED (Customer is not exist in the DataBase)");
		} // else - exist
		} catch (SQLException | FiledErrorException e) {
			throw new DaoException("Error: Getting Customer By Name - FAILED (Customer is not exist in the DataBase)");
		} // catch
	} // getCustomer - Name String

	@Override
    public Collection<Customer> getAllCustomers() throws DaoException{
		
		String sql = "SELECT * FROM " + DatabaseInfo.getDBname() + ".customer";
		Collection<Customer> customers = new HashSet<>();
		Customer c = null;
		ResultSet rs = null;
		
		try {
			Statement stat = DBconnector.getConnection().createStatement();
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				c = new Customer();
				c.setId(rs.getLong("Cust_ID"));
				c.setCustName(rs.getString("Cust_name"));
				c.setPassword(rs.getString("password"));
				//c.setEmail(rs.getString("email"));
				
				customers.add(c);
			} // while loop

		} catch (SQLException | FiledErrorException e) {
			e.printStackTrace();
		} // catch
		return customers;
	} // getAllCompanies

	@Override
	public Collection<Coupon> getCoupons(long custID) throws DaoException {
		Collection<Coupon> coupons = new HashSet<>(); 
		try {
			String sql = "SELECT " + DatabaseInfo.getDBname() + ".coupon.* "
					+ "FROM " + DatabaseInfo.getDBname() + ".customer_coupon "
					+ "LEFT JOIN " + DatabaseInfo.getDBname() + ".coupon USING (coup_id) "
					+ "WHERE " + DatabaseInfo.getDBname() + ".customer_coupon.cust_id =" + custID + " "
					+ "AND " + DatabaseInfo.getDBname() + ".customer_coupon.Coup_ID = " + DatabaseInfo.getDBname() + ".coupon.Coup_id "
					+ "AND " + DatabaseInfo.getDBname() + ".customer_coupon.Coup_ID IS NOT NULL";
			Statement stat = DBconnector.getConnection().createStatement();
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
			throw new DaoException("Error: Getting Coupons of Customer - FAILED (something went wrong)");
		} // catch
		return coupons;
	} // getCouponsno 
	
	@Override
	public Collection<Coupon> getCouponForPurchase(long custID) throws DaoException{
// TODO: NO WORKING YET...
		Collection<Coupon> coupons = new HashSet<>(); 
		
        try {
        	String sql = "SELECT * "
        			+ " FROM " + DatabaseInfo.getDBname() + ".coupon"
        			+ " WHERE " + DatabaseInfo.getDBname() + ".coupon.coup_id NOT IN ("
        					+ "SELECT coup_id FROM " +  DatabaseInfo.getDBname() + ".customer_coupon WHERE cust_id =" + custID + ") "
        							+ "AND amount > 0"  ;
			Statement stat = DBconnector.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {				
				Coupon coupon = new Coupon(
						rs.getLong("coup_id"),
						rs.getString("Title"),
						rs.getDate("start_date").toLocalDate(),
						rs.getDate("end_date").toLocalDate(),
						rs.getInt("amount"),
						rs.getString("Category"),
						rs.getString("Message"),
						rs.getDouble("Price"),
						rs.getString("image"),
						rs.getLong("owner_ID"));
				
				// adding the current coupon to the collection
				coupons.add(coupon);
			} // while
			stat.clearBatch();
		} catch (SQLException | FiledErrorException e) {
			e.printStackTrace();
			throw new DaoException("Error: Getting All Coupons By CUSTOMER- FAILED (something went wrong)");
		} // catch
		if(!coupons.isEmpty()) {
			return coupons;
		} // if - empty
		else {
			throw new DaoException("Error: No Coupons Found");
		}  // else - Set<> is empty
	} // getAllCoupons
	
	/**
	 * 
	* This is a Private remove method.</br>
	 * That's is add-on. from here the function will delete the customer from is specific tables.
	 * 
	 * @param custID {@code long} of the Customer. 
	 * @author Raziel
	 */
    private void removeCustomerMethod(long custID) throws DaoException{
		
		boolean hasRow = false;
		PreparedStatement prep = null;
		ResultSet rs = null;
		// Check if the customer has coupons BEFORE Deleting the customer.
		String sqlCheckExist = "SELECT * FROM " + DatabaseInfo.getDBname() + ".customer_coupon "
				+ "WHERE Cust_ID=" + custID;
		try {
   		prep = DBconnector.getConnection().prepareStatement(sqlCheckExist);
   		rs = prep.executeQuery();
   		while(rs.next()) {
   			// if we have rows in customer_coupon, make hasRow = true.
   			hasRow = true;
   		} // while
			prep.clearBatch();			
   		if(hasRow == true) {

   			String sqlDeleteALL = "DELETE " + DatabaseInfo.getDBname() + ".customer_coupon.*, " + DatabaseInfo.getDBname() + ".customer.*"
						+ " FROM " + DatabaseInfo.getDBname() + ".customer"
						+ " LEFT JOIN " + DatabaseInfo.getDBname() + ".customer_coupon USING (cust_id)"
						+ " WHERE " + DatabaseInfo.getDBname() + ".customer.cust_id=" + custID
						+ " AND " + DatabaseInfo.getDBname() + ".customer.cust_id IS NOT NULL";
				prep = DBconnector.getConnection().prepareStatement(sqlDeleteALL);
				prep.executeUpdate();
				prep.clearBatch();
   		} // if - hasRow		
			else {

				String sqlOnlyFromCustomer = "DELETE FROM customer WHERE Cust_ID=" + custID;
				PreparedStatement prep2 = DBconnector.getConnection().prepareStatement(sqlOnlyFromCustomer);
				prep2.executeUpdate();
				prep2.clearBatch();
			} // else - hasRow
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error: Removing Customer - FAILED (something went wrong..)");
		} // catch
	} // removeMethodPart2
	
	/**
	 * This is a Private addCoupon Method</br>
	 * This method adds a {@code Coupon} for a specific {@code Customer} by it's ID {@code long}.
	 * @param {@code Coupon} Object
	 * @param custID {@code long}
	 * @return a {@code Coupon} Object .
	 * @throws DaoException
	 */
	private Coupon addCouponMethod(Coupon coupon, long custID) throws DaoException {
					
			try{
				// check if we have the coupon in stock
				String sqlCheckAmount = "SELECT " + DatabaseInfo.getDBname() + ".coupon.*"
						+ "FROM " + DatabaseInfo.getDBname() + ".coupon "
						+ "WHERE " + DatabaseInfo.getDBname() + ".coupon.Coup_id=" + coupon.getId() + " " 
						+ "AND " + DatabaseInfo.getDBname() + ".coupon.Amount > 0";
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlCheckAmount);
				ResultSet rs = prep.executeQuery();
				rs.next();
				int currentAmount = 0;
				if(rs.getRow() != 0) {
					currentAmount = rs.getInt("amount");
					currentAmount = currentAmount - 1;
				} // if
				else {
					throw new DaoException("Error: Purchase Coupon - FAILD (The coupon out of stock)");
				} // else
				// clearing the statement.
				prep.clearBatch();
				
				// Updating the amount
				String sqlUpdateAmount = "UPDATE `"+ DatabaseInfo.getDBname() + "`.`coupon` "
						+ "SET `amount`='" + currentAmount + "' "
						+ "WHERE `coup_id`='" + coupon.getId() +"'";
				prep = DBconnector.getConnection().prepareStatement(sqlUpdateAmount);
				prep.executeUpdate();
				
				// Clearing the statement
				prep.clearBatch();

				// Adding the coupon to customer_coupon
				String sqlAddCoupon = "INSERT INTO " + DatabaseInfo.getDBname() + ".customer_coupon (cust_id, coup_id)" 
				+ "VALUES(" + custID + "," + coupon.getId() + ")";	
				prep = DBconnector.getConnection().prepareStatement(sqlAddCoupon);
				prep.executeUpdate();
				prep.clearBatch();
			} // try
			catch (SQLException | NullPointerException e) {
				throw new DaoException("Error: Creating Coupon By Customer- FAILED (something went wrong..)");			
			} // catch
			return coupon;
	} // addCouponMethod

	/**
	 * This is a my add-on: CustomerDBDAO. This method checks if the Customer {@code ID} exist in the Database.
	 * @param custID a {@code long} Customer {@code ID}.
	 * @return {@code true} if the Customer {@code ID} exist in Database, otherwise {@code false}
	 * @throws DaoException
	 */
   private boolean custotmerExistByID(long custID) throws DaoException {
		
		Statement stat = null;
		ResultSet rs = null;
		boolean answer = false;

		try {
			String sqlName = "SELECT Cust_ID FROM " + DatabaseInfo.getDBname() + ".customer WHERE "
					+ "Cust_ID= " + custID;
					
					stat = DBconnector.getConnection().createStatement();
					rs = stat.executeQuery(sqlName);
					rs.next();
					
					if (rs.getRow() != 0) {
						answer = true;
					} // if
					
					stat.clearBatch();
		} catch (SQLException e) {
			throw new DaoException("Error: cannot make sure if the customer is in the DataBase");
		} // catch
		return answer;
	} // custotmerExistByID
	
   /**
	 * This is a my add-on: CustomerDBDAO. This method checks if the CustomerDBDAO custName exist in the Database.
	 * @param custName a {@code String} Customer {@code custName}.
	 * @return {@code true} if the Customer {@code custName} exist in Database, otherwise {@code false}
	 * @throws DaoException
	 */
   private boolean custotmerExistByName(String custName) throws DaoException {

	   Statement stat = null;
		ResultSet rs = null;
		boolean answer = false;
		  
		   try {
				String sqlName = "SELECT Cust_name FROM " + DatabaseInfo.getDBname() + ".customer WHERE "
						+ "cust_name= '" + custName + "'";
				stat = DBconnector.getConnection().createStatement();
				rs = stat.executeQuery(sqlName);
				rs.next();
				if(rs.getRow() != 0) {
					answer = true;	
				} // if

				stat.clearBatch();
	            } catch (SQLException e) {
	    			throw new DaoException("Error: cannot make sure if the customer is in the DataBase");
	            } // catch
		   return answer;
	} // custotmerExistByName
	
   /**
    * This is my add-on: CustomerDBDAO. This method using the DetetionBy {@code Enum}. This {@code Enum} created because in the future we'll 
    * have the Option to search Customer by the custName {@code String} or by the custID {@code long}.
    * For now (year 2016), removeCustomer {@code Method}  is the only method that's implement it. </br>
    * 
    * @param  a Customer {@code Object}
    * @return DetectionBy {@code Enum}.  that will be <b>one</b> of the <u>third</u> options: ID, NAME or NONE.
    * @throws DaoException
    */
   private DetectionBy customerDetectInDB(Customer customer) throws DaoException{
	   
	   boolean isExist = false;
	   if(customer.getId() > 0) {
			isExist = custotmerExistByID(customer.getId());

			if(isExist == true) {
				return DetectionBy.ID;
			} // if - inner
			else {
				throw new DaoException("Error: Detection By ID - FAILED (The customer dosen't exist in the DataBase)");
			} // else - inner
		} // if 
		else if(!customer.getCustName().equals(null) || !customer.getCustName().isEmpty()) {
			if(custotmerExistByName(customer.getCustName()) == true) {
				return DetectionBy.NAME;
			} // if - inner
			else {
				throw new DaoException("Error: Detection By Name - FAILED (The customer dosen't exist in the DataBase)");
			} // else - inner
		} // else if
		return DetectionBy.NONE;
	} // customerDetectInDB
   
} // CustomerDBDAO
