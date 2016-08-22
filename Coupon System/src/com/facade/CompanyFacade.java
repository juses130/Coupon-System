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
		coupons = compDao.getCoupons(company.getId());
		
		return coupons;
	} // getAllCoupons
	
	public void removeCoupon(Coupon coupon) throws DaoException, FiledErrorException{
		coupon.setOwnerID(company.getId());
		coupDao.removeCoupon(coupon, ClientType.COMPANY);
	} // removeCoupon
	
	public Coupon updateCoupon(Coupon coupon) throws DaoException{
		coupDao.updateCoupon(coupon);
		return coupon;
	} // updateCoupon
	
	public Company viewCompay() throws DaoException{
		Company company = compDao.viewCompany(this.company.getId());
		return company;
	} // viewCompay
	
	public Coupon getCoupon(long id) throws DaoException, FiledErrorException{
		
		Coupon coupon = new Coupon();
		coupon = coupDao.getCoupon(id, ClientType.COMPANY);
		return coupon;
	} // getCoupon
	
	public Collection<Coupon> getCouponsByType(String category) throws DaoException, FiledErrorException{

		// This next two lines checks if the category-String exist in the Enum Or not.
		Coupon coupon = new Coupon();
		coupon.setCategory(category);
		Collection<Coupon> coupons = coupDao.getCouponByType(company.getId(), coupon.getCategory(), ClientType.COMPANY);
		return coupons;
	} // getCouponsByType
	
	public Collection<Coupon> getCouponsOfCompanyByPrice(double maxPrice) throws DaoException{
		Collection<Coupon> coupons = coupDao.getCouponByPrice(company.getId(), maxPrice, ClientType.COMPANY);
		return coupons;	
	} // getCouponsOfCompanyByPrice

} // Class
