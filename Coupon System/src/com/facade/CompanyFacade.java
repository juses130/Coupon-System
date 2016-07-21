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
	}
	
	
	public void createCoupon(Coupon coupon) throws DaoExeption{
		/*  Now he will make sure that the facede will get only the 
		 *  RIGHT parameters of the Company object.
		 *  Even if the user puts some incurrect inputs.
		 *  the CompanyFacade will bring the RIGHT parameters by the company Name.
		 */
		
		compDao.createCoupon(coupon);
//		coupDao.createCoupon(coupon);
		
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
	
	public Company viewCompay(long id) throws DaoExeption{
		
		Company company = new Company();
		company = compDao.getCompany(id);
		
		return company;
	}
	
	public Coupon getCoupon(Coupon coupon, Company company) throws DaoExeption{
		
		Company comp = compDao.getCompanyByCoupon(coupon, company);
		Coupon newCoupon = coupDao.getCoupon(comp.getId(), ClientType.COMPANY);
		return newCoupon;
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
			throw new LoginException("Company Login - FAILED (Unidentified user)");
			}
	} // login - function
	
	
}
