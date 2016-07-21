package com.added.functions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.facade.AdminFacade;
import com.facade.CompanyFacade;

import ExeptionErrors.DaoExeption;

/**
 * 
 *
 * <p>This Class is A Helper Class</p>
 * 
 * IT's USED ONLY FOR THE TETSING CLASSES!</p>
 * 
 * is checks IF the ID | NAME | PASSWORD is exist in the DataBase of this program.
 * It's based on the SELECT syntax and logic.
 * And this Class is running first and before any other class that connected to the DataBase.
 * It is giving us the option to check ANY USER INPUT and COMPARE IT 
 * before we try to change something in the DataBase.
 */

public class IsExistDB {
	
	// Attributes
	private static boolean answer = false;
	
	/**
	 * This instatce is Only for this class. 
	 * When we need 2 'TRUE' answers or 2 'FALSE' answers.
	 * For now, is only for the idExist().
	 */
	private static boolean answer2 = false;
	
	private static boolean dosentExistInDB = true;

	// private Constructor
	private IsExistDB() {}
	
	// Functions
	
   public static void idExist(long id, String table, String selectColmun, String whereColmun) {
		
			try {
				
				ResultSet rs = null;
				
	
				String sqlSEL = "SELECT " + selectColmun + " FROM " + table + " WHERE " + whereColmun +"= ?" ;
				PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
				prep.setLong(1, id);
				rs = prep.executeQuery();
				
				
				boolean hasRows = false;
				
				while(rs.next()) {
				 hasRows = true;
				 
//				 answer = true;
				 answer2 = true;
				}
				if(!hasRows) {
//					answer = false;
					answer2 = false;
				}

			} catch (SQLException e) {
				SharingData.setExeptionMessage(e.getMessage());			
				} // catch
	}

   public static void idExistV2Customer(long id) {
		
		try {
			
			ResultSet rs = null;
			

			String sqlSEL = "SELECT cust_ID FROM customer WHERE cust_ID= ?" ;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			rs = prep.executeQuery();
			
			boolean hasRows = false;
			
			while(rs.next()) {
			 hasRows = true;
			 
//			 answer = true;
			 answer2 = true;
			}
			if(!hasRows) {
//				answer = false;
				answer2 = false;
			}
			
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		} // catch

}

   public static void idExistV2Coupon(long coupID, String table) {
		
		try {
			
			ResultSet rs = null;
			

			String sqlSEL = "SELECT coup_ID FROM " + table + " WHERE coup_ID= ?" ;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
			prep.setLong(1, coupID);
			rs = prep.executeQuery();
			
			boolean hasRows = false;
			
			while(rs.next()) {
			 hasRows = true;
			 answer2 = true;
			}
			if(!hasRows) {
				answer2 = false;
			}
			prep.close();
			rs.close();
			
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		} // catch

} // idExistV2Coupon

   public static boolean compNsameExist(String compName) throws DaoExeption {
	   AdminFacade admF = new AdminFacade();
	   admF.getCompany(compName);
	   if(admF.getCompany(compName) != null) {
		   return true;
	   }
	   else {
		   return false;
	   }
   }
   
   public static void namePasswordExist(String name, String password){
		
    	try {
			
			ResultSet rs = null;
			

			String sqlkNAME = "SELECT Comp_name FROM coupon.company WHERE Comp_name= ?" ;
			PreparedStatement prep1 = DBconnectorV3.getConnection().prepareStatement(sqlkNAME);
			prep1.setString(1, name);
			rs = prep1.executeQuery();
		
			String sqlPassword = "SELECT password FROM coupon.company WHERE `password`= ?" ;
			PreparedStatement prep2 = DBconnectorV3.getConnection().prepareStatement(sqlPassword);
			prep2.setString(1, password);
			rs = prep2.executeQuery();
			
			boolean hasRows1 = false;
			boolean hasRows2 = false;
			
			while(rs.next()) {
			hasRows1 = true;
			hasRows2 = true;
			 
			 answer = true;
			}
			if(!hasRows1 && !hasRows2) {
				answer = false;
			}

		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		} // catch

    	
	} // nameExist - Function
   
   public static void idPasswordExist(long id, String password) {
	// TODO: when we add to the project 'passwordHASH' we will need an update here.
   	try {
   		    idExist(id, "company", "comp_id", "comp_id");
			ResultSet rs = null;
			

			
			String sqlPassword = "SELECT password FROM coupon.company WHERE `password`= ?" ;
			PreparedStatement prep2 = DBconnectorV3.getConnection().prepareStatement(sqlPassword);
			prep2.setString(1, password);
			rs = prep2.executeQuery();
			
			boolean hasRows1 = false;
//			boolean hasRows2 = false;
			
			while(rs.next()) {
			hasRows1 = true;
//			hasRows2 = true;
			 
			 answer = true;
			}
			if(!hasRows1) {
				answer = false;
			}

		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		} // catch
   	
   }
   
