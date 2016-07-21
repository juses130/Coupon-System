package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.util.Collection;


import java.util.*;

import com.added.functions.DBconnectorV3;
import com.dao.interfaces.*;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
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
		    rs.next();
		    
			if (rs.getRow() != 0) {
				hasRows = true;
			}

            } catch (SQLException | NullPointerException e) {
            	/* The throw exception here is an general error, mostly sql error.
            	 * Login failed exception - because of incurrent user Or pasword will throw from the CompanyFacade.
            	 */
    			throw new DaoExeption("Error: Company Login - FAILED (something went wrong..)");
            } // catch
        
        if(hasRows == true) {
        	return hasRows;
        }
        return hasRows;
        	} // login	
	
	@Override
	public void createCompany(Company company) throws DaoExeption{
		
		// check if the company not exist && if the instances are not empty
		if (existOrNotByName(company) == false) {
			if(company.getCompName().isEmpty() || company.getEmail().isEmpty() || company.getPassword().isEmpty()) {
				throw new DaoExeption("Error: Creating Company - FAILED (empty fields)");
			} // if - empty
			else {
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
					ResultSet rs = prep.getGeneratedKeys();
					while(rs.next()) {
					company.setId(rs.getLong(1));
					} // while
				} // try 
				catch (SQLException e) {
					throw new DaoExeption("Error: Creating New Company - FAILED (something went wrong)");
				} // catch
			} // else - empty
		} // if - existOrNotByName
		else {
			throw new DaoExeption("Error: Creating Company - FAILED (Company is already exist in the DataBase)");
		} // else
				
		
	} // createCompany - Function
	
	@Override
	public Coupon createCoupon(Company company ,Coupon coupon) throws DaoExeption{

		if(existOrNotByCoupName(coupon) == false) {

			// We need to Check if the Company ownes this coupon before.
			if(bothExistInSameTable(coupon, company, SqlAction.CREATE_COUPON) == false) {
				try{
					/* creating the coupon in Coupon Table First. 
					 * and if we don't get Exception it will move on to 
					 * create the coupon in the Company_Coupon Table.
					*/
					CouponDBDAO coupDB = new CouponDBDAO();
					coupDB.createCoupon(coupon);
					
						String sqlCompanyCoupn = "INSERT INTO Company_coupon (Comp_id, Coup_id) VALUES (" + company.getId() + "," + coupon.getId() + ")";
						PreparedStatement prep1 = DBconnectorV3.getConnection().prepareStatement(sqlCompanyCoupn);
						prep1.executeUpdate();

				} // try
				catch (SQLException | NullPointerException e) {
					throw new DaoExeption("Error: Creating Coupon By Company- FAILED");			
				} // catch
				return coupon;
			} // if - it's bought before
			else {
				throw new DaoExeption("Error: Creating Coupon By Company - FAILED (You can create only ONE coupon with the same name!)");
			} // else - it's bought before

		} // if - exist
		else {
			throw new DaoExeption("Error: Creating Coupon By Company - FAILED (Coupon already exist in the DataBase)");
		}

	} // createCoupon - function
	
	@Override
	public void removeCompany(Company company) throws DaoExeption{
		// check if the company exist
		if (existOrNotByCompID(company.getId()) == true) {
			removeMethod(company.getId());
		}
		else {
			throw new DaoExeption("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else

	} // removeCompany - By ID - Function
	
	@Override
	public void updateCompany(Company company) throws DaoExeption{
		
		// check if the company exist
		if (existOrNotByCompID(company.getId()) == true) {
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
		if (existOrNotByCompID(id) == true) {
			
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
	public Company getCompanyByCoupon(Coupon coupon, Company company) throws DaoExeption {
		getCompanyByCouponMethod(coupon, company);
		return company;
	}
	
	@Override
    public Company getCompany(String compName) throws DaoExeption{
		
		Company company = new Company();
		
			try {
				// check if the company exist
				company.setCompName(compName);
				if (existOrNotByName(company) == true) {
					String email, password;
					long id;
				
				String sqlSEL = "SELECT * FROM company WHERE comp_name= ?";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setString(1, company.getCompName());
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

	@Override
	public Collection<Company> getAllCompanies() throws DaoExeption{
		
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

		} catch (SQLException | FiledErrorException e) {
			throw new DaoExeption("Error: Getting all Companies - FAILED");
		} // catch

		return companies;
	} // getAllCompanies

	@Override
	public Collection<Coupon> getCoupons(long compID) throws DaoExeption{
		// TODO: security brich = every connected company can get all the coupon from others..

		Collection<Coupon> coupons = new HashSet<>();
		CouponDBDAO  couponDB = new CouponDBDAO();
		
		coupons = couponDB.getAllCoupons(compID, ClientType.COMPANY);
		return coupons;
	}
	
	/* Here it's 3 pirvate methods. my add on to this class.
	*  the Two last methods 'exist' - can be in some public class.
	*  but all the work, like connection to database, and checks, and exception.. 
	*  all goes from here. from the DBDAO.
	*  so consequently I decieded to put this 3 methods here as private. in all the DBDAO (3 classes)
	*  and adjust this methods to the currently DBDAO class.  
	*/
	
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
	private void removeMethod(long id) throws DaoExeption{
		//String compName, email, password;
		
		String sqlDELid1 = "DELETE FROM company WHERE Comp_ID =" + id;
		String sqlDELid2 = "DELETE FROM company_coupon WHERE Comp_ID =" + id;

		PreparedStatement prep;
		try {
			prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid1);
			prep.executeUpdate();
			prep.clearBatch();
			prep = DBconnectorV3.getConnection().prepareStatement(sqlDELid2);
			prep.executeUpdate();
		} catch (SQLException e) {
			throw new DaoExeption("Error: Removing Company - FAILED");
		}
		
	} // removeMethod
	
    private boolean existOrNotByCompID(long compID) throws DaoExeption {
    	
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
    		} catch (SQLException e) {
    			throw new DaoExeption("Error: cannot make sure if the company is in the DataBase");
    		}
    		return answer;
    	} // if - compnay
    	throw new DaoExeption("Error: Confirming CompanyID - FAILED (ID cannot contain Zero!)");
    	}
    
    private boolean existOrNotByCoupID(long coupID) throws DaoExeption {
    	
    	boolean answer = false;
    	if (coupID > 0) {
    		try {
        		
        		String sqlName = "SELECT coup_ID FROM coupon WHERE "
        		+ "coup_ID= " + "'" + coupID + "'";
        		
        		Statement stat = DBconnectorV3.getConnection().createStatement();
        		ResultSet rs = stat.executeQuery(sqlName);
    			rs.next();
    					   
    			if (rs.getRow() != 0) {
    				answer = true;
    			} // if
    		} catch (SQLException e) {
    			throw new DaoExeption("Error: cannot make sure if the coupon is in the DataBase");
    		}
    		return answer;
    	}
    	throw new DaoExeption("Error: Confirming CouponID - FAILED (ID cannot contain Zero!)");
    	}
  
    private boolean bothExistInSameTable(Coupon coupon, Company company, SqlAction action) throws DaoExeption {
    	boolean answer = false;

    	if(action == SqlAction.CREATE_COUPON) {
    		if (company.getId() > 0 && coupon.getId() == 0) {
        		try {
            		
            		String sqlName = "SELECT Comp_ID FROM company_coupon WHERE "
            		+ "Comp_ID= " + "'" + company.getId() + "' AND coup_id='" + coupon.getId() + "'";
            		
            		Statement stat = DBconnectorV3.getConnection().createStatement();
            		ResultSet rs = stat.executeQuery(sqlName);
        			rs.next();
        					   
        			if (rs.getRow() != 0) {
        				answer = true;
        			} // if
        		} catch (SQLException e) {
        			throw new DaoExeption("Error: cannot make sure if the company is in the DataBase");
        		}
        		return answer;
        	}
        	throw new DaoExeption("Error: Confirming CouponID or CompanyID - FAILED (IDs cannot contain Zero!)");
        	
    	} // if - CREATE_COUPON
    	else if (action == SqlAction.REMOVE_COUPON) {
    		
    	}
    	else if (action == SqlAction.UPDATE_COUPON) {
    		
    	}
    	return answer;
    } // bothExistInSameTable
   
    private boolean existOrNotByName(Company company) throws DaoExeption {
		
 	    Statement stat = null;
 		ResultSet rs = null;
 		boolean answer = false;
 		   
 		  try {
 				String sqlName = "SELECT Comp_name FROM company WHERE "
 				+ "comp_name= '" + company.getCompName() + "'";
 				stat = DBconnectorV3.getConnection().createStatement();
 				rs = stat.executeQuery(sqlName);
 				rs.next();
 			   
 				if (rs.getRow() != 0) {
 					answer = true;
 				} // if
 	            } catch (SQLException e) {
 	 	   			throw new DaoExeption("Error: cannot make sure if the company is in the DataBase");
// 		        e.printStackTrace();
 	            } // catch
 		  return answer;
 	}
	
    private Coupon createCouponByCompany(Coupon coupon) throws DaoExeption{
		
		long id = -1;
		
		// creating ResultSet
		ResultSet rs = null;
		
		// check if the company exist
		if (existOrNotByCompID(coupon.getId()) == true) {
			try {
				String sqlQuery = "INSERT INTO coupon (Title, Start_Date, End_Date, " + 
				"Amount, Category, Message, Price, Image, Owner_ID)" + "VALUES(?,?,?,?,?,?,?,?,?)";	
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
				prep.setString(1, coupon.getTitle());
				prep.setDate(2, Date.valueOf(coupon.getStartDate()));
				prep.setDate(3, Date.valueOf(coupon.getEndDate()));
				prep.setInt(4, coupon.getAmount());
				prep.setString(5, coupon.getType().toString());
				prep.setString(6, coupon.getMessage());
				prep.setDouble(7, coupon.getPrice());
				prep.setString(8, coupon.getImage());
				prep.setLong(9, coupon.getOwnerID());
				
				prep.executeUpdate();
				rs = prep.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				coupon.setId(id);
				
				// 2. Adding the new CouponID to the COMPANY_COUPON TABLE.
				
				long compID = coupon.getOwnerID();
				String sqlQuery1 = "INSERT INTO company_coupon (Comp_ID ,Coup_ID) VALUES ("+ compID +  
					"," + coupon.getId() + ");";
				PreparedStatement prep1 = DBconnectorV3.getConnection().prepareStatement(sqlQuery1);
				prep1.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch

			return coupon;
		}
		else {
				throw new DaoExeption("Error: Removing Company - FAILED (Company is not exist in the DataBase)");
		} // else
		
	}

    private Company getCompanyByCouponMethod(Coupon coupon, Company company) throws DaoExeption {
		
			try {
				// check if the company exist
				String compName = company.getCompName();
				if (existOrNotByName(company) == true) {
					String email, password;
					long id;
				
				String sqlSEL = "SELECT * FROM company WHERE comp_name= ?";
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setString(1, company.getCompName());
				ResultSet rs = prep.executeQuery();
				rs.next();
				id = rs.getLong("comp_id");
				email = rs.getString("Email");
				password = rs.getString("password");
				
				company = new Company(id, compName, password, email);
				}
				else {
					throw new DaoExeption("Error: Getting Company By Name - FAILED (Company is not exist in the DataBase)");
				} // else
			}
			catch (SQLException | FiledErrorException | DaoExeption e) {
				throw new DaoExeption("Error: Getting Company By ID - FAILED");
			}
			return company;
		
		
		
    }
    
    private boolean existOrNotByCoupName(Coupon coupon) throws DaoExeption {
 		boolean answer = false;
 		   
 		  try {
 				String sqlName = "SELECT title FROM coupon WHERE "
 				+ "title= '" + coupon.getTitle() + "'";
 				Statement stat = DBconnectorV3.getConnection().createStatement();
 				ResultSet rs = stat.executeQuery(sqlName);
 				rs.next();
 			   
 				if (rs.getRow() != 0) {
 					answer = true;
 				} // if
 	            } catch (SQLException e) {
 	 	   			e.printStackTrace();
 	            	throw new DaoExeption("Error: cannot make sure if the coupon is in the DataBase");
 	            } // catch
 		  return answer;
 	}
}
