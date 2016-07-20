package com.facade;


import java.util.*;


import com.added.functions.SharingData;
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
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
//	
	public CompanyFacade() throws ConnectorExeption{
		
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	
	public void createCoupon(Company company, Coupon coupon) throws DaoExeption{
		/*  Now he will make sure that the facede will get only the 
		 *  RIGHT parameters of the Company object.
		 *  Even if the user puts some incurrect inputs.
		 *  the CompanyFacade will bring the RIGHT parameters by the company Name.
		 */

		Company newCompany = compDao.getCompany(company.getCompName());

		compDao.createCoupon(newCompany, coupon);
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
		
		Set<Coupon> coupons = coupDao.getCouponByType("company_coupon", "comp_id" ,custID, category);

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
