package com.facade;

import java.util.Set;

import com.added.functions.SharingData;
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
	
	public Set<Coupon> getAllCouponsByPrice(long custID ,double maxPrice) {
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByPriceV2("customer_coupon", "Cust_id", custID, maxPrice);
		
		return coupons;	
	}
	
	public Set<Coupon> getAllCouponsByType(long custID, CouponType category) {
		
		CouponDBDAO coupDB = new CouponDBDAO();
		
		Set<Coupon> coupons = coupDB.getCouponByTypeV2("customer_coupon", "cust_id" ,custID, category);
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
