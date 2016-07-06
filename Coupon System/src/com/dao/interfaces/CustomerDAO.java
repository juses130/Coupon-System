package com.dao.interfaces;

import java.util.Collection;
import com.javabeans.*;

public interface CustomerDAO {
	public void createCustomer(Customer newCustomer);
	public void removeCustomer(Customer remCustomer);
	public void updateCustomer(Customer updateCustomer);
	public Customer getCustomer(long id);
	public Collection<Customer> getAllCustomers();
	public Collection<Coupon> getCoupons();
	public boolean login(String custName, String password);
	
}
