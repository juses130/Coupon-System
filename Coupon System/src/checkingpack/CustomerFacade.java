package checkingpack;

import java.util.Set;

import javax.security.auth.login.LoginException;

import com.dao.interfaces.*;
import com.javabeans.*;
import com.task.and.singleton.CouponSystem;

import ExeptionErrors.DaoExeption;

public class CustomerFacade {

	private long custId;
	private String custName;
//	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	public CustomerFacade(){
		
//		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}
	
	public Coupon purchaseCoupon(Coupon coupon) {
		
		short CustomerCreator = 2;
		coupDao.setCreator(CustomerCreator);
		long id = coupDao.createCoupon(coupon);
		coupon = coupDao.getCoupon(id);
		
		return coupon;
		
	} // purchaseCoupon
	
	public Set<Coupon> getAllPurchasedCoupons(long custID) {
		
		Set<Coupon> coupons = custDao.getCoupons(custID);
		return coupons;
		
	} // getAllCoupons
	
	public Set<Coupon> getAllCouponsByPrice(long custID ,double maxPrice) {
		Set<Coupon> coupons = custDao.getCouponByPrice("customer_coupon", "Cust_id", custID, maxPrice);
		
		return coupons;	
	}
	
	public Set<Coupon> getAllCouponsByType(long custID, CouponType category) {
		
		Set<Coupon> coupons = custDao.getCouponByType("customer_coupon", "cust_id" ,custID, category);
		return coupons;
	}
	
    public CustomerFacade login(String custName, String password, ClientType clientType) throws LoginException, DaoExeption {
    	
    	boolean loginSuccessful  = false;
    	try {
    		loginSuccessful  = custDao.login(custName, password);
		} catch (Exception e) {
			throw new DaoExeption("Customer Login Failed");
		}
    	
    	if (loginSuccessful && clientType.equals(ClientType.COMPANY)) {
			//company = compDao.getCompany(compName);
			this.custId = custDao.getCustomer(custName);
			this.custName = custName;
			return this;
		} else {
			throw new LoginException("Customer Login - FAILED (Unidentified user)");
		}
	} // login - function
	
}
