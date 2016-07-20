package com.facade;


import java.sql.SQLException;
import java.util.*;


import com.added.functions.SharingData;
import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.dbdao.CompanyDBDAO;
import com.exeptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CompanyFacade implements CouponClientFacade{
	
	private long compId;
	private String compName;
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
//	
	public CompanyFacade() throws ConnectorExeption{
		
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	
	public void createCoupon(Coupon coupon) throws DaoExeption{
		coupDao.createCoupon(coupon);
		
	} // createCouponF
	
	public Collection<Coupon> getAllCoupons(long compID) throws DaoExeption{
		
		Collection<Coupon> coupons = new HashSet<>();
		try {
			coupons = compDao.getCoupons(compID);
		} catch (DaoExeption e) {
			throw new DaoExeption("Error: Getting all coupons - FAILED (check the company ID)");
		}
		
		return coupons;
	}
	
	public void removeCoupon(Coupon coupon) throws DaoExeption{
		coupDao.removeCoupon(coupon);
	}
	
	public Coupon updateCoupon(Coupon coupon) throws DaoExeption{
		coupDao.updateCoupon(coupon);
		
		return coupon;
	}
	
	public Company viewCompay(long id) throws DaoExeption{
		Company c = new Company();
		try {
			c = compDao.getCompany(id);
		} catch (DaoExeption e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	public Coupon getCoupon(Coupon coupon) throws DaoExeption{
		coupon = coupDao.getCoupon(coupon.getId());
		
		return coupon;
	}
	
	public Set<Coupon> getCouponsByType(long custID, CouponType category) throws DaoExeption{
		
		Set<Coupon> coupons = coupDao.getCouponByType("company_coupon", "comp_id" ,custID, category);

		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompany(long compID) throws DaoExeption{
		Set<Coupon> coupons = coupDao.getCouponsOfCompany(compID);
		
		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyByPrice(double maxPrice) throws DaoExeption{
		Set<Coupon> coupons = coupDao.getCouponByPrice("company_coupon" ,"comp_id", SharingData.getIdsShare() ,maxPrice);
		
		return coupons;	
	}
	
    public CompanyFacade login(String compName, String password, ClientType type) throws LoginException ,DaoExeption {
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    	boolean loginSuccessful  = false;
    	
			loginSuccessful = companyDBDAO.login(compName, password);
			if(loginSuccessful == true) {
	    		return this;
	    	}
			else {
			throw new LoginException("Company Login Failed");
			}
	} // login - function
	
	
}
