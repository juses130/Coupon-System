package com.facade;


import java.util.*;


import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.exceptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CompanyFacade implements CouponClientFacade{
	
	private Company company;
//	private String compPassword;
	private CompanyDAO compDao = null;
	private CouponDAO coupDao = null;
		
//	
	public CompanyFacade() throws ConnectorException{
		
		compDao = CouponSystem.getInstance().getCompDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	} // CompanyFacade()- Constructor
	
	
	@Override
	public CompanyFacade login(String compName, String password, ClientType type) throws LoginException ,DaoException {
    	boolean loginSuccessful  = false;
    	
			loginSuccessful = compDao.login(compName, password);

			if(loginSuccessful == true) {
				// If the login was Successful, save it in the private Company instance.
				this.company = compDao.getCompany(compName);
	    		return this;
	    	}
			else {
			throw new LoginException("Error: Company Login - FAILED (Unidentified user)");
			}
	} // login - function
	
	public void addCoupon(Coupon coupon) throws DaoException, FiledErrorException {
		
//		coupon.setOwnerID(company.getId());
		
		coupon =  coupDao.createCoupon(coupon, company);
		compDao.addCoupon(coupon, company);
		
	} // createCouponF
	
	public Collection<Coupon> getAllCoupons() throws DaoException{

		Collection<Coupon> coupons = new HashSet<>();
		coupons = coupDao.getCoupons(company.getId(), ClientType.COMPANY);
		
		return coupons;
	} 
	
	public void removeCoupon(Coupon coupon) throws DaoException, FiledErrorException{
		coupon.setOwnerID(company.getId());
		coupDao.removeCoupon(coupon, ClientType.COMPANY);
	}
	
	public Coupon updateCoupon(Coupon coupon) throws DaoException{
		coupDao.updateCoupon(coupon);
		
		return coupon;
	}
	
	public Company viewCompay() throws DaoException{
		Company company = compDao.viewCompany(this.company.getId());
		return company;
	}
	
	public Coupon getCoupon(long id) throws DaoException, FiledErrorException{
		
//		Company company = new Company();
		Coupon coupon = new Coupon();
//		
//		coupon.setId(id);
//		company.setId(company.getId());
//		
//		coupon = compDao.getCoupon(coupon, company);
		coupon = coupDao.getCoupon(id, ClientType.COMPANY);
		return coupon;
	}
	
	public Set<Coupon> getCouponsByType(String category) throws DaoException, FiledErrorException{

		// This next two lines checks if the category-String exist in the Enum Or not.
		Coupon coupon = new Coupon();
		coupon.setCategory(category);
				
		Set<Coupon> coupons = coupDao.getCouponByType(company.getId(), coupon.getCategory(), ClientType.COMPANY);

		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyByPrice(double maxPrice) throws DaoException{
		Set<Coupon> coupons = coupDao.getCouponByPrice(company.getId(), maxPrice, ClientType.COMPANY);
//		
		return coupons;	
	}

}
