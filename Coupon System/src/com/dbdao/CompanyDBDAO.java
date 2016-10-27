package com.dbdao;

import java.sql.*;
import java.util.*;

import com.beans.*;
import com.dao.interfaces.*;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.task.and.singleton.DBconnector;
import com.task.and.singleton.DatabaseInfo;


/**
 * This is Company Database DAO Class. (DBDAO in short)</br>
 * Just implement the methods from CompanyDAO interfaces. 
 * 
 * @author Raziel
 */

public class CompanyDBDAO implements CompanyDAO {

	// Attributes
	private CouponFoundInDatabase existInDB = new CouponFoundInDatabase(); 

	// Default Constructor.
	public CompanyDBDAO() {}
	
	@Override
	public boolean login(String compName, String password) throws DaoException  {
		
		boolean hasRows = false;
        try {
			String sqlLoginCompany = "SELECT Comp_name, password FROM " + DatabaseInfo.getDBname() + ".company WHERE "
					+ "Comp_name= '" + compName + "'" + " AND " + "password= '" 
					+ password + "'";
			Statement stat = DBconnector.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sqlLoginCompany);
			/*
			 * The logical thinking here- is if the resultSet has NO ROW to next
			 * (Because of the condition 'Pass' && 'name'). 
			 * so, it will be throw Login Exception and will not move on.
			 */
			rs.next();
			if(rs.getRow() == 0) {
	        	throw new DaoException("Error: Company Login - FAILD (one or more of the fields is incurrect or empty)");
		    }
			else {
				hasRows = true;
			}
            } catch (SQLException | NullPointerException e) {
            	/* The throw exception here is an general error, mostly sql error.
            	 * Login failed exception - because of incorrect user Or password will throw from the CompanyFacade.
            	 */
    			throw new DaoException("Error: Company Login - FAILED (something went wrong..)");
            } // catch
        	return hasRows;
        
        	} // login	
	
	@Override
	public void createCompany(Company company) throws DaoException{
		
		// check if the company not exist (is checking also if the fields are empty in the 'Company' javaBeans)
		if (compnayExistByName(company.getCompName()) == false) {
				try {
					String sqlQuery = "INSERT INTO " + DatabaseInfo.getDBname() + ".company (comp_name, password, email) VALUES(?,?,?)";
					PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
					
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
//					e.printStackTrace();
					if(e.getMessage().equals("Duplicate entry '" + company.getEmail() + "' for key 'email'")) {
						throw new DaoException("Error: Creating New Company - FAILED (Duplicate Email)");
					}
					throw new DaoException("Error: Creating New Company - FAILED (something went wrong)");
				} // catch
		} // if - existOrNotByName
		else {
			throw new DaoException("Error: Creating Company - FAILED (Company is already exist in the DataBase)");
		} // else
	} // createCompany
	
	@Override
	public Coupon addCoupon(Coupon coupon, Company company) throws DaoException{
		if(existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_NAME) == false 
				|| existInDB.couponFoundInJoinTables(coupon, company, CheckCouponBy.BY_ID) == false) {
			try{
				// Now insert the coupon to the Join Tables.
				String sqlAddCompanyCoupn = "INSERT INTO " + DatabaseInfo.getDBname() + ".company_coupon (Comp_id, Coup_id) VALUES (" + company.getId() + "," + coupon.getId() + ");";
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlAddCompanyCoupn);
				prep.executeUpdate();
			} // try
			catch (SQLException | NullPointerException e) {
				throw new DaoException("Error: Creating Coupon By Company- FAILED (something went wrong..)");
			} // catch
			return coupon;
		} // if - existInDB
		else {
			throw new DaoException("Error: Creating Coupon By Company - FAILED (You can create only ONE coupon with the same name!)");
		} // else
	} // createCoupon
	
	@Override
	public void removeCompanyByID(Company company) throws DaoException{
		// check if the company exist
		if (compnayExistByID(company.getId()) == true) {
			removeCompanyMethod(company.getId());
		} // if - Exist
		else {
			throw new DaoException("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else - Exist

	} // removeCompany - By ID 
	
	@Override
	public void removeCompanyByName(Company company) throws DaoException{
		// check if the company exist
		if (compnayExistByName(company.getCompName()) == true) {
			company = getCompany(company.getCompName());
			removeCompanyMethod(company.getId());
		}
		else {
			throw new DaoException("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else - Exist

	} // removeCompany - By ID 
	
	@Override
	public void updateCompany(Company company) throws DaoException{
		// check if the company exist
		if (compnayExistByID(company.getId()) == true) {
			try {
				System.out.println(company.toString());
				String sqlUpdate = "UPDATE " + DatabaseInfo.getDBname() + ".company SET  password=?, email=? WHERE Comp_ID=?";
				PreparedStatement prep = DBconnector.getConnection().prepareStatement (sqlUpdate);
				prep.setString(1, company.getPassword());
				prep.setString(2, company.getEmail());
				prep.setLong(3, company.getId());
				
				prep.executeUpdate();
				
				System.out.println(company.toString());
				prep.clearBatch();
				
				} catch (SQLException e) {
					throw new DaoException("Error: Updating Company - FAILED (somthing went wrong)");
				} // catch
		} // if
		else {
			throw new DaoException("Error: Updating Company - FAILED (Company is not exist in the DataBase)");
		} // else
	
	} // updateCompany
	
	public Company viewCompany(long id) throws DaoException {

		Company company = getCompany(id);
		if(company.getId() == id) {
			return company;
		} // if
		else {
			throw new DaoException("Error: Getting Company - FAILED (Company dosen't exist in the DataBase)");
		} // else
	} // viewCompany
	
	@Override
	public Company getCompany(long id) throws DaoException{
		
		// check if the company exist
		if (compnayExistByID(id) == true) {
			
			Company company = new Company();
			String compName = null, email = null, password = null;
			
			try {

				String sqlSEL = "SELECT * FROM " + DatabaseInfo.getDBname() + ".company WHERE Comp_ID= ?" ;
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSEL);
				prep.setLong(1, id);
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
				compName = rs.getString("Comp_name");
				email = rs.getString("Email");
				password = rs.getString("password");
				} // while

				company = new Company(id, compName, password, email);
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Getting Company By ID - FAILED");
			} // catch
			return company;
		} // if
		else {
			throw new DaoException("Error: Getting Company - FAILED (Company dosen't exist in the DataBase)");
		} // else 
	} // getCompany - BY ID

	@Override
    public Company getCompany(String compName) throws DaoException{
		
		Company company = new Company();
			try {
				// check if the company exist
				
				if (compnayExistByName(compName) == true) {
					String email, name, password;
					long id;
				
				String sqlSEL = "SELECT * FROM " + DatabaseInfo.getDBname() + ".company WHERE comp_name= ?";
				PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSEL);
				prep.setString(1, compName);
				ResultSet rs = prep.executeQuery();
				rs.next();
				id = rs.getLong("comp_id");
				name = rs.getString("comp_name");
				email = rs.getString("Email");
				password = rs.getString("password");
				company = new Company(id, name, password, email);
				} // if
				else {
					throw new DaoException("Error: Getting Company - FAILED (Company is not exist in the DataBase)");
				} // else
			} catch (SQLException | FiledErrorException e) {
				throw new DaoException("Error: Getting Company By ID - FAILED");
			} // catch
			return company;
	} // getCompany - BY Name

	@Override
	public Collection<Company> getAllCompanies() throws DaoException{
		
		String sql = "SELECT * FROM " + DatabaseInfo.getDBname() + ".company";
		Collection<Company> companies = new HashSet<>();
		Company company = null;
		ResultSet rs = null;
		
		try {
			
			Statement stat = DBconnector.getConnection().createStatement();
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				
				company = new Company(
						rs.getLong("Comp_ID"),
						rs.getString("Comp_name"),
						rs.getString("password"),
						rs.getString("email")
						);

				companies.add(company);
			} // while loop

		} catch (SQLException | FiledErrorException e) {
			throw new DaoException("Error: Getting all Companies - FAILED");
		} // catch

		return companies;
	} // getAllCompanies

	@Override	
	public Collection<Coupon> getCoupons(long compID) throws DaoException {
		Collection<Coupon> coupons = new HashSet<>(); 
		try {
			String sql = "SELECT * FROM " + DatabaseInfo.getDBname() + ".coupon WHERE owner_ID=" + compID;
			Statement stat = DBconnector.getConnection().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				
				Coupon coupon = new Coupon();
				
				coupon.setId(rs.getLong("coup_id"));
				coupon.setTitle(rs.getString("Title"));
				coupon.setStartDate(rs.getDate("start_date").toLocalDate());
				coupon.setEndDate(rs.getDate("end_date").toLocalDate());
				coupon.setAmount(rs.getInt("amount"));
				coupon.setCategory(rs.getString("Category"));
				coupon.setMessage(rs.getString("Message"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setImage(rs.getString("image"));
				coupon.setOwnerID(rs.getLong("owner_ID"));

				// adding the current coupon to the collection
				coupons.add(coupon);
			}// while
		} catch (SQLException | FiledErrorException e) {
			throw new DaoException("Error: Getting Coupons of Company - FAILED (something went wrong)");
		} // catch
		if(!coupons.isEmpty()) {
			return coupons;
		} // if
		else {
			throw new DaoException("Error: No Coupons Found");
		}  // else - Set<> is empty
	} // getAllCouponsOfCompany
	
	/**
	 * This is my Private add-on for this class.</br>
	 * Remove Method </p> 
	 * Here is the game plan:
	 * First, when the function will execute the 'sqlCheckExist' {@code String}. </br>
	 * It will check if the Company has (or not) 
	 * coupons in 'company_coupon' Table.</p>
	 * 
	 * If Yes - it will execute 'sqlDeleteALL' {@code String} and delete it from 
	 * the TABLES:
	 * Company, Company_Coupon, Coupon. </br>
	 * 
	 * If No - (Company DOSEN'T have coupons..) it will delete it only from
	 * the company Table (execute the 'sqlOnlyFromCompany' {@code String} command).
	 * 
	 * @param compID {@code long} Company ID.
	 * @throws DaoException
	 */
	private void removeCompanyMethod(long compID) throws DaoException{
		
		boolean hasRow = false;
		PreparedStatement prep = null;
		ResultSet rs = null;
		// Check if the company has any coupons BEFORE Deleting the company.
		String sqlCheckExist = "SELECT * FROM " + DatabaseInfo.getDBname() + ".company_coupon WHERE Comp_ID=" + compID;
		try {
    		prep = DBconnector.getConnection().prepareStatement(sqlCheckExist);
    		rs = prep.executeQuery();
    		while(rs.next()) {
    			// if we have rows in Company_coupon, make hasRow = true.
    			hasRow = true;
    		} // while

			prep.clearBatch();
			
    		if(hasRow == true) {
    			String sqlDeleteALL = "DELETE " 
    					+ DatabaseInfo.getDBname() + ".coupon.*, "
    					+ DatabaseInfo.getDBname() + ".company.*, "
    					+ DatabaseInfo.getDBname() + ".company_coupon.*"
						+ " FROM " + DatabaseInfo.getDBname() + ".company_coupon"
						+ " LEFT JOIN " + DatabaseInfo.getDBname() + ".coupon USING (coup_id)"
						+ " LEFT JOIN " + DatabaseInfo.getDBname() + ".company USING (comp_id)"
						+ " WHERE " + DatabaseInfo.getDBname() + ".company_coupon.Comp_ID=" + compID
						+ " AND " + DatabaseInfo.getDBname() + ".company_coupon.Comp_ID IS NOT NULL";
				prep = DBconnector.getConnection().prepareStatement(sqlDeleteALL);
				prep.executeUpdate();
				prep.clearBatch();
				
    		} // if - hasRow		
			else {

				String sqlOnlyFromCompany = "DELETE FROM " + DatabaseInfo.getDBname() + ".company WHERE Comp_ID=" + compID;
				PreparedStatement prep2 = DBconnector.getConnection().prepareStatement(sqlOnlyFromCompany);
				prep2.executeUpdate();
				prep2.clearBatch();
			} // else - hasRow

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error: Removing Company - FAILED (something went wrong..)");
		} // catch
		
	} // removeMethod
	
	/**
	 * This is a my add-on: CompanyDBDAO. This method checks if the Company {@code ID} exist in the Database.
	 * @param compID a {@code long} Company {@code ID}.
	 * @return {@code true} if the Company {@code ID} exist in Database, otherwise {@code false}
	 * @throws DaoException
	 */
    private boolean compnayExistByID(long compID) throws DaoException {
    	
    	boolean answer = false;
    	if(compID > 0) {
    		
    		try {
        		String sqlName = "SELECT Comp_ID FROM " + DatabaseInfo.getDBname() + ".company WHERE "
        		+ "Comp_ID= " + "'" + compID + "'";
        		
        		Statement stat = DBconnector.getConnection().createStatement();
        		ResultSet rs = stat.executeQuery(sqlName);
    			rs.next();
    	    					   
    			if (rs.getRow() != 0) {
    				answer = true;
    			} // if
    			
    			stat.clearBatch();
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    			throw new DaoException("Error: cannot make sure if the company is in the DataBase");
    		} // catch
    		return answer;
    	} // if - compID
    	throw new DaoException("Error: Confirming CompanyID - FAILED (ID cannot contain or been under Zero!)");
    	} // compnayExistByID
    
    /**
	 * This is a my add-on: CompanyDBDAO. This method checks if the Company compName exist in the Database.
	 * @param compName a {@code String} Company {@code compName}.
	 * @return {@code true} if the Company {@code compName} exist in Database, otherwise {@code false}
	 * @throws DaoException
	 */
    private boolean compnayExistByName(String compName) throws DaoException {
		
 	    Statement stat = null;
 		ResultSet rs = null;
 		boolean answer = false;
 		
 		  try {
				String sqlName = "SELECT Comp_name FROM " + DatabaseInfo.getDBname() + ".company WHERE "
				+ "comp_name= '" + compName + "'";
				stat = DBconnector.getConnection().createStatement();
				rs = stat.executeQuery(sqlName);
				rs.next();
 			   
 				if (rs.getRow() != 0) {
 					answer = true;
 				} // if
 	            } catch (SQLException e) {
 	 	   			throw new DaoException("Error: cannot make sure if the company is in the DataBase");
 	            } // catch
 		  return answer;
 	} // compnayExistByName
} // CompanyDBDAO
