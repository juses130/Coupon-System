package com.dao.interfaces;

import java.util.*;


import com.javabeans.*;

import ExeptionErrors.DaoExeption;

public interface CompanyDAO {

	public void createCompany(Company company) throws DaoExeption;

	public Coupon createCoupon(Coupon coupon) throws DaoExeption;
	
	public void removeCompany(Company company) throws DaoExeption;

	public void updateCompany(Company company) throws DaoExeption;

	public Company getCompany(long id) throws DaoExeption;

	public Collection<Company> getAllCompanies() throws DaoExeption;

	public Collection<Coupon> getCoupons(long compID) throws DaoExeption;

	public boolean login(String compName, String password) throws DaoExeption;

	public Company getCompany(String compName) throws DaoExeption;

	

}
