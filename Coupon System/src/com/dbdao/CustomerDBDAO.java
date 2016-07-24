package com.dbdao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.added.functions.DBconnectorV3;
import com.dao.interfaces.CustomerDAO;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.DetectionBy;
import com.javabeans.Coupon;
import com.javabeans.CouponType;
import com.javabeans.Customer;

/**
 * This is Customer Database DAO Class.
 * Just impelemnts the methods from CustomerDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CustomerDBDAO implements CustomerDAO {
	
	public CustomerDBDAO() {}
	
	@Override
	public boolean login(String userName, String password) throws DaoExeption {
		
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
			throw new DaoExeption("Error: Customer Login - FAILED");
		} // catch
	return hasRows;
	}
	
	@Override
	public void createCustomer(Customer customer) throws DaoExeption{
		
		// check if the cost not exist && if the instances are not empty
		if (existOrNotByID(customer) == false) {
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
			} // try 
			catch (SQLException | NullPointerException e) {
				throw new DaoExeption("Error: Creating New Customer - FAILED (something went wrong)");
			} // catch
		}
		else {
			throw new DaoExeption("Error: Creating Customer - FAILED (Customer is already exist in the DataBase)");
		} // else
			} // createCompany - Function

	@Override
	public Coupon addCoupon(Coupon coupon, Customer customer) throws DaoExeption {
		coupon = addCouponMethod(coupon, customer);
		return coupon;
	}
	
	
	@Override
	public void removeCustomer(Customer customer) throws DaoExeption, FiledErrorException {
		
		// check if the customer exist
		if (existOrNotByID(customer) == true) {
			
			DetectionBy detect = customerDetectInDB(customer);
			if(detect == DetectionBy.ID) {
				removeMethod(customer.getId());
				
			}
			else if (customerDetectInDB(customer) == DetectionBy.USERNAME) {
				Customer customerCopy = new Customer();
				customerCopy = getCustomer(customer.getCustName());
				removeMethod(customer.getId());
			}
			else {
				throw new DaoExeption("Error: Removing Customer - FAILED (Customer dosen't exist in the DataBase)");
			}
		} // if - exist
		else {
			throw new DaoExeption("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else - exist
	}

	@Override
	public void updateCustomer(Customer customer) throws DaoExeption{
		
		// check if the customer exist
		if (existOrNotByID(customer) == true) {
			updateMethod(customer);		
		}// if - exist
		else {
			throw new DaoExeption("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else	
	}

	@Override
	public Customer getCustomer(long id) throws DaoExeption{
		
		Customer customer = new Customer();
		customer.setId(id);
		// check if the customer exist
		if (existOrNotByID(customer) == true) {

			String custName = null, password = null;
			
			try {
				String sqlSEL = "SELECT * FROM customer WHERE Cust_ID= ?" ;
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setLong(1, id);
				
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
				custName = rs.getString("Cust_name");
				password = rs.getString("password");	
				
				}
				customer = new Customer(id, custName, password);	
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Getting Customer By ID - FAILED");
			}
			return customer;
		}   
		else {
			throw new DaoExeption("Error: Getting Customer - FAILED (Customer is not exist in the DataBase)");
		} // else	

		
	}

	
	@Override
	public Customer getCustomer(String custName) throws DaoExeption, FiledErrorException {
		
		Customer customer = new Customer();
		
		customer.setCustName(custName);
		
		if(existOrNotByName(customer) == true) {
			try {
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
				
				} catch (SQLException | FiledErrorException e) {
					throw new DaoExeption("Error: Getting Customer By Name - FAILED (Customer is not exist in the DataBase)");
				} // catch
		}
		else {
			throw new DaoExeption("Error: Getting Customer By ID - FAILED");
		} // else - exist

		return customer;
	}
	
	@Override
    public Collection<Customer> getAllCustomers() throws DaoExeption{
		
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

	@Override
	public Set<Coupon> getCoupons(long custId) throws DaoExeption{

		Set<Coupon> coupons = new HashSet<>();
		
		try {
			
			String sql = "SELECT COUP_ID FROM Customer_Coupon WHERE CUST_ID=?";
			PreparedStatement stat = DBconnectorV3.getConnection().prepareStatement(sql);
			stat.setLong(1, custId);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				String title, message, image;
				Date stDate, enDate ;	
				int amount;
				CouponType type = null;
				double price;
				long id;
				
				title = rs.getString("Title");
				stDate = rs.getDate("start_date");
				enDate = rs.getDate("end_date");
				amount = rs.getInt("amount");
				type = CouponType.valueOf(rs.getString("Category").toUpperCase());
				message = rs.getString("Message");
				price = rs.getDouble("Price");
				image = rs.getString("image");
				id = rs.getLong("coup_id");

//				Coupon coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, type,  message, price, image);
				Coupon coupon = new Coupon();
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			throw new DaoExeption("Error: Getting Coupons Of Customer - FAILED (somthing went wrong..)");
		}
		 return coupons;
	}

	public Set<Coupon> getAllCouponsByPrice(long custID ,double maxPrice) throws DaoExeption{
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByPrice("customer_coupon" , "cust_id", custID, maxPrice);
		
		return coupons;
	}
	
	/**
	 * 
	* <p>This is a Private remove method.</p>
	 * It is an add on. from here the function will delete the customer from the tabels.
	 * 
	 * @param long id
	 * @param String table
	 * 
	 * @author Raziel
	 */
	
	private void removeMethodPart1(long id) throws DaoExeption{
		
		try {			
			String sqlDELid = "DELETE FROM customer WHERE Cust_ID=" + id;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid);
			prep.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DaoExeption("Error: Removing Customer - FAILED");
		}
		
	} // removeMethodPart1
	
	/**
	 * 
	* <p>This is a Private remove method.</p>
	 * It is an add on. from here the function will delete the customer from the specific table.
	 * 
	 * @param long id
	 * @param String table
	 * 
	 * @author Raziel
	 */
	
   private void removeMethod(long id) throws DaoExeption{
		
		boolean hasRow = false;
		PreparedStatement prep = null;
		ResultSet rs = null;
		// Check if the customer has coupons BEFORE Deleting the customer.
		String sqlCheckExist = "SELECT * FROM customer_coupon WHERE Cust_ID=" + id;
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
						+ " WHERE customer.cust_id=" + id
						+ " AND customer.cust_id IS NOT NULL";
				prep = DBconnectorV3.getConnection().prepareStatement(sqlDeleteALL);
				prep.executeUpdate();
				prep.clearBatch();
				
   		} // if - hasRow		
			else {

				String sqlOnlyFromCompany = "DELETE FROM customer WHERE Cust_ID=" + id;
				PreparedStatement prep2 = DBconnectorV3.getConnection().prepareStatement(sqlOnlyFromCompany);
				prep2.executeUpdate();
				prep2.clearBatch();
			} // else - hasRow

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoExeption("Error: Removing Company - FAILED (something went wrong..)");
		} // catch
		
	} // removeMethodPart2
	
	private void updateMethod(Customer customer) throws DaoExeption{
		
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
	

	private Coupon addCouponMethod(Coupon coupon, Customer customer) throws DaoExeption {
//		if(existInCustomer?(coupon, company, CheckCouponBy.BY_NAME) == false ) {
		
			try{
				/* creating the coupon in Coupon Table First. 
				 * and if we don't get Exception it will move on to 
				 * create the coupon in the Company_Coupon Table.
				*/
				String sqlAddCoupon = "INSERT INTO customer_coupon (cust_id, coup_id)" 
				+ "VALUES(" + customer.getId() + "," + coupon.getId() + ")";	
				
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlAddCoupon);
				prep.executeUpdate();
				prep.clearBatch();
				

			} // try
			catch (SQLException | NullPointerException e) {
				e.printStackTrace();
				throw new DaoExeption("Error: Creating Coupon By Customer- FAILED (something went wrong..)");			
			} // catch
			return coupon;
		} // if - it's bought before
