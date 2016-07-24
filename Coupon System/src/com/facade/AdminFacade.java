package com.facade;

import java.util.Collection;

import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.exeptionerrors.ConnectorExeption;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.exeptionerrors.LoginException;
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
	public AdminFacade() throws ConnectorExeption {
			
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
		
	} // AdminFacade() - contructor

	/*
	 *  Company Access
	 */
	
	public void createCompany(Company company) throws DaoExeption {
		compDao.createCompany(company);
	} // createCompany - function
	
	public void removeCompany(Company company) throws DaoExeption{
		
		/* Iv'e added to the DataBase a new Column named - OwnerID.
		 * When we looking in the Coupons list, we may want to know 
		 * who is the owner of this coupon?
		 * so insted of going to Company_Coupon Table and search for 
		 * the specific coupon and only then you will find out who is the owner..
		 * We can see it from the coupons list.
		 */
		
		compDao.removeCompany(company);
		
	} // removeCompanyA - function
	
	public void updateCompany(Company company) throws DaoExeption{
			compDao.updateCompany(company);
		
	} // updateCompanyA - function

	public Company getCompany(long id) throws DaoExeption{
		Company company = new Company();
		company = compDao.getCompany(id);
		return company;
		
		
	} // getCompanyA - function
	
	public Company getCompany(String compName) throws DaoExeption{
		Company company = new Company();
		company = compDao.getCompany(compName);
		return company;
	}
	
	public Collection<Company> getAllCompanies() throws DaoExeption{
		
		Collection<Company> companies = null;
		companies = compDao.getAllCompanies();
		return companies;
		
	} // getAllCompaniesA - function
	
	/*
	 *  Customer Access
	 */
	
	public void createCustomer(Customer customer) throws DaoExeption{
		
		custDao.createCustomer(customer);
		
	} // createCustomerA - function
	
	public void removeCustomer(Customer customer) throws DaoExeption, FiledErrorException{
			custDao.removeCustomer(customer);
	}
	
	public void updateCustomer(Customer customer) throws DaoExeption{
		custDao.updateCustomer(customer);
	} // createCustomerA - function
	
	public Customer getCustomer(long id) throws DaoExeption, FiledErrorException{
		
		Customer customer = new Customer();
		customer = custDao.getCustomer(id);
		
		if(customer != null) {
		// set the Collection of the Coupons.
		customer.setCoupons(custDao.getCoupons(id));
		}
		return customer;
	}
	
	public Customer getCustomer(String compName) throws DaoExeption, FiledErrorException{
		
		Customer customer = new Customer();
//		c.setId(id);
		customer = custDao.getCustomer(compName);
		
		if(customer != null) {
		// set the Collection of the Coupons.
		customer.setCoupons(custDao.getCoupons(customer.getId()));
		}
		return customer;
	}

	public Collection<Customer> getAllCustomers() throws DaoExeption{
		return custDao.getAllCustomers();
	} // getAllCompaniesA - function

	@Override
	public AdminFacade login(String adminName, String password, ClientType client) throws LoginException , DaoExeption {
		if(adminName.toLowerCase().equals(adminUser) && String.valueOf(password).equals(adminPassword) 
				&& client == ClientType.ADMIN) {
			return this;
		}
		else {
			throw new LoginException ("Admin Login - FAILED (Unidentified user)");
		}
		
	}
	
	public void removeCoupon(Coupon coupon) throws DaoExeption, FiledErrorException{
		coupDao.removeCoupon(coupon, ClientType.COMPANY);
	}

	
}
