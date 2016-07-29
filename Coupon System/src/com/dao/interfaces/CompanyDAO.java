package com.dao.interfaces;

import java.util.*;

import com.exceptionerrors.DaoException;
import com.javabeans.*;

public interface CompanyDAO {

	/**
	 * Checks if the password and company name given, matches the credentials stored in the underlying database (or any other persistence storage).
	 * @param compName a {@code String} Company name 
	 * @param password a {@code String} password
	 * @return {@code true} if the credentials match, otherwise {@code false}
	 * @throws DAOException 
	 */
	public boolean login(String compName, String password) throws DaoException;
	
	/**
	 * Create a new Company in the underlying database (or any other persistence storage), from a given {@code Company} object. 
	 * @param company a {@code Company} object
	 * @return a positive {@code long} value corresponding to the ID of the newly created company, or {@code -1} if the operation failed.  
	 * @throws DAOException
	 */
	public void createCompany(Company company) throws DaoException;
	
	/**
	 * Adds a correlation between a Coupon and a Company in the underlying database (or any other persistence storage).
	 * @param company a {@code Company} object
	 * @param coupon a {@code Coupon} object
	 * @throws DAOException
	 */
	public Coupon addCoupon(Coupon coupon, Company company) throws DaoException;

	/**
	 * Remove a Company from the underlying database (or any other persistence storage).
	 * @param company a {@code Company} Object
	 * @throws DAOException
	 */
	public void removeCompany(Company company) throws DaoException;
	
	/**
	 * Updates a Company in the underlying database (or any other persistence storage), from a given {@code Company} object. The fields affected are Name, Email, Password and Salt.
	 * @param company a {@code Company} object
	 * @throws DAOException
	 */
	public void updateCompany(Company company) throws DaoException;
	
	/**
	 * This is a my add-on: For CompanyFacade. is an option for us to let the company ONE click for his details.</p>
	 * Returns a {@code Company} object from the underlying database (or any other persistence storage).
	 * @param company a {@code Company} object
	 * @return a {@code Company} object
	 * @throws DAOException
	 */
	public Company viewCompany(long compID) throws DaoException;
	
	/**
	 * Returns a {@code Company} object from the underlying database (or any other persistence storage).
	 * @param compID a {@code long} Company ID
	 * @return a {@code Company} object
	 * @throws DAOException
	 */
	public Company getCompany(long compID) throws DaoException;
	
	/**
	 * Returns a {@code Company} object from the underlying database (or any other persistence storage).
	 * @param compName a {@code String} Company Name
	 * @return a {@code Company} object
	 * @throws DAOException
	 */
	public Company getCompany(String compName) throws DaoException;

	/**
	 * Returns a {@code Collection<Company>} of all Companies from the underlying database (or any other persistence storage).
	 * @return a {@code Collection<Company>}
	 * @throws DAOException
	 */
	public Collection<Company> getAllCompanies() throws DaoException;
	

}
