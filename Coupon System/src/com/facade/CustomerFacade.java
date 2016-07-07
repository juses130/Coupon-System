package com.facade;

import com.dbdao.CouponDBDAO;
import com.dbdao.CustomerDBDAO;
import com.javabeans.*;

public class CustomerFacade {

	public CustomerFacade(){}
	
	public void purchaseCoupon(Coupon coupon) {
		//TODO: working on..
		CouponDBDAO couDB = new CouponDBDAO();
		short CustomerCreator = 2;
		couDB.setCreator(CustomerCreator);
		couDB.createCoupon(coupon);
		
	} // purchaseCoupon
	
    public boolean login(String compName, String password) {
		
    	CustomerDBDAO cus = new CustomerDBDAO();
		boolean exsistOrNot = cus.login(compName, password);
		
		if(exsistOrNot != true) {
			return false;
		}
		else {
			return true;
		} // else
	} // login - function
	
}
