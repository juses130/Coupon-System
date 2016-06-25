package com.added.functions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Raziel
 * This Class is A Helper Class.
 * is checks IF the ID | NAME | PASSWORD is exist in the DataBase of this program.
 * It's based on the SELECT syntax.
 * And this Class is running first and before any other class that connected to the DataBase.
 * It is giving us the option to check ANY USER INPUT and COMPARE IT 
 * before we try to change something in the DataBase.
 */

public class IsExistDB {

	/* This Class is A Helper Class. 
	 * Is checking if the ID or NAME is Exist in the DB *before* we running
	 * the DBDAO Methods. 
	 */
	
	// Attributes
	private static boolean answer = false;
	
	/**
	 * This instatce is Only for this class. 
	 * When we need 2 'TRUE' answers or 2 'FALSE' answers.
	 * For now, is only for the idExist().
	 */
	private static boolean answer2 = false;

	// private Constructor
	private IsExistDB() {}
	
	// Functions
	
   public static void idExist(long id) {
		
			try {
				
				ResultSet rs = null;
				
				DBconnector.getCon();
				String sqlSEL = "SELECT Comp_ID FROM coupon.company WHERE Comp_ID= ?" ;
				PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlSEL);
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
				e.printStackTrace();
			} // catch
			
			finally {
				try {
					DBconnector.getInstatce().close();
				} catch (SQLException e) {
					
				}
			} // finally

	}

   public static void idExistV2Customer(long id) {
		
		try {
			
			ResultSet rs = null;
			
			DBconnector.getCon();
			String sqlSEL = "SELECT cust_ID FROM customer WHERE cust_ID= ?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlSEL);
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
			e.printStackTrace();
		} // catch
		
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		} // finally

}

   public static void idExistV2Coupon(long id) {
		
		try {
			
			ResultSet rs = null;
			
			DBconnector.getCon();
			String sqlSEL = "SELECT coup_ID FROM coupon WHERE coup_ID= ?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			rs = prep.executeQuery();
			
			boolean hasRows = false;
			
			while(rs.next()) {
			 hasRows = true;
			 answer2 = true;
			}
			if(!hasRows) {
				answer2 = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} // catch
		
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		} // finally

} // idExistV2Coupon

   
   
   public static void namePasswordExist(String name, String password){
		
    	try {
			
			ResultSet rs = null;
			
			DBconnector.getCon();
			String sqlkNAME = "SELECT Comp_name FROM coupon.company WHERE Comp_name= ?" ;
			PreparedStatement prep1 = DBconnector.getInstatce().prepareStatement(sqlkNAME);
			prep1.setString(1, name);
			rs = prep1.executeQuery();
		
			String sqlPassword = "SELECT password FROM coupon.company WHERE `password`= ?" ;
			PreparedStatement prep2 = DBconnector.getInstatce().prepareStatement(sqlPassword);
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
			e.printStackTrace();
		} // catch
		
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		} // finally
    	
	} // nameExist - Function
   
   public static void idPasswordExist(long id, String password) {
	// TODO: when we add to the project 'passwordHASH' we will need an update here.
   	try {
   		    idExist(id);
			ResultSet rs = null;
			
			DBconnector.getCon();
			
			String sqlPassword = "SELECT password FROM coupon.company WHERE `password`= ?" ;
			PreparedStatement prep2 = DBconnector.getInstatce().prepareStatement(sqlPassword);
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
			e.printStackTrace();
		} // catch
		
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		} // finally
   	
   }
   
   //ID AND Password Checker V2
   public static boolean idPassExistV2Company(long id, String password) {
	   
	   Statement stat = null;
	   ResultSet rs = null;
	   //boolean hasRows = false;
	   try {
		
		   DBconnector.getCon();
			String sqlName = "SELECT Comp_ID, password FROM company WHERE "
					+ "Comp_ID= " + id + " AND " + "password= '" 
					+ password + "'";
			stat = DBconnector.getInstatce().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}
			else {
				answer = false;
			}

            } catch (SQLException e) {
	        e.printStackTrace();
	        
            } // catch
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		}// finally
	   
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
			stat = DBconnector.getInstatce().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}

            } catch (SQLException e) {
	        e.printStackTrace();
	        
            } // catch
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		}// finally
   return answer;
	   
   }
   
 //ID AND Password Checker V2
   public static boolean idPassExistV2Customer(long id, String password) {
	   
	   Statement stat = null;
	   ResultSet rs = null;
	   //boolean hasRows = false;
	   try {
		
		   DBconnector.getCon();
			String sqlName = "SELECT Cust_ID, password FROM customer WHERE "
					+ "Cust_ID= " + id + " AND " + "password= '" 
					+ password + "'";
			stat = DBconnector.getInstatce().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}
			else {
				answer = false;
			}

            } catch (SQLException e) {
	        e.printStackTrace();
	        
            } // catch
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		}// finally
	   
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
			stat = DBconnector.getInstatce().createStatement();
			rs = stat.executeQuery(sqlName);
			rs.next();
		   
			if (rs.getRow() != 0) {
				answer = true;
			}

            } catch (SQLException e) {
	        e.printStackTrace();
	        
            } // catch
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		}// finally
   return answer;
	   
   }
      


   
   public static void emailExist(String email){
		
   	try {
			
			ResultSet rs = null;
			
			DBconnector.getCon();
			String sqlSEL = "SELECT Comp_name FROM coupon.company WHERE Email= ?" ;
			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlSEL);
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
			e.printStackTrace();
		} // catch
		
		finally {
			try {
				DBconnector.getInstatce().close();
			} catch (SQLException e) {
				
			}
		} // finally
   	
	} // nameExist - Function

    
   public static boolean getAnswer() {
    	return answer;
    }


   public static boolean getAnswer2() {
	
	return answer2;
    }

	
}
