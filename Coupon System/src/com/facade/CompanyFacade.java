package com.facade;


import java.sql.SQLException;
import java.util.*;

import com.added.functions.SharingData;
import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.dbdao.CompanyDBDAO;
import com.javabeans.*;
import com.task.and.singleton.CouponSystem;

public class CompanyFacade {

	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
//	
	public CompanyFacade() {
		
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	
	public void createCouponF(Coupon coupon) {
		
		short companyCreator = 1;
		coupDao.setCreator(companyCreator);
		coupDao.createCoupon(coupon);
		
	} // createCouponF
	
	public Collection<Coupon> getAllCoupons(long compID) {
		
		Collection<Coupon> coupons = new HashSet<>();
		try {
			coupons = compDao.getCoupons(compID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return coupons;
	}
	
	public void removeCoupon(Coupon coupon) {
		coupDao.removeCoupon(coupon);
	}
	
	public Coupon updateCoupon(Coupon coupon) {
		coupDao.updateCoupon(coupon);
		
		return coupon;
	}
	
	public Company viewCompay(long id) {
		Company c = new Company();
		try {
			c = compDao.getCompany(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	public Coupon getCouponA(Coupon coupon) {
		coupon = coupDao.getCoupon(coupon.getId());
		
		return coupon;
	}
	
	public Set<Coupon> getCouponsByType(long custID, CouponType category) {
		
		Set<Coupon> coupons = coupDao.getCouponByType("company_coupon", "comp_id" ,custID, category);

		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompany(long compID) {
		Set<Coupon> coupons = coupDao.getCouponsOfCompany(compID);
		
		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyByPrice(double maxPrice) {
		Set<Coupon> coupons = coupDao.getCouponByPrice("company_coupon" ,"comp_id", SharingData.getIdsShare() ,maxPrice);
		
		return coupons;	
	}
	
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
