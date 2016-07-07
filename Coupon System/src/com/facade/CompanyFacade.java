package com.facade;

import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.javabeans.*;

public class CompanyFacade {

	public CompanyFacade() {}
	
	
	public void createCouponF(Coupon coupon) {
		
		CouponDBDAO cou = new CouponDBDAO();
		short companyCreator = 1;
		cou.setCreator(companyCreator);
		cou.createCoupon(coupon);
		
	} // createCouponF
	
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
	
	
}
