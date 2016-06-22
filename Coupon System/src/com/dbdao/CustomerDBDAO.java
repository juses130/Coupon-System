package com.dbdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import com.added.functions.DBconnector;
import com.added.functions.SharingData;
import com.dao.interfaces.CustomerDAO;
import com.javabeans.Coupon;
import com.javabeans.Customer;

public class CustomerDBDAO implements CustomerDAO {

	@Override
	public void createCustomer(Customer newCustomer) {
		
		// creating ResultSet
				ResultSet rs = null;
				long id = 1;
				try {
					DBconnector.getCon();
					String sqlQuery = "INSERT INTO customer (CUST_NAME, PASSWORD) VALUES(?,?)";
					PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
					//short cou = 1;
					
					// now we will put the in their places.
					prep.setString(1, newCustomer.getCustName());
					prep.setString(2, newCustomer.getPassword());
				
					// after we "loaded" the columns, we will executeUpdate prep.
					prep.executeUpdate();

					// This 2 lines will make it run to the next null row. and line 3 will set the ID (next new row).
					rs = prep.getGeneratedKeys();
					rs.next();
					id = rs.getLong(1);
					newCustomer.setId(id);
					
					// Letting the others (if the asking) that the Company Added Succsefully.
					SharingData.setFlag1(true);
					String tostring = newCustomer.toString();
					SharingData.setVarchar4(tostring);

					
				} // try 
				catch (SQLException e) {
					SharingData.setFlag1(false);
					e.printStackTrace(); // TODO: by the project guide, WE DON'T NEED TO PRINT the StackTrace.
				} // catch
				finally {
					try {
						DBconnector.getInstatce().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} // finally
				
			} // createCompany - Function
	

	public CustomerDBDAO() {
	// TODO Auto-generated constructor stub
}
	
	public void removeCustomer(long id) throws SQLException{
		// test
		
		try {
			DBconnector.getCon();
			//String compName, email, password;
			
			String sqlDELid = "DELETE FROM customer WHERE Cust_id =?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlDELid);
			prep.setLong(1, id);
			prep.executeUpdate();
			
		}
		catch (SQLException e) {
			e.getMessage();
		}
		finally {
			DBconnector.getInstatce().close();
		} // finally
		
	} // removeCompany - By ID - Functi

	public void removeCustomer(String name) throws SQLException {
		
		try {
			DBconnector.getCon();
			String sqlDELname = "DELETE FROM Customer WHERE cust_name =?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlDELname);
			prep.setString(1, name);
			
			prep.executeUpdate();
			
			// Letting the other Classes (if they asking) that the Company Removed Succsefully.
			SharingData.setFlag1(true);
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		finally {
			DBconnector.getInstatce().close();
		} // finally
		
	} // removeCompany - BY Name - Function
	
	@Override
	public void updateCustomer(Customer updateCustomer) {
       try {
			
			DBconnector.getCon();
			
			String sqlCmdStr = "UPDATE customer SET Cust_name=?, password=? WHERE Cust_ID=?";
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement (sqlCmdStr);
			prep.setString(1, updateCustomer.getCustName());
			prep.setString(2, updateCustomer.getPassword());
			prep.setLong(3, updateCustomer.getId());
			
			prep.executeUpdate();
			
		    // Letting the others (if the asking) that the Company update Succsefully.
		    SharingData.setFlag1(true);
		    String tostring = updateCustomer.toString();
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
	public Customer getCustomer() {
		
		return null;
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		
		return null;
	}

	@Override
	public Collection<Coupon> getCoupons() {
		
		return null;
	}

	@Override
	public boolean login(Customer custName, Customer password) {
		
		return false;
	}


	@Override
	public void removeCustomer(Customer remCustomer) {
		
		
	}

}
