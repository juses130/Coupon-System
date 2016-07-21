package com.dao.interfaces;

import java.util.*;

import com.exeptionerrors.DaoExeption;
import com.javabeans.*;

public interface CompanyDAO {

	public void createCompany(Company company) throws DaoExeption;
	
	public void removeCompany(Company company) throws DaoExeption;
	
	public Coupon createCoupon(Coupon coupon) throws DaoExeption;
	
	public void updateCompany(Company company) throws DaoExeption;

	public Company getCompany(long id) throws DaoExeption;
	
	/**
	 * <p> getCompanyByCoupon - Function</p>
	 * 
	 * This function is allows us to get the Company By some specific coupon from the DataBase. It's an add on function to the system.
	 * 
	 * @param Company {@code Company} object
	 * @param Coupon {@code Coupon} object
 	 * @return a positive {@code Coupon} object value and a positive {@code Company} object value.
	 * if the it was run successfully.
	 * @throws DaoExeption
	 * @author Raziel
	 */
	public Company getCompanyByCoupon(Coupon coupon, Company company) throws DaoExeption;
	
	public Company getCompany(String compName) throws DaoExeption;

	public Collection<Company> getAllCompanies() throws DaoExeption;

	public Collection<Coupon> getCoupons(long compID) throws DaoExeption;

	public boolean login(String compName, String password) throws DaoExeption;



	

}
