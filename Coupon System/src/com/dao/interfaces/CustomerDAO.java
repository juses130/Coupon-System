package com.dao.interfaces;

import java.util.Collection;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.javabeans.*;

/**
 * <p>Customer DAO - Interface</p>
 * @author Raziel
 */

public interface CustomerDAO {
	
	/**
	 * Checks if the password and customer name given, matches the credentials stored in the underlying database (or any other persistence storage).
	 * @param custName a {@code String} Customer name
	 * @param password a {@code String} password
	 * @return {@code true} if the credentials match, otherwise {@code false}
	 * @throws DaoException 
	 */
	public boolean login(String custName, String password) throws DaoException;
	
	/**
	 * Adds an Customer Object in the underlying database (or any other persistence storage).
	 * @param customer a {@code Customer} object
	 * @throws DaoException
	 */
	public void createCustomer(Customer customer) throws DaoException;
	
	/**
	 * Remove a Customer from the underlying database (or any other persistence storage).
	 * @param customer a {@code Customer} Object
	 * @throws DaoException
	 */
	public void removeCustomer(Customer customer) throws DaoException, FiledErrorException;
	
	/**
	 * Updates a Customer in the underlying database (or any other persistence storage) 
	 * from a given {@code Customer} object. The fields affected are Name, Email, Password and Salt.
	 * @param customer a {@code Customer} object
	 * @throws DaoException
	 */
	public void updateCustomer(Customer updateCustomer) throws DaoException;
	
	/**
	 * Adds a Coupon {@code Coupon} to the Customer_Coupons {@code SqlTable} Database.
	 * Pulling the Coupon from the Table Coupon and add it into the Customer Database.
	 * 
	 * @param coupon a {@code Coupon} Object
	 * @param custID a {@code long} Customer ID
	 * @return a {@code Coupon} Object
	 * @throws DaoException
	 */
	public Coupon addCoupon(Coupon coupon, long custID) throws DaoException;
	
	/**
	 * Returns a {@code Customer} object from the underlying database 
	 * (or any other persistence storage).
	 * 
	 * @param custID a {@code long} Customer ID
	 * @return a {@code Customer} object
	 * @throws DaoException
	 */
	public Customer getCustomer(long custID) throws DaoException;
	
	/**
	 * Returns a {@code Customer} object from the underlying database 
	 * (or any other persistence storage).
	 * 
	 * @param custName a {@code String} Customer name
	 * @return a {@code Customer} object
	 * @throws DaoException
	 */
	public Customer getCustomer(String custName) throws DaoException;

	/**
	 * Returns a {@code Collection<Customer>} of all Customers from the 
	 * underlying database (or any other persistence storage).
	 * 
	 * @return a {@code Collection<Customer>}
	 * @throws DaoException
	 */
	public Collection<Customer> getAllCustomers() throws DaoException;

	/**
	 * Returns all the coupons {@code Coupon} of the customer {@code Customer} using the customer ID {@code long}.
	 * @param custID {@code long}
	 * @return a {@code Collection<Coupon>}
	 * @throws DaoException
	 */
	public Collection<Coupon> getCoupons(long custID) throws DaoException;
	
}