   //ID AND Password Checker V2
   public static boolean idPassExistV2Company(long id, String password) {
	   
	   Statement stat = null;
	   ResultSet rs = null;
	   //boolean hasRows = false;
	   try {
			String sqlName = "SELECT Comp_ID, password FROM company WHERE "
					+ "Comp_ID= " + id + " AND " + "password= '" 
					+ password + "'";
			stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			
			if (rs.getRow() != 0) {
				answer = true;
			}
			else {
				answer = false;
			}

            } catch (SQLException e) {
            	SharingData.setExeptionMessage(e.getMessage());	        
            } // catch
	   
	   return answer;
   
   }
	   
   public static boolean namePassExistV2Company(String name, String password) {
	   Statement stat = null;
	   ResultSet rs = null;
	   //boolean hasRows = false;
	   try {
		
		   DBconnector.getCon();
			String sqlName = "SELECT Comp_name, password FROM company WHERE "
					+ "Comp_name= '" + name + "' AND " + "password= '" 
					+ password + "'";
			stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}

            } catch (SQLException e) {
            	SharingData.setExeptionMessage(e.getMessage());
	        
            } // catch
   return answer;
	   
   }
   
   // Name checker for all Tables, and Colums
   public static boolean stringExistV2(String table, String column, String name) {
	   Statement stat = null;
	   ResultSet rs = null;
	   //boolean hasRows = false;
	   try {
		
			String sqlName = "SELECT " + column + " FROM " + table + " WHERE "
					+ column + "='" + name + "'";
			stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}
			else {
				answer = false;
			}

            } catch (SQLException e) {
            	SharingData.setExeptionMessage(e.getMessage());
	        
            } // catch
		
	   if(answer = true) {
		   dosentExistInDB = false;
	   }
   return answer;
	   
   }
   
   public static void stringExistV3(String table, String column, String name) {
	   
	   ResultSet rs = null;
	   try {
			
			String sqlAll = "SELECT " + column + " FROM " + table + " WHERE "
					+ column + "=?";
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlAll);
			prep.setString(1, name);
			rs = prep.executeQuery();
			rs.next();

			boolean hasRow = false;
			
			while(rs.next()) {
				hasRow = true;
				answer = true;
			}
			if(!hasRow) {
				answer = false;
			}

            } catch (SQLException e) {
	        e.getMessage();
	        
            } // catch
	   
   }
   
 //ID AND Password Checker V2
   public static boolean idPassExistV2Customer(long id, String password) {
	   
	   Statement stat = null;
	   ResultSet rs = null;
	   //boolean hasRows = false;
	   try {
			String sqlName = "SELECT Cust_ID, password FROM customer WHERE "
			+ "Cust_ID= " + id + " AND " + "password= '" 
			+ password + "'";
			stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}
			else {
				answer = false;
			}

            } catch (SQLException e) {
            	SharingData.setExeptionMessage(e.getMessage());
	        
            } // catch
	   
	   return answer;
   
   }
   
   public static boolean namePassExistV2Customer(String name, String password) {
	   Statement stat = null;
	   ResultSet rs = null;
	   //boolean hasRows = false;
	   try {
		
		   DBconnector.getCon();
			String sqlName = "SELECT Cust_name, password FROM customer WHERE "
					+ "Cust_name= '" + name + "' AND " + "password= '" 
					+ password + "'";
			stat = DBconnectorV3.getConnection().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}

            } catch (SQLException e) {
	        SharingData.setExeptionMessage(e.getMessage());
	        
            } // catch
   return answer;
	   
   }
      


   
   public static void emailExist(String email){
		
   	try {
			
			ResultSet rs = null;
			

			String sqlSEL = "SELECT Comp_name FROM coupon.company WHERE Email= ?" ;
			PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSEL);
			prep.setString(1, email);
			rs = prep.executeQuery();
			
			boolean hasRows = false;
			
			while(rs.next()) {
			 hasRows = true;
			 
			 answer = true;
			}
			if(!hasRows) {
				answer = false;
			}
			
		} catch (SQLException e) {
			SharingData.setExeptionMessage(e.getMessage());
		} // catch
		
   	
	} // nameExist - Function

    
   public static boolean getAnswer() {
    	return answer;
    }


   public static boolean getAnswer2() {
	
	return answer2;
    }


   public static boolean isDosentExistInDB() {
	return dosentExistInDB;
}


   private static void setDosentExistInDB(boolean dosentExistInDB) {
	IsExistDB.dosentExistInDB = dosentExistInDB;
}



	
}
