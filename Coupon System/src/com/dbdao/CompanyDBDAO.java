package com.dbdao;

import java.sql.*;
import java.util.Collection;
import java.util.*;

import com.added.functions.DBconnector;
import com.added.functions.DBconnectorV2;
import com.added.functions.DBconnectorV3;
import com.added.functions.SharingData;
import com.dao.interfaces.*;
import com.javabeans.*;

/**
 * This is Company Database DAO Class.
 * Just impelemnts the methods from CompanyDAO in 'com.dao.interfaces' package. 
 * 
 * 
 * @author Raziel
 *
 */

public class CompanyDBDAO implements CompanyDAO {
	
	// Default Constructor.
	public CompanyDBDAO() {}
	
	@Override
	public void createCompany(Company company) {
		
		// creating ResultSet
		ResultSet rs = null;
		
		try {
			
			
			String sqlQuery = "INSERT INTO company (COMP_NAME, PASSWORD, EMAIL) VALUES(?,?,?)";
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			
			// now we will put the in their places.
			prep.setString(1, company.getCompName());
			prep.setString(2, company.getPassword());
			prep.setString(3, company.getEmail());
		
			// after we "loaded" the columns, we will executeUpdate prep.
			prep.executeUpdate();

			// This 2 lines will make it run to the next null row. and line 3 will set the ID (next new row).
			rs = prep.getGeneratedKeys();
			rs.next();
			company.setId(rs.getLong(1));
			
			// Letting the others (if the asking) that the Company Added Succsefully.
			SharingData.setFlag1(true);
			String tostring = company.toString();
			SharingData.setVarchar4(tostring);

			
		} // try 
		catch (SQLException e) {
			SharingData.setFlag1(false);
			SharingData.setExeptionMessage(e.getMessage());
		
		} // catch
	} // createCompany - Function
	
	@Override
	public void removeCompany(Company company) throws SQLException{
		
		long id = company.getId();
		
		removeMethod(id, "company");
		removeMethod(id, "company_coupon");
		
	} // removeCompany - By ID - Function
	
	@Override
	public void updateCompany(Company company) {

		try {
			
			
			
			String sqlUpdate = "UPDATE company SET Comp_name=?, password=?, email=? WHERE Comp_ID=?";
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement (sqlUpdate);
			prep.setString(1, company.getCompName());
			prep.setString(2, company.getPassword());
			prep.setString(3, company.getEmail());
			prep.setLong(4, company.getId());
			
			prep.executeUpdate();
			
		    // Letting the others (if the asking) that the Company update Succsefully.
		    SharingData.setFlag1(true);
		    
			} catch (SQLException e) {
				SharingData.setExeptionMessage(e.getMessage());
			}
				

		
	} // updateCompany - Function
	
	@Override
	public Company getCompany(long id) {
		
		Company c = new Company();
		String compName, email, password;
		
		try {

			String sqlSEL = "SELECT * FROM company WHERE Comp_ID= ?" ;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			ResultSet rs = prep.executeQuery();
			rs.next();
			compName = rs.getString("Comp_name");
			email = rs.getString("Email");
			password = rs.getString("password");
			

			c = new Company(id, compName, password, email);

			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			
		}
		catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		}
		return c;
		
		
	} // getCompany - Function

	@Override
	public Collection<Company> getAllCompanies() {
		
		String sql = "SELECT * FROM company";
		Collection<Company> companies = new HashSet<>();
		Company c = null;
		ResultSet rs = null;
		
		try {
			
			Statement stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				c = new Company();
				c.setId(rs.getLong("Comp_ID"));
				c.setCompName(rs.getString("Comp_name"));
				c.setPassword(rs.getString("password"));
				c.setEmail(rs.getString("email"));
				
				companies.add(c);
			} // while loop

		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		} // catch
		
		
		return companies;
	} // getAllCompanies

	@Override
	public Collection<Coupon> getCoupons(long compID) {
		 
		// We can test it in some simple tests (like shortTest Class). NOT in the testDevelopers Class.
		
		Set<Coupon> coupons = new HashSet<>();
		CouponDBDAO  couponDB = new CouponDBDAO();
		
		try {
			
			String sql = "SELECT Coup_ID FROM coupon WHERE Owner_ID=?";
			PreparedStatement stat = DBconnectorV3.getConnection().prepareStatement (sql);
			stat.setLong(1, compID);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				coupons.add(couponDB.getCoupon(rs.getLong("Coup_ID")));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupons;
	}

	@Override
	public boolean login(String compName, String password) {
		
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Statement stat1 = null;
		Statement stat2 = null;

		boolean hasRows = false;
        try {
			
			
			String sqlName = "SELECT Comp_name, password FROM company WHERE "
					+ "Comp_name= '" + compName + "'" + " AND " + "password= '" 
					+ password + "'";
			stat1 = DBconnectorV3.getConnection().createStatement();
		    rs1 = stat1.executeQuery(sqlName);
		    rs1.next();

			if (rs1.getRow() != 0) {
				
				// In case we have the row, we'll return 'true' and set the Company Name to some shared variable.
				// we need this ID for putting it in other TABLES such as Company_Coupon.
				hasRows = true;
				String sqlGetID = "SELECT Comp_id from company WHERE Comp_name='" + compName + "'";
				//System.out.println(hasRows);
				stat2 = DBconnectorV3.getConnection().createStatement();
				rs2 = stat2.executeQuery(sqlGetID);
				rs2.next();
				SharingData.setIdsShare(rs2.getLong(1));
//				stat2.clearBatch();

				
			}

            } catch (SQLException e) {
            	SharingData.setExeptionMessage(e.getMessage());
	        
            } // catch
        
	   return hasRows;
	}	
	
	/**
	 * 
	 * This is a remove method - making the deleting of company more flexible and easy.
	 * It's my add on.
	 * 
	 * @param long id
	 * @param String table
	 * 
	 * @author Raziel
	 */
	
	private void removeMethod(long id, String table) {
		
		try {
			
			//String compName, email, password;
			
			String sqlDELid = "DELETE FROM " + table + " WHERE Comp_ID =" + id ;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid);
			prep.executeUpdate();
			
		}
		catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
			}
		
	} // removeMethod

}
