package com.facade;

import java.sql.SQLException;
import java.util.Collection;

import com.dbdao.CompanyDBDAO;
import com.dbdao.CustomerDBDAO;
import com.javabeans.*;



public class AdminFacade {

	private static final String userName = "Admin";
	private static final int password = 1234;
	
	// constructor
	public AdminFacade() {}
	
	public boolean login(String userName, int password) {
		if(userName == AdminFacade.userName || password == AdminFacade.password) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 *  Company Access
	 */
	
	public void createCompanyA(Company company) {
		CompanyDBDAO coDB = new CompanyDBDAO();
		coDB.createCompany(company);
		
	} // createCompanyA - function
	
	public void removeCompanyA(Company company) {
		//TODO: I'm here now. I will need to remove all the coupons when we removing company..
		CompanyDBDAO coDB = new CompanyDBDAO();
		try {
			coDB.removeCompany(company);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	} // removeCompanyA - function
	
	public void updateCompanyA(Company company) {
		//TODO:
		CompanyDBDAO coDB = new CompanyDBDAO();
		coDB.updateCompany(company);
		
		
	} // updateCompanyA - function

	public Company getCompanyA(long id) {
		//TODO:
		CompanyDBDAO coDB = new CompanyDBDAO();
		return coDB.getCompany(id);
		
		
	} // getCompanyA - function
	
	public Collection<Company> getAllCompaniesA() {
		//TODO:
		CompanyDBDAO coDB = new CompanyDBDAO();
		return coDB.getAllCompanies();
		
		
	} // getAllCompaniesA - function
	
	/*
	 *  Customer Access
	 */
	
	public void createCustomerA(Customer customer) {
		//TODO:
		CustomerDBDAO cuDB = new CustomerDBDAO();
		cuDB.createCustomer(customer);
	} // createCustomerA - function
	
	public void removeCustomer(Customer customer) {
		//TODO:
		CustomerDBDAO cuDB = new CustomerDBDAO();
		cuDB.removeCustomer(customer);
	}
}
