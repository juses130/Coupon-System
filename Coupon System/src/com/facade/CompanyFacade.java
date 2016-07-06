package com.facade;

import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.javabeans.*;

public class CompanyFacade {

	public CompanyFacade() {}
	
	public boolean login(String compName, String password) {
		
		CompanyDBDAO db = new CompanyDBDAO();
		boolean exsistOrNot = db.login(compName, password);
		
		if(exsistOrNot != true) {
			return false;
		}
		else {
			return true;
		} // else
	} // login - function
	
	public void createCouponF(Coupon coupon) {
		
		CouponDBDAO cou = new CouponDBDAO();
		cou.createCoupon(coupon);
		
	} // createCouponF
	
}
