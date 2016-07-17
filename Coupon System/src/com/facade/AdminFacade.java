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
	
	public void createCompanyA(Company company) {
		
		try {
			compDao.createCompany(company);
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage() + e.getErrorCode());
		}
		
	} // createCompanyA - function
	
	public void removeCompanyA(Company company) throws SQLException{
		
		Coupon coupon = new Coupon();
		coupon.setOwnerID(company.getId());
		compDao.removeCompany(company);
		coupDao.removeCouponOwnerID(coupon.getOwnerID());
		coupDao.removeCoupon(coupon);
		
	} // removeCompanyA - function
	
	public void updateCompanyA(Company company) {
		try {
			compDao.updateCompany(company);
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage() + e.getErrorCode());
		}
		
		
	} // updateCompanyA - function

	public Company getCompanyA(long id) {
		Company c = new Company();
		c = compDao.getCompany(id);
		return c;
		
		
	} // getCompanyA - function
	
	public Collection<Company> getAllCompaniesA() {
		return compDao.getAllCompanies();
		
		
	} // getAllCompaniesA - function
	
	/*
	 *  Customer Access
	 */
	
	public void createCustomerA(Customer customer) {
		
		IsExistDB.stringExistV3("customer", "cust_name", customer.getCustName());
		if(IsExistDB.getAnswer() == false) {
			custDao.createCustomer(customer);
		}
	} // createCustomerA - function
	
	public void removeCustomerA(Customer customer) {
		custDao.removeCustomer(customer);
	}
	
	public void updateCustomerA(Customer customer) {
		custDao.updateCustomer(customer);
	} // createCustomerA - function
	
	public Customer getCustomerA(long id) {
		
		Customer c = new Customer();
		c.setId(id);
		c = custDao.getCustomer(id);
		
		// set the Collection of the Coupons.
		c.setCoupons(custDao.getCoupons(id));
		
		return c;
	}
	
	public Collection<Customer> getAllCustomersA() {
		return custDao.getAllCustomers();
	} // getAllCompaniesA - function
	
	public boolean login(String userName, String password) {
		
		if(userName.toLowerCase().equals(adminUser) && String.valueOf(password).equals(AdminFacade.password)) {
			return true;
		}
		else {
			return false;
		}
	}
}
