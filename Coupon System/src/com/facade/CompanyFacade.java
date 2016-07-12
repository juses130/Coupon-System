package com.facade;


import java.util.*;

import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.dbdao.CustomerDBDAO;
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
	
	public void removeCouponA(Coupon coupon) {
		
		CouponDBDAO couDB = new CouponDBDAO();
		couDB.removeCoupon(coupon);
	}
	
	public void updateCouponA(Coupon coupon) {
		CouponDBDAO cupDB = new CouponDBDAO();
		cupDB.updateCoupon(coupon);
	}
	
	public Coupon getCouponA(Coupon coupon) {
		
		CouponDBDAO couDB = new CouponDBDAO();
		coupon = couDB.getCoupon(coupon.getId());
		
		return coupon;
	}
	
	public Collection<Coupon> getCouponsByType(CouponType couponCategory) {
		
		CouponDBDAO couDB = new CouponDBDAO();
		return couDB.getCouponByType(couponCategory);

	}
	
	public Set<Coupon> getCouponsOfCompanyF(long compID) {
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponsOfCompany(compID);
		
		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyByPrice(double minPrice, double maxPrice) {
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByPriceV3("company_coupon" ,minPrice, maxPrice);
		
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
