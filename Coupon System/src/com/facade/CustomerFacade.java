package com.facade;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dbdao.CouponDBDAO;
import com.dbdao.CustomerDBDAO;
import com.javabeans.*;

public class CustomerFacade {

	public CustomerFacade(){}
	
	public Coupon purchaseCoupon(Coupon coupon) {
		//TODO: working on..
		CouponDBDAO couDB = new CouponDBDAO();
		short CustomerCreator = 2;
		couDB.setCreator(CustomerCreator);
		long id = couDB.createCoupon(coupon);
		coupon = couDB.getCoupon(id);
		
		return coupon;
		
	} // purchaseCoupon
	
	public Set<Coupon> getAllPurchasedCoupons(long custID) {
		
		CustomerDBDAO cusDB = new CustomerDBDAO();
		Set<Coupon> coupons = cusDB.getCoupons(custID);
		return coupons;
		
	} // getAllCoupons
	
	public Set<Coupon> getAllCouponsByType(CouponType category) {
		
		CouponDBDAO coupDB = new CouponDBDAO();
		
		Set<Coupon> coupons = coupDB.getCouponByType(category);
		return coupons;
	}
	
	public Set<Coupon> getAllCouponsByPrice(double minPrice, double maxPrice) {
		
		CouponDBDAO coupDB = new CouponDBDAO();
		
		Set<Coupon> coupons = coupDB.getCouponByPriceV3("customer_coupon", minPrice, maxPrice);
		return coupons;
	}
	
    public boolean login(long custID, String password) {
		
    	CustomerDBDAO cus = new CustomerDBDAO();
		boolean exsistOrNot = cus.login(custID, password);
		
		if(exsistOrNot != true) {
			return false;
		}
		else {
			return true;
		} // else
	} // login - function
	
}
