package com.dbdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.added.functions.DBconnectorV3;
import com.added.functions.SharingData;
import com.dao.interfaces.CustomerDAO;
import com.javabeans.Coupon;
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
	public void createCustomer(Customer customer) {
		
		// creating ResultSet
				ResultSet rs = null;
				//long id = -1;
				try {
					
					String sqlQuery = "INSERT INTO customer (CUST_NAME, PASSWORD) VALUES(?,?)";
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
					//short cou = 1;
					
					// now we will put the in their places.
					prep.setString(1, customer.getCustName());
					prep.setString(2, customer.getPassword());
				
					// after we "loaded" the columns, we will executeUpdate prep.
					prep.executeUpdate();

					// This 2 lines will make it run to the next null row. and line 3 will set the ID (next new row).
					rs = prep.getGeneratedKeys();
					rs.next();
					customer.setId(rs.getLong(1));
					
					
					// Letting the others (if the asking) that the Company Added Succsefully.
					SharingData.setFlag1(true);
					String tostring = customer.toString();
					SharingData.setVarchar4(tostring);

					
				} // try 
				catch (SQLException e) {
					SharingData.setFlag1(false);
					//e.printStackTrace(); // TODO: by the project guide, WE DON'T NEED TO PRINT the StackTrace.
					SharingData.setExeptionMessage(e.getMessage());
				} // catch

				
			} // createCompany - Function

	@Override
	public void removeCustomer(Customer customer) {
		
		removeMethod(customer.getId(), "customer");
		removeMethod(customer.getId(), "customer_coupon");
	}

	@Override
	public void updateCustomer(Customer customer) {
			updateMethod(customer);
	}

	@Override
	public Customer getCustomer(long id) {
		

		Customer c = null;
		String custName, password;
		
		try {

			
			String sqlSEL = "SELECT * FROM customer WHERE Cust_ID= ?" ;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			
			ResultSet rs = prep.executeQuery();
			rs.next();
			custName = rs.getString("Cust_name");
			//email = rs.getString("Email");
			password = rs.getString("password");
			

			c = new Customer(id, custName, password);
			String customerInfo = c.toString();
			SharingData.setVarchar2(customerInfo);

			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		return c;
	}

	@Override
    public Collection<Customer> getAllCustomers() {
		
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

		} catch (SQLException e) {
			e.printStackTrace();
		} // catch
		
		
		return customers;
	} // getAllCompanies

	@Override
	public Set<Coupon> getCoupons(long custId) {

		Set<Coupon> coupons = new HashSet<>();
		CouponDBDAO coupDB = new CouponDBDAO();
		
		try {
			
			String sql = "SELECT COUP_ID FROM Customer_Coupon WHERE CUST_ID=?";
			PreparedStatement stat = DBconnectorV3.getConnection().prepareStatement(sql);
			stat.setLong(1, custId);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				coupons.add(coupDB.getCoupon(rs.getLong("COUP_ID")));
			}
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		 return coupons;
	}

	public Set<Coupon> getAllCouponsByPrice(long custID ,double maxPrice) {
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByPriceV2("customer_coupon" , "cust_id", custID, maxPrice);
		
		return coupons;
	}
	
	@Override
	public boolean login(long custID, String password) {
		
		ResultSet rs1 = null;
		Statement stat1 = null;


		boolean hasRows = false;
        try {
			
			
			String sqlName = "SELECT Cust_ID, password FROM customer WHERE "
					+ "Cust_ID= '" + custID + "'" + " AND " + "password= '" 
					+ password + "'";
			stat1 = DBconnectorV3.getConnection().createStatement();
		    rs1 = stat1.executeQuery(sqlName);
		    rs1.next();

			if (rs1.getRow() != 0) {
				hasRows = true;
				// sharing the id between CustomerDbDAO and CouponDbDAO Classes.
			SharingData.setIdsShare(custID);
			}

            } catch (SQLException e) {
	        e.printStackTrace();
	        
            } // catch

	return hasRows;
	}


	/**
	 * 
	* This is a remove method - making the deleting of company more flexible and easy.
	 * It's my add on.
	 * 
	 * @param long id
	 * @param String table
	 * 
	 * @author Raziel
	 */
	
	private void removeMethod(long id, String table) {
		
		try {
			
			//String compName, email, password;
			
			String sqlDELid = "DELETE FROM " + table + " WHERE Cust_ID =" + id;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid);
			prep.executeUpdate();
			
		}
		catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		
	} // removeMethod
	
	private void updateMethod(Customer customer) {
		
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
			//prep1.close();
			
		    // Letting the others (if the asking) that the Company update Succsefully.
		    SharingData.setFlag1(true);
		    String tostring = customer.toString();
			SharingData.setVarchar4(tostring);

			} catch (SQLException e) {
				SharingData.setExeptionMessage(e.getMessage());
			}

		
	}
	

}
