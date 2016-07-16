package com.dao.interfaces;

import java.util.Collection;
import java.util.Set;

import com.javabeans.*;

public interface CustomerDAO {
	public void createCustomer(Customer newCustomer);
	public void removeCustomer(Customer remCustomer);
	public void updateCustomer(Customer updateCustomer);
	public Customer getCustomer(long id);
	public Collection<Customer> getAllCustomers();
	public Set<Coupon> getCoupons(long custId);
	public boolean login(String custName, String password);
	
}
