package com.facade;


import java.util.*;


import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.exeptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class CompanyFacade implements CouponClientFacade{
	
	private long compID;
//	private String compPassword;
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
		this.compID = company.getId();
		company.setId(this.compID);
		compDao.addCoupon(coupon, company);
		
	} // createCouponF
	
	public Collection<Coupon> getAllCoupons() throws DaoExeption{

		Collection<Coupon> coupons = new HashSet<>();
		coupons = coupDao.getCoupons(compID, ClientType.COMPANY);
		
		return coupons;
	} 
	
	public void removeCoupon(Coupon coupon) throws DaoExeption, FiledErrorException{
		coupon.setOwnerID(this.compID);
		coupDao.removeCoupon(coupon, ClientType.COMPANY);
	}
	
	public Coupon updateCoupon(Coupon coupon) throws DaoExeption{
		coupDao.updateCoupon(coupon);
		
		return coupon;
	}
	
	public Company viewCompay() throws DaoExeption{
		System.out.println(compID);
		Company company = compDao.viewCompany(this.compID);
		return company;
	}
	
	public Coupon getCoupon(long id) throws DaoExeption, FiledErrorException{
		
		Company company = new Company();
		Coupon coupon = new Coupon();
		
		coupon.setId(id);
		company.setId(this.compID);
		
		coupon = compDao.getCoupon(coupon, company);
		return coupon;
	}
	
	public Set<Coupon> getCouponsByType(CouponType category) throws DaoExeption{

		Set<Coupon> coupons = coupDao.getCouponByType(this.compID, category, ClientType.COMPANY);

		return coupons;

	}
	
	public Set<Coupon> getCouponsOfCompanyByPrice(double maxPrice) throws DaoExeption{
		Set<Coupon> coupons = coupDao.getCouponByPrice(compID, maxPrice, ClientType.COMPANY);
//		
		return coupons;	
	}
	
	@Override
	public CompanyFacade login(String compName, String password, ClientType type) throws LoginException ,DaoExeption {
    	boolean loginSuccessful  = false;
    	
			loginSuccessful = compDao.login(compName, password);

			if(loginSuccessful == true) {
				// If the login was Successful, save it in the private instatces.
				Company comp = compDao.getCompany(compName);
				this.compID = comp.getId();
//				this.compPassword = password;
	    		return this;
	    	}
			else {
			throw new LoginException("Error: Company Login - FAILED (Unidentified user)");
			}
	} // login - function
	
	
}
