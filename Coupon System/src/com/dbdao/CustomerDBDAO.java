package com.dbdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import com.dao.interfaces.CustomerDAO;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.facade.DetectionBy;
import com.javabeans.Coupon;
import com.javabeans.Customer;
import com.task.and.singleton.DBconnectorV3;

/**
 * This is Customer Database DAO Class.
 * Just impelemnts the methods from CustomerDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CustomerDBDAO implements CustomerDAO {
	// attr
	private CouponFoundInDatabase existInDB = new CouponFoundInDatabase();
	
	public CustomerDBDAO() {}
	
	@Override
	public boolean login(String userName, String password) throws DaoException {
		
		boolean hasRows = false;
        String sqlName = "SELECT Cust_id, Cust_name, password FROM customer WHERE "
				+ "Cust_name= '" + userName + "'" + " AND " + "password= '" 
				+ password + "'";
		try {
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sqlName);
			
			rs.next();
			if (rs.getRow() != 0) {
					hasRows = true;
				}
			
		} catch (SQLException | NullPointerException e) {
			throw new DaoException("Error: Customer Login - FAILED");
		} // catch
	return hasRows;
	}
	
	@Override
	public void createCustomer(Customer customer) throws DaoException{
		
		// check if the customer is not exist (Name and ID)
		if (custotmerExistByID(customer.getId()) == false 
				&& custotmerExistByName(customer.getCustName()) == false) {
			try {
				
				String sqlQuery = "INSERT INTO customer (CUST_NAME, PASSWORD) VALUES(?,?)";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
				
				// now we will put the in their places.
				prep.setString(1, customer.getCustName());
				prep.setString(2, customer.getPassword());
				prep.executeUpdate();
				

				// This 2 lines will make it run to the next null row. and line 3 will set the ID (next new row).
				ResultSet rs = prep.getGeneratedKeys();
				while(rs.next()) {
					customer.setId(rs.getLong(1));;
				}
				prep.clearBatch();
			} // try 
			catch (SQLException | NullPointerException e) {
				throw new DaoException("Error: Creating New Customer - FAILED (something went wrong)");
			} // catch
		}
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
				removeMethod(customer.getId());
				
			} // if - id
			else if (customerDetectInDB(customer) == DetectionBy.USERNAME) {
				customer = getCustomer(customer.getCustName());
				removeMethod(customer.getId());
			} // else if - name
			else {
				throw new DaoException("Error: Removing Customer - FAILED (Customer dosen't exist in the DataBase)");
			} // else
	}

	@Override
	public void updateCustomer(Customer customer) throws DaoException{
		
		// check if the customer exist
		if (custotmerExistByID(customer.getId()) == true) {
			updateMethod(customer);		
		}// if - exist
		else {
			throw new DaoException("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else	
	}

	@Override
	public Customer getCustomer(long custID) throws DaoException{
		
		// check if the customer exist
		if (custotmerExistByID(custID) == true) {
			
			Customer customer = null;
			String custName = null, password = null;
			
			try {
				String sqlSEL = "SELECT * FROM customer WHERE Cust_ID= ?" ;
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setLong(1, custID);
				
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
				custName = rs.getString("Cust_name");
				password = rs.getString("password");	
				
				}
				
				customer = new Customer(custID, custName, password);	
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Getting Customer By ID - FAILED");
			}
			return customer;
		}   
		else {
			throw new DaoException("Error: Getting Customer - FAILED (Customer is not exist in the DataBase)");
		} // else	

		
	}

	@Override
	public Customer getCustomer(String custName) throws DaoException {
		
		Customer customer = null;
		
		try {

		if(custotmerExistByName(custName) == true) {
			
			String password = null;
			long id = 0;

				String sqlSEL = "SELECT * FROM customer WHERE cust_name= ?" ;
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setString(1, custName);
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
				password = rs.getString("password");
				id = rs.getLong("cust_id");
				} // while
				
				customer = new Customer(id, custName, password);
				
				
		}
		else {
			throw new DaoException("Error: Getting Customer By ID - FAILED");
		} // else - exist
		} catch (SQLException | FiledErrorException e) {
			throw new DaoException("Error: Getting Customer By Name - FAILED (Customer is not exist in the DataBase)");
		} // catch

		return customer;
	}

	@Override
    public Collection<Customer> getAllCustomers() throws DaoException{
		
		String sql = "SELECT * FROM customer";
		Collection<Customer> customers = new HashSet<>();
		Customer c = null;
		ResultSet rs = null;
		
		try {
			
			Statement stat = DBconnectorV3.getConnection().createStatement();
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
	
	/**
	 * 
	* <p>This is a Private remove method.</p>
	 * That's is add-on. from here the function will delete the customer from the specific table.
	 * 
	 * @param custID {@code long} of the Customer. 
	 * 
	 * @author Raziel
	 */
	
    private void removeMethod(long custID) throws DaoException{
		
		boolean hasRow = false;
		PreparedStatement prep = null;
		ResultSet rs = null;
		// Check if the customer has coupons BEFORE Deleting the customer.
		String sqlCheckExist = "SELECT * FROM customer_coupon WHERE Cust_ID=" + custID;
		try {
   		prep = DBconnectorV3.getConnection().prepareStatement(sqlCheckExist);
   		rs = prep.executeQuery();
   		while(rs.next()) {
   			// if we have rows in customer_coupon, make hasRow = true.
   			hasRow = true;
   		}
   		
			prep.clearBatch();
			
   		if(hasRow == true) {

   			String sqlDeleteALL = "DELETE customer_coupon.*, customer.*"
						+ " FROM customer"
						+ " LEFT JOIN customer_coupon USING (cust_id)"
						+ " WHERE customer.cust_id=" + custID
						+ " AND customer.cust_id IS NOT NULL";
				prep = DBconnectorV3.getConnection().prepareStatement(sqlDeleteALL);
				prep.executeUpdate();
				prep.clearBatch();
				
   		} // if - hasRow		
			else {

				String sqlOnlyFromCompany = "DELETE FROM customer WHERE Cust_ID=" + custID;
				PreparedStatement prep2 = DBconnectorV3.getConnection().prepareStatement(sqlOnlyFromCompany);
				prep2.executeUpdate();
				prep2.clearBatch();
			} // else - hasRow

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error: Removing Company - FAILED (something went wrong..)");
		} // catch
		
	} // removeMethodPart2
	
	private void updateMethod(Customer customer) throws DaoException{
		
       try {

			String sqlUpdateCustomerTable = "UPDATE customer SET Cust_name=?, password=? WHERE Cust_ID=?";
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement (sqlUpdateCustomerTable);
			prep.setString(1, customer.getCustName());
			prep.setString(2, customer.getPassword());
			prep.setLong(3, customer.getId());
			
			prep.executeUpdate();
			//prep.close();
			
			String sqlUpdateCustomer_CouponTable = "UPDATE customer_coupon SET Cust_ID=? WHERE Cust_ID=?";
			PreparedStatement prep1 = DBconnectorV3.getConnection().prepareStatement(sqlUpdateCustomer_CouponTable);
			prep1.setLong(1, customer.getId());
			prep1.setLong(2, customer.getId());
			prep1.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
		
	}
	
	/* Here it's 3 pirvate methods. my add on to this class.
	*  the Two last methods 'exist' - can be in some public class.
	*  but all the work, like connection to database, and checks, and exception.. 
	*  all goes from here. from the DBDAO.
	*  so consequently I decieded to put this 3 methods here as private. in all the DBDAO (3 classes)
	*  and adjust this methods to the currently DBDAO class.  
	*/

	private Coupon addCouponMethod(Coupon coupon, long custID) throws DaoException {
					
			try{
				// check if we have the coupon in stock
				String sqlCheckAmount = "SELECT coupon.*"
						+ "FROM coupon "
						+ "WHERE coupon.Coup_id=" + coupon.getId() + " " 
						+ "AND coupon.Amount > 0";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlCheckAmount);
				ResultSet rs = prep.executeQuery();
				rs.next();
				int currentAmount = 0;
				if(rs.getRow() != 0) {
					currentAmount = rs.getInt("amount");
					currentAmount = currentAmount - 1;
				}
				else {
					throw new DaoException("Error: Purchase Coupon - FAILD (The coupon out of stock)");
				}
				// clearing the statement.
				prep.clearBatch();
				
				// Updating the amount
				String sqlUpdateAmount = "UPDATE `coupon`.`coupon` "
						+ "SET `amount`='" + currentAmount + "' "
						+ "WHERE `coup_id`='" + coupon.getId() +"'";
				prep = DBconnectorV3.getConnection().prepareStatement(sqlUpdateAmount);
				prep.executeUpdate();
				
				// Clearing the statement
				prep.clearBatch();

				// Adding the coupon to customer_coupon
				String sqlAddCoupon = "INSERT INTO customer_coupon (cust_id, coup_id)" 
				+ "VALUES(" + custID + "," + coupon.getId() + ")";	
				prep = DBconnectorV3.getConnection().prepareStatement(sqlAddCoupon);
				prep.executeUpdate();
				prep.clearBatch();
//	

			} // try
			catch (SQLException | NullPointerException e) {
				throw new DaoException("Error: Creating Coupon By Customer- FAILED (something went wrong..)");			
			} // catch
			return coupon;

	}

   private boolean custotmerExistByID(long custID) throws DaoException {
		
		Statement stat = null;
		ResultSet rs = null;
		boolean answer = false;

		try {
			String sqlName = "SELECT Cust_ID FROM customer WHERE "
					+ "Cust_ID= " + custID;
					
					stat = DBconnectorV3.getConnection().createStatement();
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
	
   private boolean custotmerExistByName(String custName) throws DaoException {

	   Statement stat = null;
		ResultSet rs = null;
		boolean answer = false;
		  
		   try {
				String sqlName = "SELECT Cust_name FROM customer WHERE "
						+ "cust_name= '" + custName + "'";
				stat = DBconnectorV3.getConnection().createStatement();
				rs = stat.executeQuery(sqlName);
				rs.next();
				
				if(rs.getRow() != 0) {
					answer = true;	
				}

				stat.clearBatch();
	            } catch (SQLException e) {
	    			throw new DaoException("Error: cannot make sure if the customer is in the DataBase");
	            } // catch
		   return answer;
	} // custotmerExistByName
	
   private DetectionBy customerDetectInDB(Customer customer) throws DaoException{
	   boolean exist = false;
	   if(customer.getId() > 0) {
			exist = custotmerExistByID(customer.getId());

			if(exist == true) {
				return DetectionBy.ID;
			} // if - inner
			else {
				throw new DaoException("Error: Detection By ID - FAILED (The customer dosen't exist in the DataBase)");
			} // else - inner
		} // if 
		else if(customer.getCustName() != null) {
			if(custotmerExistByName(customer.getCustName()) == true) {
				return DetectionBy.USERNAME;
			} // if - inner
			else {
				throw new DaoException("Error: Detection By Name - FAILED (The customer dosen't exist in the DataBase)");
			} // else - inner
		} // else if
		return DetectionBy.NONE;
	} // customerDetectInDB
} // CustomerDBDAO
