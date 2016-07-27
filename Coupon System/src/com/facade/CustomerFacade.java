package com.facade;

import java.util.HashSet;
import java.util.Set;

import com.dao.interfaces.*;
import com.exeptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CustomerFacade implements CouponClientFacade {

	private Customer customer;
//	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	
    public CustomerFacade login(String custName, String password, ClientType client) throws LoginException, DaoExeption {
    	
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
	
	public CustomerFacade() throws ConnectorExeption{
		
//		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	public Coupon purchaseCoupon(Coupon coupon) throws DaoExeption {

		coupon = coupDao.getCoupon(coupon.getId(), ClientType.CUSTOMER);
		custDao.addCoupon(coupon, customer.getId());
		
		return coupon;
		
	} // purchaseCoupon
	
	public Customer getCustomer() throws DaoExeption {
		Customer customer = new Customer();
		customer = custDao.getCustomer(this.customer.getId());
		return customer;
	}
	
	public Set<Coupon> getAllPurchasedCoupons() throws DaoExeption {
		Set<Coupon> coupons = coupDao.getCoupons(customer.getId(), ClientType.CUSTOMER);
		return coupons;
		
	} // getAllCoupons
	
	public Set<Coupon> getAllCouponsByPrice(long custID ,double maxPrice) throws DaoExeption {
		Set<Coupon> coupons = new HashSet<>();
		
		return coupons;	
	}
	

//	public Set<Coupon> getAllCouponsByType(long custID, CouponType category) {
//		
//		Set<Coupon> coupons = custDao.getCouponByType("customer_coupon", "cust_id" ,custID, category);
//		return coupons;
//	}
	

	
}
