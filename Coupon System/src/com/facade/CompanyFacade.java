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
	
	// Instance of Security Access Check
	private static boolean companyIsConnected = false;
	
	// Constructor
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
				companyIsConnected = true;
	    		return this;
	    	}
			else {
			throw new LoginException("Error: Company Login - FAILED (Unidentified user)");
			}
	} // login - function
	
	public void addCoupon(Coupon coupon) throws DaoException, FiledErrorException {
		
		// Security Access Check	
		if(companyIsConnected != false) {
			coupon =  coupDao.createCoupon(coupon, company);
			compDao.addCoupon(coupon, company);
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected
	} // createCouponF
	
	public Collection<Coupon> getAllCoupons() throws DaoException{

		// Security Access Check	
		if(companyIsConnected != false) {
			Collection<Coupon> coupons = new HashSet<>();
			coupons = compDao.getCoupons(company.getId());
			return coupons;
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected
	} // getAllCoupons
	
	public void removeCoupon(Coupon coupon) throws DaoException, FiledErrorException{
		
		// Security Access Check	
		if(companyIsConnected != false) {
			coupon.setOwnerID(company.getId());
			coupDao.removeCoupon(coupon, ClientType.COMPANY);
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected
	} // removeCoupon
	
	public Coupon updateCoupon(Coupon coupon) throws DaoException{
		
		// Security Access Check	
		if(companyIsConnected != false) {
			coupDao.updateCoupon(coupon);
			return coupon;
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected
	} // updateCoupon
	
	public Company viewCompay() throws DaoException{
		
		// Security Access Check	
		if(companyIsConnected != false) {
			Company company = compDao.viewCompany(this.company.getId());
			return company;
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected
	} // viewCompay
	
	public Coupon getCoupon(long id) throws DaoException, FiledErrorException{
		
		// Security Access Check	
		if(companyIsConnected != false) {
			Coupon coupon = new Coupon();
			coupon = coupDao.getCoupon(id, ClientType.COMPANY);
			return coupon;
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected
	} // getCoupon
	
	public Collection<Coupon> getCouponsByType(String category) throws DaoException, FiledErrorException{

		// Security Access Check	
		if(companyIsConnected != false) {
			// This next two lines checks if the category-String exist in the Enum Or not.
			Coupon coupon = new Coupon();
			coupon.setCategory(category);
			Collection<Coupon> coupons = coupDao.getCouponByType(company.getId(), coupon.getCategory(), ClientType.COMPANY);
			return coupons;
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected
	} // getCouponsByType
	
	public Collection<Coupon> getCouponsOfCompanyByPrice(double maxPrice) throws DaoException{
		// Security Access Check	
		if(companyIsConnected != false) {
			Collection<Coupon> coupons = coupDao.getCouponByPrice(company.getId(), maxPrice, ClientType.COMPANY);
			return coupons;
			} // if - companyIsConnected
			else {
				throw new DaoException("Error: Access Denied - Company login failed!");
			} // else - companyIsConnected	
	} // getCouponsOfCompanyByPrice

} // Class
