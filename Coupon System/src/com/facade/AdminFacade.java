package com.facade;

import java.sql.SQLException;
import java.util.Collection;

import javax.security.auth.login.LoginException;

import com.added.functions.IsExistDB;
import com.dao.interfaces.CompanyDAO;
import com.dao.interfaces.CouponDAO;
import com.dao.interfaces.CustomerDAO;
import com.dbdao.*;
import com.javabeans.*;
import com.task.and.singleton.CouponSystem;



public class AdminFacade {

	private static final String adminUser = "admin";
	private static final String password = "1234";
	
	private CompanyDAO compDao = null;
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
		CompanyDBDAO coDB = new CompanyDBDAO();
//		IsExistDB.stringExistV3("company", "comp_name", company.getCompName());
//		if(IsExistDB.isDosentExistInDB() == false) {
			coDB.createCompany(company);
//		}
		
	} // createCompanyA - function
	
	public void removeCompanyA(Company company) throws SQLException{
		
		Coupon coupon = new Coupon();
		CompanyDBDAO coDB = new CompanyDBDAO();
		CouponDBDAO couDB = new CouponDBDAO();
		
		coDB.removeCompany(company);
		
		coupon.setOwnerID(company.getId());
		couDB.removeCouponOwnerID(coupon.getOwnerID());
		couDB.removeCoupon(coupon);
		

	} // removeCompanyA - function
	
	public void updateCompanyA(Company company) {
		
		CompanyDBDAO coDB = new CompanyDBDAO();
		coDB.updateCompany(company);
		
		
	} // updateCompanyA - function

	public Company getCompanyA(long id) {
		
		CompanyDBDAO coDB = new CompanyDBDAO();
		Company c = new Company();
		c = coDB.getCompany(id);
		//System.out.println(c.toString());
		return c;
		
		
	} // getCompanyA - function
	
	public Collection<Company> getAllCompaniesA() {
	
		CompanyDBDAO coDB = new CompanyDBDAO();
		return coDB.getAllCompanies();
		
		
	} // getAllCompaniesA - function
	
	/*
	 *  Customer Access
	 */
	
	public void createCustomerA(Customer customer) {
		
		IsExistDB.stringExistV3("customer", "cust_name", customer.getCustName());
		if(IsExistDB.getAnswer() == false) {
			CustomerDBDAO cuDB = new CustomerDBDAO();
			cuDB.createCustomer(customer);
		}
	} // createCustomerA - function
	
	public void removeCustomerA(Customer customer) {
		CustomerDBDAO cuDB = new CustomerDBDAO();
		cuDB.removeCustomer(customer);
	}
	
	public void updateCustomerA(Customer customer) {
		// TODO
		CustomerDBDAO cuDB = new CustomerDBDAO();
		cuDB.updateCustomer(customer);
	} // createCustomerA - function
	
	public Customer getCustomerA(long id) {
		
		CustomerDBDAO cusDB = new CustomerDBDAO();
		Customer c = new Customer();
		c.setId(id);
		c = cusDB.getCustomer(id);
		
		// set the Collection of the Coupons.
		c.setCoupons(cusDB.getCoupons(id));
		
		return c;
	}
	
	public Collection<Customer> getAllCustomersA() {
		
		CustomerDBDAO coDB = new CustomerDBDAO();
		return coDB.getAllCustomers();
		
		
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
