package com.dao.interfaces;

import java.sql.SQLException;
import java.util.*;
import com.javabeans.*;

public interface CompanyDAO {

	public void createCompany(Company company) throws SQLException;

	public void removeCompany(Company company) throws SQLException;

	public void updateCompany(Company company) throws SQLException;

	public Company getCompany(long id) throws SQLException;

	public Collection<Company> getAllCompanies() throws SQLException;

	public Collection<Coupon> getCoupons(long compID) throws SQLException;

	public boolean login(String compName, String password);

}
