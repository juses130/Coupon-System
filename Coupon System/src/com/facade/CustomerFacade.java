package com.facade;

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
		System.out.println(coupon.toString());
		return coupon;
		
	} // purchaseCoupon
	
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
