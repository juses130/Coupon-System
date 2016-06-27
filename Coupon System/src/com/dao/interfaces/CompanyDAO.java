package com.dao.interfaces;

import java.sql.SQLException;
import java.util.*;
import com.javabeans.*;

public interface CompanyDAO {
	
	public void createCompany(Company company) throws SQLException;
	
	/*
	 * *********************************************************************
	 *  We have 3 Functions of removing! 
	 *  Using the remove methods By name and By ID is make more sense then
	 *  using the company as requested in the project guide).
	 */
	public void removeCompany(long id) throws SQLException;;
	public void removeCompany(String name) throws SQLException;
	public void removeCompany(Company company) throws SQLException;
	//**********************************************************************
	
	public void updateCompany(Company company) throws SQLException;
	public Company getCompany(long id);
	public Collection<Company> getAllCompanies() ;
	public Collection<Coupon> getCoupons(long compID);
	public boolean login(String compName, String password);
	
}
