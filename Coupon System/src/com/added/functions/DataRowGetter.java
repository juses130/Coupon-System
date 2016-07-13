package com.added.functions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.javabeans.*;

public class DataRowGetter {

	//private static Company c = null;
	private static int countANDcalculate = 0;
	private static boolean verificationExistInDataB = false;
	private static short verifyByNumber = 0;
	
	// Constructor (private)
	private DataRowGetter() {};
	
	// Main Function
	public static Company getCompanyRow (Company c) throws SQLException {
		
		// now we need a new Company Object for the next checks.
		
		//while(true) {
			
				String compName = c.getCompName();
				String compPass = c.getPassword();
				String compEmail = c.getEmail();
				long compID = c.getId();
				
				/*
				 * The instances below is ID of the IF'S Conditions.
				 * We need them for checking which information we have on the Obejct.
				 */
				
				short ifCompName = 0;
				short ifCompPassword = 0;
				short ifCompID = 0;
				short ifCompEmail = 0;
				

				
				/*
				 * This is the Main Checker.
				 * is checking if we have a 'null' instead of 'Parameter'.
				 */
				if(compName != null) {
					c.setCompName(compName);
					ifCompName = 1;
					countANDcalculate = ifCompName;
				}
				if (compPass != null) { // Cannot search by Password.
					c.setPassword(compPass);
					ifCompPassword = 10;
					countANDcalculate = countANDcalculate +  ifCompPassword;
				}
				if (compEmail != null) { // Cannot search by Email
					c.setEmail(compEmail);
					ifCompEmail = 100;
					countANDcalculate = countANDcalculate + ifCompEmail; 
				}
				if (compID != 0) {
					c.setId(compID);
					ifCompID = 1000;
					countANDcalculate = countANDcalculate + ifCompID;
				}
						
				/*
				 * AND NOW for the answers and the real checkers..
				 */
				
				// If it's ALL set (4 parameters), we just return the newComp Object.
				if (countANDcalculate == 1111) {
					
					IsExistDB.namePassExistV2Company(c.getCompName(), c.getPassword());
					if(IsExistDB.getAnswer() == true) {
						DBconnector.getCon();
						
						/*/**********************************************************
						 * IMPORTANT NOTE: the description below is relevant ONLY **
						 * if the User typed the UserName and Password Currectly. **
						 ***********************************************************
						 * 
						 * Now we'll make sure that the Program will send to
						 * the USER the currect ID of the Object.
						 * we will pull it from the DB.
						 * (even if the user put's somthing alse) 
						 */
						
						ResultSet rs = null;

						String sqlNAME = "SELECT Comp_ID, email FROM company WHERE Comp_name= '" + c.getCompName() + "'" ;
						Statement stat = DBconnector.getInstatce().createStatement();
						rs = stat.executeQuery(sqlNAME);
						
						rs.next();
						long currectID = rs.getLong("Comp_ID");
						String currectEmail = rs.getString("email");
						c.setId(currectID);
						c.setEmail(currectEmail);
						
						stat.close();
						rs.close();
	
						DBconnector.getInstatce().close();
				
						// will give a TRUE verification - Succeed.
						verificationExistInDataB = true;
						return c;
						
					} // if
					
				} // if - 4 parameters
				
				// if we have 3 parameters: User && Password && Email
				if (countANDcalculate == 111) {
					
					/*/***********************************************************************
					 * If the Result is: '111' it's tell us that the USER typed:   **********
					 * UserName, Password, Email.                                  **********
					 * So now we need to confirm it.                               **********
					 ************************************************************************
					 *
					 * Now , as before. we'll make sure that the Program will send to
					 * the USER the currect EMAIL of the Object.
					 * we will pull it from the DB.
					 * (even if the user put's somthing alse) 
					 */
					
					IsExistDB.namePassExistV2Company(c.getCompName(), c.getPassword());
					if(IsExistDB.getAnswer() != false) {
						
		
						ResultSet rs = null;
						DBconnector.getCon();
						
						String sqlNAME = "SELECT Comp_ID, email FROM company WHERE Comp_name= '" 
						+ c.getCompName() + "'";
						Statement stat = DBconnector.getInstatce().createStatement();
						rs = stat.executeQuery(sqlNAME);
						rs.next();
						
						// We only need to insert id and then return it.
						long currectID = rs.getLong("Comp_ID");
						String currectEmail = rs.getString("email");
						c.setId(currectID);
						c.setEmail(currectEmail);
						
						stat.close();
						rs.close();
						
						DBconnector.getInstatce().close();
						verificationExistInDataB = true;
						return c;
						
					} // if
					else {
						verificationExistInDataB = false;
					
					}
					
				} // if - 3 parameters
				
				// IF - 2 pramaters: User && Password
				if(countANDcalculate == 11) {
					
					IsExistDB.namePassExistV2Company(c.getCompName(), c.getPassword());
					if(IsExistDB.getAnswer() == true) {
						
						DBconnector.getCon();
						ResultSet rs = null;
						
						String sqlNAME = "SELECT Comp_ID, email FROM company WHERE Comp_name= '" 
						+ c.getCompName() + "'";
						Statement stat = DBconnector.getInstatce().createStatement();
						rs = stat.executeQuery(sqlNAME);
						rs.next();
						
						// We only need to insert id and then return it.
						long currectID = rs.getLong("Comp_ID");
						String currectEmail = rs.getString("email");
						c.setId(currectID);
						c.setEmail(currectEmail);
						
						DBconnector.getInstatce().close();
						verificationExistInDataB = true;
						return c;
						
					} // if
					else {
						verificationExistInDataB = false;
					}
				}
				if (countANDcalculate == 1011) {
					
					IsExistDB.namePassExistV2Company(c.getCompName(), c.getPassword());
					if(IsExistDB.getAnswer() != false) {
						
		
						ResultSet rs = null;
						DBconnector.getCon();
						
						String sqlNAME = "SELECT Comp_ID, email FROM company WHERE Comp_name= '" 
						+ c.getCompName() + "'";
						Statement stat = DBconnector.getInstatce().createStatement();
						rs = stat.executeQuery(sqlNAME);
						rs.next();
						
						// We only need to insert id and then return it.
						long currectID = rs.getLong("Comp_ID");
						String currectEmail = rs.getString("email");
						c.setId(currectID);
						c.setEmail(currectEmail);
						
						stat.close();
						rs.close();
						
						DBconnector.getInstatce().close();
						verificationExistInDataB = true;
						return c;
						
					} // if
					else {
						verificationExistInDataB = false;
					
					}
				}

		return null;
		
	}

	public static int getCountANDcalculate() {
		return countANDcalculate;
	}

	public static boolean isVerificationExistInDataB() {
		return verificationExistInDataB;
	}
	public static short getVerifyByNumber() {
		return verifyByNumber;
	}

} // dataGetter - Class
