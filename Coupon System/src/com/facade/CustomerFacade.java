package com.facade;

import java.util.Set;

import com.dao.interfaces.*;
import com.exeptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CustomerFacade implements CouponClientFacade {

//	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	private Customer customer;
	
    public CustomerFacade login(String custName, String password, ClientType client) throws LoginException, DaoExeption {
    	
    	boolean loginSuccessful  = false;
    	try {
    		loginSuccessful  = custDao.login(custName, password);
		} catch (Exception e) {
			throw new LoginException("Customer Login Failed");
		}
    	
    	if(loginSuccessful == true) {
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
		Coupon coup = new Coupon();
		//TODO: add Some query that will get coupon from the couponTable and set it into customer_coupon
		coup = coupDao.getCoupon(coupon.getId(), ClientType.CUSTOMER);
		return coup;
		
	} // purchaseCoupon
	
	public Set<Coupon> getAllPurchasedCoupons(long custID) throws DaoExeption {
		
		Set<Coupon> coupons = custDao.getCoupons(custID);
		return coupons;
		
	} // getAllCoupons
	
	public Set<Coupon> getAllCouponsByPrice(long custID ,double maxPrice) throws DaoExeption {
		Set<Coupon> coupons = custDao.getCouponByPrice("customer_coupon", "Cust_id", custID, maxPrice);
		
		return coupons;	
	}
	
//	public Set<Coupon> getAllCouponsByType(long custID, CouponType category) {
//		
//		Set<Coupon> coupons = custDao.getCouponByType("customer_coupon", "cust_id" ,custID, category);
//		return coupons;
//	}
	

	
}
