package com.facade;


import java.util.*;


import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.dbdao.CompanyDBDAO;
import com.exeptionerrors.*;
import com.javabeans.*;
import com.mysql.fabric.xmlrpc.Client;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CompanyFacade implements CouponClientFacade{
	
	private CompanyDAO compDao = null;
	private CouponDAO coupDao = null;
		
//	
	public CompanyFacade() throws ConnectorExeption{
		
		compDao = CouponSystem.getInstance().getCompDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	} // CompanyFacade()- Constructor
	
	
	public void addCoupon(Coupon coupon, Company company) throws DaoExeption{
		
		/*  Now he will make sure that the facede will get only the 
		 *  RIGHT parameters of the Company object.
		 *  Even if the user puts some incurrect inputs.
		 *  the CompanyFacade will bring the RIGHT parameters by the company Name.
		 */
		compDao.addCoupon(coupon, company);
		
	} // createCouponF
	
	public Collection<Coupon> getAllCoupons(long compID) throws DaoExeption{

		Collection<Coupon> coupons = new HashSet<>();
		coupons = compDao.getCoupons(compID);
		
		return coupons;
	}
	
	public void removeCoupon(Coupon coupon) throws DaoExeption{
		coupDao.removeCoupon(coupon);
	}
	
	public Coupon updateCoupon(Coupon coupon) throws DaoExeption{
		coupDao.updateCoupon(coupon);
		
		return coupon;
	}
	
	public Company viewCompay(long id, String password) throws DaoExeption{
		
		Company company = compDao.viewCompany(id, password);
		return company;
	}
	
	public Coupon getCoupon(Coupon coupon, Company company) throws DaoExeption{
		coupon = compDao.getCoupon(coupon, company);
		return coupon;
	}
	
	public Set<Coupon> getCouponsByType(long custID, CouponType category) throws DaoExeption{
		//TODO: create this function - NOT WORKING FOR NOW

		Set<Coupon> coupons = coupDao.getCouponByType("company_coupon", "comp_id" ,custID, category);

		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyByPrice(double maxPrice) throws DaoExeption{
		//TODO: create this function - NOT WORKING FOR NOW
		Set<Coupon> coupons = coupDao.getCouponByPrice("company_coupon" ,"comp_id", 0 ,maxPrice);
		
		return coupons;	
	}
	
	@Override
	public CompanyFacade login(String compName, String password, ClientType type) throws LoginException ,DaoExeption {
    	boolean loginSuccessful  = false;
    	
			loginSuccessful = compDao.login(compName, password);
			if(loginSuccessful == true) {
	    		return this;
	    	}
			else {
			throw new LoginException("Error: Company Login - FAILED (Unidentified user)");
			}
	} // login - function
	
	
}
