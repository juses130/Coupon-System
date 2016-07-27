package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.util.Collection;


import java.util.*;

import com.added.functions.DBconnectorV3;
import com.dao.interfaces.*;
import com.exeptionerrors.*;
import com.facade.ClientType;
import com.javabeans.*;

/**
 * This is Company Database DAO Class. (DBDAO in short)</p>
 * Just implement the methods from CompanyDAO interfaces. 
 * 
 * @author Raziel
 *
 */

public class CompanyDBDAO implements CompanyDAO {
	
	// Default Constructor.
	public CompanyDBDAO() {}
	
	@Override
	public boolean login(String compName, String password) throws DaoExeption  {
		
		boolean hasRows = false;
        try {
			String sqlLoginCompany = "SELECT Comp_name, password FROM company WHERE "
					+ "Comp_name= '" + compName + "'" + " AND " + "password= '" 
					+ password + "'";
			Statement stat = DBconnectorV3.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sqlLoginCompany);
		    
			/*
			 * The logical thinking here- is if the resultSet has NO ROW to next
			 * (Because of the condition 'Pass' && 'name'). 
			 * so, it will be throw Login Exception and will not move on.
			 */
			
			rs.next();
			if(rs.getRow() == 0) {
	        	throw new DaoExeption("Error: Company Login - FAILD (one or more of the fields is incurrect or empty)");
		    }
			else {
				hasRows = true;
			}
            } catch (SQLException | NullPointerException e) {
            	/* The throw exception here is an general error, mostly sql error.
            	 * Login failed exception - because of incurrent user Or pasword will throw from the CompanyFacade.
            	 */
    			throw new DaoExeption("Error: Company Login - FAILED (something went wrong..)");
            } // catch
        	return hasRows;
        
        	} // login	
	
	@Override
	public void createCompany(Company company) throws DaoExeption{
		
		// check if the company not exist (is checking also if the fields are empty in the 'Company' javaBeans)
		if (compnayExistByName(company.getCompName()) == false) {
				try {
					String sqlQuery = "INSERT INTO company (COMP_NAME, PASSWORD, EMAIL) VALUES(?,?,?)";
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
					
					prep.setString(1, company.getCompName());
					prep.setString(2, company.getPassword());
					prep.setString(3, company.getEmail());
				
					prep.executeUpdate();
					
					ResultSet rs = prep.getGeneratedKeys();
					while(rs.next()) {
					company.setId(rs.getLong(1));
					} // while
				} // try 
				catch (SQLException e) {
					throw new DaoExeption("Error: Creating New Company - FAILED (something went wrong)");
				} // catch
		} // if - existOrNotByName
		else {
			throw new DaoExeption("Error: Creating Company - FAILED (Company is already exist in the DataBase)");
		} // else
				
		
	} // createCompany - Function
	
	@Override
	public Coupon addCoupon(Coupon coupon, Company company) throws DaoExeption{
		
				try{
					// Now insert the coupon to the Join Tables.
					String sqlAddCompanyCoupn = "INSERT INTO Company_coupon (Comp_id, Coup_id) VALUES (" + company.getId() + "," + coupon.getId() + ");";
					PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlAddCompanyCoupn);
					prep.executeUpdate();
					

				} // try
				catch (SQLException | NullPointerException e) {
					throw new DaoExeption("Error: Creating Coupon By Company- FAILED (something went wrong..)");
				} // catch
				return coupon;

	} // createCoupon - function
	
	@Override
	public void removeCompany(Company company) throws DaoExeption{
		// check if the company exist
		if (compnayExistByID(company.getId()) == true) {
			removeCompanyMethod(company.getId());
		} // if - Exist
		else {
			throw new DaoExeption("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else - Exist

	} // removeCompany - By ID - Function
	
	@Override
	public void updateCompany(Company company) throws DaoExeption{
		
		// check if the company exist
		if (compnayExistByID(company.getId()) == true) {
			try {
				String sqlUpdate = "UPDATE company SET Comp_name=?, password=?, email=? WHERE Comp_ID=?";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement (sqlUpdate);
				prep.setString(1, company.getCompName());
				prep.setString(2, company.getPassword());
				prep.setString(3, company.getEmail());
				prep.setLong(4, company.getId());
				
				prep.executeUpdate();
			    
				} catch (SQLException e) {
					throw new DaoExeption("Error: Updating Company - FAILED");
				}
		}
		else {
			throw new DaoExeption("Error: Updating Company - FAILED (Company is not exist in the DataBase)");
		} // else
	
	} // updateCompany - Function
	
	@Override
	public Company getCompany(long id) throws DaoExeption{
		
		// check if the company exist
		if (compnayExistByID(id) == true) {
			
			Company company = new Company();
			String compName = null, email = null, password = null;
			
			try {

				String sqlSEL = "SELECT * FROM company WHERE Comp_ID= ?" ;
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setLong(1, id);
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
				compName = rs.getString("Comp_name");
				email = rs.getString("Email");
				password = rs.getString("password");
				} // while

				company = new Company(id, compName, password, email);
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Getting Company By ID - FAILED");
			}
			return company;
			
		}
		else {
			throw new DaoExeption("Error: Getting Company - FAILED (Company dosen't exist in the DataBase)");
		} // else
		
		
		
	} // getCompany - Function

	@Override
    public Company getCompany(String compName) throws DaoExeption{
		
		Company company = new Company();
		
			try {
				// check if the company exist
				
				if (compnayExistByName(compName) == true) {
					String email, password;
					long id;
				
				String sqlSEL = "SELECT * FROM company WHERE comp_name= ?";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setString(1, compName);
				ResultSet rs = prep.executeQuery();
				rs.next();
				id = rs.getLong("comp_id");
				email = rs.getString("Email");
				password = rs.getString("password");
				
				company = new Company(id, compName, password, email);
				}
				else {
					throw new DaoExeption("Error: Getting Company - FAILED (Company is not exist in the DataBase)");
				} // else
			}
			catch (SQLException | FiledErrorException e) {
				throw new DaoExeption("Error: Getting Company By ID - FAILED");
			}
			return company;
		
		
		
	}

	public Company viewCompany(long id) throws DaoExeption {

		Company company = getCompany(id);
		if(company.getId() == id) {
			return company;
		} // if
		else {
			throw new DaoExeption("Error: Getting Company - FAILED (Company dosen't exist in the DataBase)");
		} // else
	} // viewCompany

	@Override
	public Collection<Company> getAllCompanies() throws DaoExeption{
		
		String sql = "SELECT * FROM company";
		Collection<Company> companies = new HashSet<>();
		Company company = null;
		ResultSet rs = null;
		
		try {
			
			Statement stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				company = new Company();
				company.setId(rs.getLong("Comp_ID"));
				company.setCompName(rs.getString("Comp_name"));
				company.setPassword(rs.getString("password"));
				company.setEmail(rs.getString("email"));
				
				companies.add(company);
			} // while loop

		} catch (SQLException | FiledErrorException e) {
			throw new DaoExeption("Error: Getting all Companies - FAILED");
		} // catch

		return companies;
	} // getAllCompanies

	private void removeCompanyMethod(long id) throws DaoExeption{
		
		/*
		 * Explain - here is the game plan:
		 * First, when the function will execute 'sqlCheckExist'.
		 * we are checking that the Company has (or not) 
		 * coupons in 'company_coupon Table.
		 * If Yes - we execute 'sqlDeleteALL' and delete it from 
		 * the TABLES:
		 * Company, Company_Coupon, Coupon. 
		 * 
		 * If No - (Company DOSEN'T have coupons..) We will delete it only from
		 * the company Table (execute the 'sqlOnlyFromCompany' command).
		 */
		
		boolean hasRow = false;
		PreparedStatement prep = null;
		ResultSet rs = null;
		// Check if the company has coupons BEFORE Deleting the company.
		String sqlCheckExist = "SELECT * FROM company_coupon WHERE Comp_ID=" + id;
		try {
    		prep = DBconnectorV3.getConnection().prepareStatement(sqlCheckExist);
    		rs = prep.executeQuery();
    		while(rs.next()) {
    			// if we have rows in Company_coupon, make hasRow = true.
    			hasRow = true;
    		}
    		
			prep.clearBatch();
			
    		if(hasRow == true) {

    			String sqlDeleteALL = "DELETE coupon.*, company.*, company_coupon.*"
						+ " FROM company_coupon"
						+ " LEFT JOIN coupon USING (coup_id)"
						+ " LEFT JOIN company USING (comp_id)"
						+ " WHERE company_coupon.Comp_ID=" + id
						+ " AND company_coupon.Comp_ID IS NOT NULL";
				prep = DBconnectorV3.getConnection().prepareStatement(sqlDeleteALL);
				prep.executeUpdate();
				prep.clearBatch();
				
    		} // if - hasRow		
			else {

				String sqlOnlyFromCompany = "DELETE FROM company WHERE Comp_ID=" + id;
				PreparedStatement prep2 = DBconnectorV3.getConnection().prepareStatement(sqlOnlyFromCompany);
				prep2.executeUpdate();
				prep2.clearBatch();
			} // else - hasRow

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoExeption("Error: Removing Company - FAILED (something went wrong..)");
		} // catch
		
	} // removeMethod
	
    private boolean compnayExistByID(long compID) throws DaoExeption {
    	
    	boolean answer = false;
    	if(compID > 0) {
    		
    		try {
        		String sqlName = "SELECT Comp_ID FROM company WHERE "
        		+ "Comp_ID= " + "'" + compID + "'";
        		
        		Statement stat = DBconnectorV3.getConnection().createStatement();
        		ResultSet rs = stat.executeQuery(sqlName);
    			rs.next();
    	    					   
    			if (rs.getRow() != 0) {
    				answer = true;
    			} // if
    			
    			stat.clearBatch();
    			
    		} catch (SQLException e) {
    			throw new DaoExeption("Error: cannot make sure if the company is in the DataBase");
    		} // catch
    		return answer;
    	} // if - compnay
    	throw new DaoExeption("Error: Confirming CompanyID - FAILED (ID cannot contain Zero!)");
    	} // compnayExistByID
    
    private boolean compnayExistByName(String compName) throws DaoExeption {
		
 	    Statement stat = null;
 		ResultSet rs = null;
 		boolean answer = false;
 		   
 		  try {
 				String sqlName = "SELECT Comp_name FROM company WHERE "
 				+ "comp_name= '" + compName + "'";
 				stat = DBconnectorV3.getConnection().createStatement();
 				rs = stat.executeQuery(sqlName);
 				rs.next();
 			   
 				if (rs.getRow() != 0) {
 					answer = true;
 				} // if
 	            } catch (SQLException e) {
 	 	   			throw new DaoExeption("Error: cannot make sure if the company is in the DataBase");
 	            } // catch
 		  return answer;
 	}



	

    
    
}
