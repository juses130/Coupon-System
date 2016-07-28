package com.dao.interfaces;

import java.util.Collection;
import java.util.Set;

import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.exceptionerrors.LoginException;
import com.javabeans.*;

/**
 * <p>Customer DAO - Interface</p>
 * @author Raziel
 */

public interface CustomerDAO {
	
	/**
	 * <p> createCustomer - Function</p>
	 * 
	 * This function is allows us to create New Customer to the DataBase.
	 * 
	 * @param Customer {@code Customer} object
	 * @return a positive {@code Customer} object value if the it was run successfully.
	 * @throws DaoException
	 * @author Raziel
	 */
	
	public void createCustomer(Customer Customer) throws DaoException;
	public void removeCustomer(Customer customer) throws DaoException, FiledErrorException;
	public void updateCustomer(Customer updateCustomer) throws DaoException;
	public Coupon addCoupon(Coupon coupon, long custID) throws DaoException;
	public Customer getCustomer(long id) throws DaoException;
	public Collection<Customer> getAllCustomers() throws DaoException;
	public boolean login(String custName, String password) throws DaoException;
	public Customer getCustomer(String custName) throws DaoException;

	
}
