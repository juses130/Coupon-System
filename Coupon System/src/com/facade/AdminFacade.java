package com.facade;

import java.sql.SQLException;
import java.util.Collection;

import com.added.functions.SharingData;
import com.dbdao.*;
import com.javabeans.*;



public class AdminFacade {

//	private static final String userName = "Admin";
//	private static final int password = 1234;
	
	// constructor
	public AdminFacade() {}

	/*
	 *  Company Access
	 */
	
	public void createCompanyA(Company company) {
		CompanyDBDAO coDB = new CompanyDBDAO();
		coDB.createCompany(company);
		
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
		
		CustomerDBDAO cuDB = new CustomerDBDAO();
		cuDB.createCustomer(customer);
	} // createCustomerA - function
	
	public void removeCustomerA(Customer customer) {
		//TODO: it's working BUT we need to delete the customers coupons for the Table customer_coupon.
		CustomerDBDAO cuDB = new CustomerDBDAO();
		cuDB.removeCustomer(customer);
	}
	
	public void updateCustomerA(Customer customer) {
		// TODO
		CustomerDBDAO cuDB = new CustomerDBDAO();
		cuDB.updateCustomer(customer);
	} // createCustomerA - function
	
	public boolean login(String userName, int password) {
		if(userName == "admin" || password == 1234) {
			return true;
		}
		else {
			return false;
		}
	}
}
