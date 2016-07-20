package com.dao.interfaces;

import java.util.Collection;
import java.util.Set;

import com.javabeans.*;

import ExeptionErrors.DaoExeption;
import ExeptionErrors.FiledErrorException;
import ExeptionErrors.LoginException;

public interface CustomerDAO {
	public void createCustomer(Customer newCustomer) throws DaoExeption;
	public void removeCustomer(Customer customer) throws DaoExeption, FiledErrorException;
	public void updateCustomer(Customer updateCustomer) throws DaoExeption;
	public Customer getCustomer(long id) throws DaoExeption;
	public Collection<Customer> getAllCustomers() throws DaoExeption;
	public Set<Coupon> getCoupons(long custId) throws DaoExeption;
	public boolean login(String custName, String password) throws DaoExeption;
	public Customer getCustomer(String custName) throws DaoExeption, FiledErrorException;
	
	//TODO: change this new two function.. it's looking bad and too much general.
	public Set<Coupon> getCouponByPrice(String string, String string2, long custID, double maxPrice) throws DaoExeption;
	public Set<Coupon> getCouponByType(String string, String string2, long custID, CouponType category) throws DaoExeption;
	
	
}
