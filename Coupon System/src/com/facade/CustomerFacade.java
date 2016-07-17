package com.facade;

import java.util.Set;

import com.dao.interfaces.*;
import com.javabeans.*;
import com.task.and.singleton.CouponSystem;

public class CustomerFacade {

//	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	public CustomerFacade(){
		
//		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	public Coupon purchaseCoupon(Coupon coupon) {
		
		short CustomerCreator = 2;
		coupDao.setCreator(CustomerCreator);
		long id = coupDao.createCoupon(coupon);
		coupon = coupDao.getCoupon(id);
		
		return coupon;
		
	} // purchaseCoupon
	
	public Set<Coupon> getAllPurchasedCoupons(long custID) {
		
		Set<Coupon> coupons = custDao.getCoupons(custID);
		return coupons;
		
	} // getAllCoupons
	
	public Set<Coupon> getAllCouponsByPrice(long custID ,double maxPrice) {
		Set<Coupon> coupons = custDao.getCouponByPrice("customer_coupon", "Cust_id", custID, maxPrice);
		
		return coupons;	
	}
	
	public Set<Coupon> getAllCouponsByType(long custID, CouponType category) {
		
		Set<Coupon> coupons = custDao.getCouponByType("customer_coupon", "cust_id" ,custID, category);
		return coupons;
	}
	
    public boolean login(String userName, String password) {
		
    	
		boolean exsistOrNot = custDao.login(userName, password);
		
		if(exsistOrNot != true) {
			return false;
		}
		else {
			return true;
		} // else
	} // login - function
	
}