//		else {
//			throw new DaoExeption("Error: Creating Coupon By Company - FAILED (You can create only ONE coupon with the same name!)");
//		} // else - it's bought before


	
   private boolean existOrNotByID(Customer customer) throws DaoExeption {
		
		Statement stat = null;
		ResultSet rs = null;
		boolean answer = false;

		try {
			String sqlName = "SELECT Cust_ID FROM customer WHERE "
					+ "Cust_ID= " + customer.getId();
					
					stat = DBconnectorV3.getConnection().createStatement();
					rs = stat.executeQuery(sqlName);
					rs.next();
							   
					if (rs.getRow() != 0) {
						answer = true;
					} // if
		} catch (SQLException e) {
			throw new DaoExeption("Error: cannot make sure if the customer is in the DataBase");
		}
		return answer;
	}
	
   private boolean existOrNotByName(Customer customer) throws DaoExeption {

	   Statement stat = null;
		ResultSet rs = null;
		boolean answer = false;
		  
		   try {
				String sqlName = "SELECT Cust_name FROM customer WHERE "
						+ "cust_name= '" + customer.getCustName() + "'";
				stat = DBconnectorV3.getConnection().createStatement();
				rs = stat.executeQuery(sqlName);
				rs.next();
				if(rs.getRow() != 0) {
					answer = true;	
				}

//				if (rs.getRow() != 0) {
//				} // if
	            } catch (SQLException e) {
	    			throw new DaoExeption("Error: cannot make sure if the customer is in the DataBase");
	            } // catch
		   
		   return answer;
	}
	
   private DetectionBy customerDetectInDB(Customer customer) throws DaoExeption{
	  
	   boolean exist = false;
		
	   if(customer.getId() > 0) {
			
			exist = existOrNotByID(customer);

			if(exist == true) {
				return DetectionBy.ID;
			} // if - inner
			else {
				throw new DaoExeption("Error: Detection By ID - FAILED (The customer dosen't exist in the DataBase)");
			} // else - inner
		} // if 
		else if(customer.getCustName() != null) {
			if(existOrNotByName(customer) == true) {
				return DetectionBy.USERNAME;
			} // if - inner
			else {
				throw new DaoExeption("Error: Detection By Name - FAILED (The customer dosen't exist in the DataBase)");
			} // else - inner
		} // else if
		
		return DetectionBy.NONE;
	}

	
	@Override
	public Set<Coupon> getCouponByPrice(String table, String colmun, long custID, double maxPrice) throws DaoExeption{
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByPrice(table, colmun, custID, maxPrice);
		
		return coupons;
	}

	@Override
	public Set<Coupon> getCouponByType(String table, String colmun, long custID, CouponType category) throws DaoExeption{
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByType(table, colmun, custID, category);
		
		return coupons;
	}



}
