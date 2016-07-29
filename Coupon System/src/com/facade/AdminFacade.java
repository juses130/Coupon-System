package com.facade;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.exceptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;



public class AdminFacade implements CouponClientFacade{

	// static final instances
	private static final String adminUser = "admin";
	private static final String adminPassword = "1234";
	
	// 3 dao instances (working by CouponSystem-Singelton)
	private static CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	// constructor
	public AdminFacade() throws ConnectorException {
			
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
		
	} // AdminFacade() - contructor

	/*
	 *  Company Access
	 */
	
	public void createCompany(Company company) throws DaoException {
		compDao.createCompany(company);
	} // createCompany - function
	
	public void removeCompany(Company company) throws DaoException{
		
		/* Iv'e added to the DataBase a new Column named - OwnerID.
		 * When we looking in the Coupons list, we may want to know 
		 * who is the owner of this coupon?
		 * so insted of going to Company_Coupon Table and search for 
		 * the specific coupon and only then you will find out who is the owner..
		 * We can see it from the coupons list.
		 */
		
		compDao.removeCompany(company);
		
	} // removeCompanyA - function
	
	public void updateCompany(Company company) throws DaoException{
			compDao.updateCompany(company);
		
	} // updateCompanyA - function

	public Company getCompany(long id) throws DaoException{
		Company company = new Company();
		company = compDao.getCompany(id);
		return company;
		
		
	} // getCompanyA - function
	
	public Company getCompany(String compName) throws DaoException{
		Company company = new Company();
		company = compDao.getCompany(compName);
		return company;
	}
	
	public Collection<Company> getAllCompanies() throws DaoException{
		
		Collection<Company> companies = null;
		companies = compDao.getAllCompanies();
		return companies;
		
	} // getAllCompaniesA - function
	
	/*
	 *  Customer Access
	 */
	
	public void createCustomer(Customer customer) throws DaoException{
		
		custDao.createCustomer(customer);
		
	} // createCustomerA - function
	
	public void removeCustomer(Customer customer) throws DaoException, FiledErrorException{
			custDao.removeCustomer(customer);
	}
	
	public void removeCoupon(Coupon coupon) throws DaoException, FiledErrorException{
		coupDao.removeCoupon(coupon, ClientType.ADMIN);
	}
	
	public void updateCustomer(Customer customer) throws DaoException{
		custDao.updateCustomer(customer);
	} // createCustomerA - function
	
	public Customer getCustomer(long id) throws DaoException, FiledErrorException{
		
		Customer customer = new Customer();
		customer = custDao.getCustomer(id);
		
		if(customer != null) {
		// set the Collection of the Coupons.
		customer.setCoupons(coupDao.getCoupons(customer.getId(), ClientType.CUSTOMER));
		}
		return customer;
	}
	
	public Customer getCustomer(String compName) throws DaoException, FiledErrorException{
		
		Customer customer = new Customer();
//		c.setId(id);
		customer = custDao.getCustomer(compName);
		
		if(customer != null) {
		// set the Collection of the Coupons.
		customer.setCoupons(coupDao.getCoupons(customer.getId(), ClientType.CUSTOMER));
		}
		return customer;
	}

	public Collection<Customer> getAllCustomers() throws DaoException{
		return custDao.getAllCustomers();
	} // getAllCompaniesA - function

	public Set<Coupon> getCouponByPrice(double maxPrice) throws DaoException {
		return coupDao.getCouponByPrice(0, maxPrice, ClientType.ADMIN);
	}
	
	public Set<Coupon> getCouponByType(String category) throws DaoException, FiledErrorException {
		/* we don't need an ID for the admin.. 
		 * But we want to use this function from all the Facades, so
		 * we need id for the two other facades.
		 * 
		 */
		
		// This next two lines checks if the category-String exist in the Enum Or not.
		Coupon coupon = new Coupon();
		coupon.setCategory(category);
		
		long id = 0;
		return coupDao.getCouponByType(id, coupon.getCategory() ,ClientType.ADMIN);
	}

	public Set<Coupon> getAllCoupons() throws DaoException {
		
		Set<Coupon> coupons = coupDao.getCoupons(0, ClientType.ADMIN);
		return coupons;
		
	}
	
	@Override
	public AdminFacade login(String adminName, String password, ClientType client) throws LoginException , DaoException {
		if(adminName.toLowerCase().equals(adminUser) && String.valueOf(password).equals(adminPassword) 
				&& client == ClientType.ADMIN) {
			return this;
		}
		else {
			throw new LoginException ("Admin Login - FAILED (Unidentified user)");
		}
		
	}
	
	
	
}
