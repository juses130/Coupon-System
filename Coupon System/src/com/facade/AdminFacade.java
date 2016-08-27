package com.facade;

import java.util.Collection;
import java.util.Set;

import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.exceptionerrors.*;
import com.javabeans.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;

public class AdminFacade implements CouponClientFacade{

	 // Attributes
	 
	// static final instances
	private static final String adminUser = "admin";
	private static final String adminPassword = "1234";
	// Instance of Security Access Check
	private static boolean adminIsConnected = false;
	
	// 3 dao instances (working by CouponSystem-Singleton)
	private  CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	// Constructor
	public AdminFacade() throws ConnectorException {
			
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
		
	} // AdminFacade() - constructor

	@Override
	public AdminFacade login(String adminName, String password, ClientType client) throws LoginException , DaoException {
		if(adminName.toLowerCase().equals(adminUser) && String.valueOf(password).equals(adminPassword) 
				&& client == ClientType.ADMIN) {
			adminIsConnected = true;
			return this;
		} // if
		else {
			throw new LoginException ("Admin Login - FAILED (Incorrect password or username)");
			} // else
	} // login
	
	/*
	 *  Company Access
	 */
	
	public void createCompany(Company company) throws DaoException {
		
		// Security Access Check		
		if(adminIsConnected != false) {
			compDao.createCompany(company);
			}
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			}
	} // createCompany - function
	
	public void removeCompany(Company company) throws DaoException{
		/* Iv'e added to the DataBase a new Column named - OwnerID.
		 * When we looking in the Coupons list, we may want to know 
		 * who is the owner of this coupon?
		 * so instead of going to Company_Coupon Table and search for 
		 * the specific coupon and only then you will find out who is the owner..
		 * We can see it from the coupons list.
		 */
		
		// Security Access Check	
		if(adminIsConnected != false) {
			compDao.removeCompany(company);
			}
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			}
	} // removeCompanyA
	
	public void updateCompany(Company company) throws DaoException{
		
		// Security Access Check
		if(adminIsConnected != false) {
		compDao.updateCompany(company);
		}
		else {
			throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
		}
	} // updateCompanyA 

	public Company getCompany(long id) throws DaoException{
		
		// Security Access Check	
		if(adminIsConnected != false) {
			Company company = new Company();
			company = compDao.getCompany(id);
			return company;			}
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			}
	
	} // getCompanyA - ID long
	
	public Company getCompany(String compName) throws DaoException{
		
		// Security Access Check	
		if(adminIsConnected != false) {
			Company company = new Company();
			company = compDao.getCompany(compName);
			return company;		
			}
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			}

	} // getCompany - Name String
	
	public Collection<Company> getAllCompanies() throws DaoException{
		
		// Security Access Check	
		if(adminIsConnected != false) {
			Collection<Company> companies = null;
			companies = compDao.getAllCompanies();
			return companies;
			}
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			}

	} // getAllCompaniesA 
	
	/*
	 *  Customer Access
	 */
	
	public void createCustomer(Customer customer) throws DaoException{
		
		// Security Access Check	
		if(adminIsConnected != false) {
			custDao.createCustomer(customer);
			}
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			}
	} // createCustomerA 
	
	public void removeCustomer(Customer customer) throws DaoException, FiledErrorException{
			
		// Security Access Check	
		if(adminIsConnected != false) {
			custDao.removeCustomer(customer);
			}
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			}
	} // removeCustomer
		
	public void updateCustomer(Customer customer) throws DaoException{
		custDao.updateCustomer(customer);
	} // createCustomerA
	
	public Customer getCustomer(long id) throws DaoException, FiledErrorException{
		
		// Security Access Check	
		if(adminIsConnected != false) {
			Customer customer = new Customer();
			customer = custDao.getCustomer(id);
			
			if(customer != null) {
			// set the Collection of the Coupons.
				customer.setCoupons(custDao.getCoupons(customer.getId()));
			} // if
			return customer;
			} // if - adminIsConnected
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			} // else - adminIsConnected
	} // getCustomer - id long
	
	public Customer getCustomer(String compName) throws DaoException, FiledErrorException{
		
		// Security Access Check	
		if(adminIsConnected != false) {
			Customer customer = new Customer();
			customer = custDao.getCustomer(compName);
			
			if(customer != null) {
			// set the Collection of the Coupons.
			customer.setCoupons(custDao.getCoupons(customer.getId()));
			} // if
			return customer;
			} // if - adminIsConnected
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			} // else - adminIsConnected
		
	} // getCustomer - name String

	public Collection<Customer> getAllCustomers() throws DaoException{
		// Security Access Check	
		if(adminIsConnected != false) {
			return custDao.getAllCustomers();
			} // if - adminIsConnected
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			} // else - adminIsConnected
	} // getAllCompaniesA

	/*
	 * Coupon Access
	 */
	
	public void removeCoupon(Coupon coupon) throws DaoException, FiledErrorException{
		
		// Security Access Check	
		if(adminIsConnected != false) {
			coupDao.removeCoupon(coupon, ClientType.ADMIN);
			} // if - adminIsConnected
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			} // else - adminIsConnected
	} // removeCoupon
	
	public Collection<Coupon> getCouponByPrice(double maxPrice) throws DaoException {
		
		// Security Access Check	
		if(adminIsConnected != false) {
			return coupDao.getCouponByPrice(0, maxPrice, ClientType.ADMIN);
			} // if - adminIsConnected
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			} // else - adminIsConnected
	} // getCouponByPrice
	
	public Collection<Coupon> getCouponByType(String category) throws DaoException, FiledErrorException {
		/* we don't need an ID for the admin.. 
		 * But we want to use this function from all the Facades, so
		 * we need id for the two other facades.
		 * 
		 */
		
		// Security Access Check	
		if(adminIsConnected != false) {
			// This next two lines checks if the category-String exist in the Enum Or not.
			Coupon coupon = new Coupon();
			coupon.setCategory(category);
			
			long id = 0;
			return coupDao.getCouponByType(id, coupon.getCategory() ,ClientType.ADMIN);
			} // if - adminIsConnected
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			} // else - adminIsConnected
	} // getCouponByType

	public Collection<Coupon> getAllCoupons() throws DaoException {
		
		// Security Access Check	
		if(adminIsConnected != false) {
			Collection<Coupon> coupons = coupDao.getAllCoupons();
			return coupons;
			} // if - adminIsConnected
			else {
				throw new DaoException("Error: Access Denied [Admin] - FAILED (Unidentified user)");
			} // else - adminIsConnected
	} // getAllCoupons
	
} // AdminFacade
