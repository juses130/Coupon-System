package com.dbdao;

import java.awt.dnd.DnDConstants;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

import javax.naming.spi.DirStateFactory.Result;

import com.added.functions.DBconnector;
import com.added.functions.SharingData;
import com.dao.interfaces.*;
import com.javabeans.*;
import com.mysql.jdbc.util.ResultSetUtil;

public class CompanyDBDAO implements CompanyDAO {
	
	// Default Constructor.
	public CompanyDBDAO() {}
	
	@Override
	public void createCompany(Company company) {
		
		// creating ResultSet
		ResultSet rs = null;

		// Be aware, this is a Temporary method-connections. 
		// we will change the method of the connections in the next weeks.
		
		try {
			DBconnector.getCon();
			String sqlQuery = "INSERT INTO coupon.company (COMP_NAME, PASSWORD, EMAIL) VALUES(?,?,?)";
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			//short cou = 1;
			
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
			e.printStackTrace(); // TODO: by the project guide, WE DON'T NEED TO PRINT the StackTrace.
		} // catch
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
	} // createCompany - Function

	// 3 Remove methods! 
	
	//1. By Company.
	@Override
	public void removeCompany(Company company) throws SQLException {
			
		ResultSet rs = null;
		Statement stat = null;
		try {
			
			DBconnector.getCon();
			String sqlDELobject = "DELETE FROM coupon.company WHERE Comp_ID = "
					+ company.getId() ;
			stat = DBconnector.getInstatce().createStatement();
			rs = stat.executeQuery(sqlDELobject);
			rs.next();
			
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
		finally {
			DBconnector.getInstatce().close();
		} // finally
		
	}
	
	//2. By ID (long)
	@Override
	public void removeCompany(long id) throws SQLException{
		// test
		
		try {
			DBconnector.getCon();
			//String compName, email, password;
			
			String sqlDELid = "DELETE FROM coupon.company WHERE Comp_ID =?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlDELid);
			prep.setLong(1, id);
			prep.executeUpdate();
			
		}
		catch (SQLException e) {
			e.getMessage();
		}
		finally {
			DBconnector.getInstatce().close();
		} // finally
		
	} // removeCompany - By ID - Function
	
	//.3 By Name (String)
	@Override
	public void removeCompany(String name) throws SQLException {
		// TODO: Add here - IsExist
		try {
			DBconnector.getCon();
			String sqlDELname = "DELETE FROM coupon.company WHERE Comp_name =?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlDELname);
			prep.setString(1, name);
			
			prep.executeUpdate();
			
			// Letting the other Classes (if they asking) that the Company Removed Succsefully.
			SharingData.setFlag1(true);
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		finally {
			DBconnector.getInstatce().close();
		} // finally
		
	} // removeCompany - BY Name - Function
	
	
	@Override
	public void updateCompany(Company company) {

		try {
			
			DBconnector.getCon();
			
			String sqlCmdStr = "UPDATE coupon.company SET Comp_name=?, password=?, email=? WHERE Comp_ID=?";
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement (sqlCmdStr);
			prep.setString(1, company.getCompName());
			prep.setString(2, company.getPassword());
			prep.setString(3, company.getEmail());
			prep.setLong(4, company.getId());
			
			prep.executeUpdate();
			
		    // Letting the others (if the asking) that the Company update Succsefully.
		    SharingData.setFlag1(true);
		    String tostring = company.toString();
			SharingData.setVarchar4(tostring);

			} catch (SQLException e) {
			e.printStackTrace();
			}
			finally {
			try {
				
			DBconnector.getInstatce().close();
			} catch (SQLException e) {
			e.printStackTrace();
					} // catch
			} // finally
				

		
	} // updateCompany - Function

	public void updateComp2(Company c) {
		try {
			
			DBconnector.getCon();
			
			String sqlCmdStr = "UPDATE coupon.company SET Comp_name=?, email=? WHERE Comp_ID=?";
			PreparedStatement stat = DBconnector.getInstatce().prepareStatement (sqlCmdStr);
			stat.setString(1, c.getCompName());
			stat.setString(2, c.getEmail());
			stat.setLong(3, c.getId());
			stat.executeUpdate();
			
			long tostring = c.getId();
			SharingData.setLongNum1(tostring);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Company getCompany(long id) {
		// TODO:
		Company c = null;
		String compName, email, password;
		
		try {

			DBconnector.getCon();
			String sqlSEL = "SELECT * FROM coupon.company WHERE Comp_ID= ?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			
			ResultSet rs = prep.executeQuery();
			rs.next();
			compName = rs.getString("Comp_name");
			email = rs.getString("Email");
			password = rs.getString("password");
			

			c = new Company(id, compName, password, email);
			String companyInfo = c.toString();
			SharingData.setVarchar2(companyInfo);

			// Letting the others (if the asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		return c;
		
		
	} // getCompany - Function

	@Override
	public Collection<Company> getAllCompanies() {
		
		String sql = "SELECT * FROM company";
		Collection<Company> companies = new HashSet<>();
		Company c = null;
		ResultSet rs = null;
		
		try {
			DBconnector.getCon();
			Statement stat = DBconnector.getInstatce().createStatement();
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
			e.printStackTrace();
		} // catch
		
		finally {
			try {
				rs.close();
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
		} // finally
		
		// TODO: change the NULL
		return companies;
	} // getAllCompanies

	@Override
	public Collection<Coupon> getCoupons() {
		
		
		
		return null;
	}

	@Override
	public boolean login(String compName, String password) {
		
		ResultSet rs1 = null;
		Statement stat1 = null;

		boolean hasRows = false;
try {
			
			DBconnector.getCon();
			String sqlName = "SELECT Comp_name, password FROM company WHERE "
					+ "Comp_name= '" + compName + "'" + " AND " + "password= '" 
					+ password + "'";
			stat1 = DBconnector.getInstatce().createStatement();
		    rs1 = stat1.executeQuery(sqlName);
		    rs1.next();

			if (rs1.getRow() != 0) {
				hasRows = true;
				
				System.out.println(hasRows);
			}

            } catch (SQLException e) {
	        e.printStackTrace();
	        
            } // catch
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}// finally

	return hasRows;
	}	
	

}
