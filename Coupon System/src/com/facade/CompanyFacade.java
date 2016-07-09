package com.facade;


import java.util.*;

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
	
	public Collection<Coupon> getAllCoupons(long compID) {
		
		Collection<Coupon> coupons = new HashSet<>();
		CompanyDBDAO compDB = new CompanyDBDAO();
		coupons = compDB.getCoupons(compID);
		
		return coupons;
	}
	
   public boolean login(long compID, String password) {
		
		CompanyDBDAO db = new CompanyDBDAO();
		boolean exsistOrNot = db.login(compID, password);
		
		if(exsistOrNot != true) {
			return false;
		}
		else {
			return true;
		} // else
	} // login - function
	
	
}
