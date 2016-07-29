package com.facade;

import java.util.HashSet;
import java.util.Set;

import com.dao.interfaces.*;
import com.exceptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CustomerFacade implements CouponClientFacade {

	private Customer customer;
//	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	
    public CustomerFacade login(String custName, String password, ClientType client) throws LoginException, DaoException {
    	
    	boolean loginSuccessful  = false;
    	try {
    		loginSuccessful  = custDao.login(custName, password);
		} catch (Exception e) {
			throw new LoginException("Customer Login Failed");
		}
    	
    	if(loginSuccessful == true) {
    		this.customer = custDao.getCustomer(custName);
    		return this;
    	}
    	else {
			throw new LoginException("Customer Login Failed.");
		}
	} // login - function
	
	public CustomerFacade() throws ConnectorException{
		
//		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	public Coupon purchaseCoupon(Coupon coupon) throws DaoException {

		coupon = coupDao.getCoupon(coupon.getId(), ClientType.CUSTOMER);
		custDao.addCoupon(coupon, customer.getId());
		
		return coupon;
		
	} // purchaseCoupon
	
	public Customer getCustomer() throws DaoException {
		Customer customer = new Customer();
		customer = custDao.getCustomer(this.customer.getId());
		return customer;
	}
	
    public Customer getCustomerAndCoupons() throws DaoException {
    	
    	Set<Coupon> coupons = getAllPurchasedCoupons();
    	Customer customer = this.customer;
    	customer.setCoupons(coupons);
    	
    	return customer;
	} // getCustomerAndCoupons
	
	public Set<Coupon> getAllPurchasedCoupons() throws DaoException {
		Set<Coupon> coupons = coupDao.getCoupons(customer.getId(), ClientType.CUSTOMER);
		return coupons;
		
	} // getAllCoupons
	
	public Set<Coupon> getAllCouponsByPrice(double maxPrice) throws DaoException {
		Set<Coupon> coupons = new HashSet<>();
		coupons = coupDao.getCouponByPrice(customer.getId(), maxPrice, ClientType.CUSTOMER);
		return coupons;	
	}
	public Set<Coupon> getAllCouponsByType(String category) throws DaoException, FiledErrorException {
		Set<Coupon> coupons = new HashSet<>();
		
		Coupon coupon = new Coupon();
		coupon.setCategory(category);
		
		coupons = coupDao.getCouponByType(customer.getId(), coupon.getCategory(), ClientType.CUSTOMER);
		return coupons;
	}
	

	
}
