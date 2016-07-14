package com.facade;


import java.sql.SQLException;
import java.util.*;

import com.added.functions.DBconnector;
import com.added.functions.SharingData;
import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.dbdao.CustomerDBDAO;
import com.javabeans.*;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import com.task.and.singleton.CouponSystem;

public class CompanyFacade {

	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	public CompanyFacade() {
		
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	
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
	
	public Coupon updateCouponA(Coupon coupon) {
		CouponDBDAO cupDB = new CouponDBDAO();
		cupDB.updateCouponV2(coupon);
		
		return coupon;
	}
	
	public Company viewCompay(long id) {
		
		Company c = new Company();
		CompanyDBDAO comDB = new CompanyDBDAO();
		c = comDB.getCompany(id);
		
		return c;
	}
	
	public Coupon getCouponA(Coupon coupon) {
		
		CouponDBDAO couDB = new CouponDBDAO();
		coupon = couDB.getCoupon(coupon.getId());
		
		return coupon;
	}
	
	public Set<Coupon> getCouponsByType(long custID, CouponType category) {
		
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByTypeV2("company_coupon", "comp_id" ,custID, category);

		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyF(long compID) {
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponsOfCompany(compID);
		
		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyByPrice(double maxPrice) {
		CouponDBDAO coupDB = new CouponDBDAO();
		Set<Coupon> coupons = coupDB.getCouponByPriceV2("company_coupon" ,"comp_id", SharingData.getIdsShare() ,maxPrice);
		
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
