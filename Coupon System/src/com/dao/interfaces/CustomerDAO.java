package com.dao.interfaces;

import java.util.Collection;
import java.util.Set;

import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.exeptionerrors.LoginException;
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
	 * @throws DaoExeption
	 * @author Raziel
	 */
	
	public void createCustomer(Customer Customer) throws DaoExeption;
	public void removeCustomer(Customer customer) throws DaoExeption, FiledErrorException;
	public void updateCustomer(Customer updateCustomer) throws DaoExeption;
	public Coupon addCoupon(Coupon coupon, long custID) throws DaoExeption;
	public Customer getCustomer(long id) throws DaoExeption;
	public Collection<Customer> getAllCustomers() throws DaoExeption;
	public boolean login(String custName, String password) throws DaoExeption;
	public Customer getCustomer(String custName) throws DaoExeption;

	
}
