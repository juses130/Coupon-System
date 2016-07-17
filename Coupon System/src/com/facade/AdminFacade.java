package com.facade;

import java.sql.SQLException;
import java.util.Collection;

import javax.security.auth.login.LoginException;

import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.dbdao.*;
import com.javabeans.*;
import com.mysql.fabric.xmlrpc.Client;
import com.task.and.singleton.CouponSystem;



public class AdminFacade {

	private static final String adminUser = "admin";
	private static final String password = "1234";
	
	private static CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	
	// constructor
	public AdminFacade() {
		
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		coupDao = CouponSystem.getInstance().getCouponDao();
	}

	/*
	 *  Company Access
	 */
	
	public void createCompany(Company company) {
		
		try {
			compDao.createCompany(company);
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage() + e.getErrorCode());
		}
		
	} // createCompanyA - function
	
	public void removeCompany(Company company) throws SQLException{
		
		Coupon coupon = new Coupon();
		coupon.setOwnerID(company.getId());
		compDao.removeCompany(company);
		coupDao.removeCouponOwnerID(coupon.getOwnerID());
		coupDao.removeCoupon(coupon);
		
	} // removeCompanyA - function
	
	public void updateCompany(Company company) {
		try {
			compDao.updateCompany(company);
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage() + e.getErrorCode());
		}
		
		
	} // updateCompanyA - function

	public Company getCompany(long id) {
		Company c = new Company();
		try {
			c = compDao.getCompany(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
		
		
	} // getCompanyA - function
	
	public Collection<Company> getAllCompaniesA() {
		Collection<Company> companies = null;
		try {
			companies = compDao.getAllCompanies();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
		
	} // getAllCompaniesA - function
	
	/*
	 *  Customer Access
	 */
	
	public void createCustomer(Customer customer) {
		
		IsExistDB.stringExistV3("customer", "cust_name", customer.getCustName());
		if(IsExistDB.getAnswer() == false) {
			custDao.createCustomer(customer);
		}
	} // createCustomerA - function
	
	public void removeCustomer(Customer customer) {
		custDao.removeCustomer(customer);
	}
	
	public void updateCustomer(Customer customer) {
		custDao.updateCustomer(customer);
	} // createCustomerA - function
	
	public Customer getCustomer(long id) {
		
		Customer c = new Customer();
		c.setId(id);
		c = custDao.getCustomer(id);
		
		// set the Collection of the Coupons.
		c.setCoupons(custDao.getCoupons(id));
		
		return c;
	}
	
	public Collection<Customer> getAllCustomers() {
		return custDao.getAllCustomers();
	} // getAllCompaniesA - function
	
	public AdminFacade login(String userName, String password, ClientType clientType) throws LoginException{
		if(userName.toLowerCase().equals(adminUser) && String.valueOf(password).equals(AdminFacade.password)) {
			return this;
		} // if
		else {
			throw new LoginException("Admin Login Failed");
		}
	} // login

}
