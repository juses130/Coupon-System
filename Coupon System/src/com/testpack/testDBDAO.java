package com.testpack;

import java.sql.*;
import java.util.*;
import com.added.functions.*;
import com.dbdao.*;
import com.javabeans.*;

/**
 * 
 * @author Raziel
 * This is a Test Class for our Project. 
 * Attention! -> THIS IS NOT THE REQUESTED TEST IN THE PROJECT GUIDE INSTRUCTIONS - it's only for us, the Developers.
 *
 */

public class testDBDAO {

	
	
	// This static short is helping to the function userInputwww
	private static short counterWorngTimes = 0;
    
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		// Printing wellcom, loading JDBC Driver, Printing Main Usage.
		printWellcom();
		loadDriver();
		printUsageMainOptions();
		
		

		// boolean just for the MAIN while loop for keeping it running.
		boolean on = true;
		
		while (on) {
			short userChoiceOfSideWork = userInputShort();
			
			//Check the user choice and switch it:
			switch (userChoiceOfSideWork) {
			
			case 1: {
				CompanySwitch();
				break;
			} // case 1
			case 2: {
				System.out.println("Still not ready..");
				break;
			}
			case 3: {
				//vote();
				System.out.println("Still not ready..");
				break;
			}
			case 4: {
				//getAllMoives();
				System.out.println("Still not ready..");
				break;
			}
			case 5: {
				//getTopMovie();
				System.out.println("Still not ready..");
				break;
			}
			case 6: {
				//Load();
				System.out.println("Still not ready..");
				break;
			}
			case 7: {
				System.out.println("Still not ready..");
				break;

			}
			case 8: {
				printUsageMainOptions();
				continue;
			}
			case 0: {
				System.out.println("Thank You! Bye Bye :) ");
				on = false;
				break;
			}
			} // Switch
			
		} // While Loop - Switch
		
	} // main

	
/*
 *  This is Multi-Use Functions - Main Prints, userInputs, loadDriver and more.
 */

	public static void printWellcom() {
		System.out.println("Wellcom To Our Basic Program - Working with DataBase (SQL) :)");
		System.out.println("Written By Raziel, Avi and Yiftach.");
		System.out.println("Beta Version: 1.0");
		System.out.println("******************");

	}

	public static void printUsageMainOptions() {

		
		System.out.println( "\n" + 
				 "Main Usage: " + "\n"
				 + "You have 3 Main Option of Using this JavaProgram - Database: " + "\n");
		System.out.println("1. Compnay - Add, Remove, Update.."
				+ "\n" + "2. Customer - same as above."
				+ "\n" + "3. Coupon - same as above."
				+ "\n" + "8. Go Back The Main Usage Guide."
				+ "\n" + "0. To Quit" 
				+ "\n");
		
	}
	
	public static void printIDnotExist() {
		System.out.println("\n" + "****************************************************");
		System.out.println("Access Denied :( ");
		System.out.println("Error - The ID number dosen't exist in the DataBase :(");
		System.out.println("****************************************************" + "\n");
		printGoingBackToUsage();
	}
	
	public static void printNAMEnotExist() {
		System.out.println("\n" + "****************************************************");
		System.out.println("Access Denied :( ");
		System.out.println("Error - ONE or More of the parameters: ' ID | NAME | PASSWORD ' dosen't match OR exist in the DataBase :(");
		System.out.println("****************************************************" + "\n");
		printGoingBackToUsage();
	}
	public static void printEMAILnotExist() {
		System.out.println("\n" + "****************************************************");
		System.out.println("Error - The Email dosen't exist in the DataBase :(");
		System.out.println("****************************************************" + "\n");
		printGoingBackToUsage();
	}
	
	public static void printFoundID() {
		System.out.println("\n" + "****************************************************");
		System.out.println("Your Company ID Was Found In The DataBase :)");
		System.out.println("****************************************************" + "\n");

	}

	public static void printFoundNAME() {
		System.out.println("\n" + "****************************************************");
		System.out.println("Access Granted :)");
		System.out.println("Your Company Name Was Found In The DataBase.");
		System.out.println("****************************************************" + "\n");

	}
	
	public static void printCompanyRemoved() {
		System.out.println("\n" + "------------ Company Removed Successfully ----------" + "\n");

	}
	
	public static void printGoingBackToUsage() {
		
		System.out.println("Going back to Main Usage.." + "\n");

	}
	
	private static short userInputShort() {

		/*
		 * This is a Multi-Purpose Function.
		 * We will use this method anytime we want to input(By Scanner) SHORT variable.
		 */
		System.out.print("->> ");
		
		Scanner scanner = new Scanner(System.in);
		
		try {
			short choice = scanner.nextShort();
			if (choice < 0 || choice > 8) {
				throw (new IllegalArgumentException());
			} // IF - choice
			else {
				return choice;
			} // else
		} // try
		// Catch the two problems we can get. Strings and Numbers.
		catch (InputMismatchException | IllegalArgumentException e) {
			System.out.println("Error - Please take a look again in the Usage or type 8 To access it.");
			// counter LEFT for counting back:
			int counterLEFT = 3 - counterWorngTimes;
			System.out.println("You have" + " ** " + counterLEFT + " ** " + "More Attempts To Fail.");

			// Counting the worng times of the user inputs:
			counterWorngTimes++;
			
			if (counterWorngTimes > 3 || counterWorngTimes < 0) {
				System.out.println("You've reached the 3 attempts You had. Exiting Program..");
				return 0;
			} // if - counterWorngTimes 
			return 8;
		} // catch
		
			
	} // userInputShort

	private static long userInputLong() {
		
		
			Scanner scanner = new Scanner(System.in);
			System.out.print("->> ");
			
			try {
				long input = scanner.nextLong();
				return input;
			} catch ( InputMismatchException e) {
				System.out.println("\n" + "Error - YOU SHOULD TYPE A *NUMBERS ONLY* AND NOT A STRING!");
				System.out.println("When an ERROR like this occurs - the program will send ZERO (number) as default." + "\n");
			} // catch

		return 0;
		
	}
	
	private static void checker() {
		
	}
	
	private static String userInputString(){
		System.out.print("->> ");
		Scanner scanner = new Scanner(System.in);
		//scanner.close();
		return scanner.nextLine();
		

	} // userInputString
	
	private static void loadDriver() {
		// Loading JDBC Driver - Function.
				try {
					//String driverName = loadFromFIle();
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("----------- DRIVER LOADED -----------------" + "\n");

				} catch (ClassNotFoundException e) {
					System.out.println("----------- ERROR: Driver Didn't Loaded -----------------");
				}
	}
	
	
	
	/* 
	 * The Next Functions is For The Objects.
	*/
	
	//1. Companies List Functions.
    public static void printUsageCompany() {

		
		System.out.println( "\n" + 
				 "Company Side: " + "\n"
				 + "You Have Couple of Options Here (DB is DataBase): " + "\n");
		System.out.println("1. Add a Company to the DB."
				+ "\n" + "2. Remove a Company from the DB."
				+ "\n" + "3. Update a Company in the DB."
				+ "\n" + "4. Get a Specific Company (By ID) from the DB."
				+ "\n" + "5. Get the List of all Companies in the DB."
				+ "\n" + "0. To Quit" 
				+ "\n");
		
	}

    private static void CompanySwitch() throws SQLException {
    	
    	printUsageCompany();
    	short choice = userInputShort();
    	
    	switch (choice) {
    	
    	case 1: {
    		addCompnay_T();
    		//printUsageMainOptions();
    		break;
    	}
    	case 2: {
    		removeCompany_T();
    		// TODO: Still need to add Remove By Company Object.
    		break;
    	}
    	case 3: {
    		updateCompany_T();
    		break;
    	}
    	case 4: {
    		getCompanyID_T();
    		break;
    	}
    	case 0: {
    		printUsageMainOptions();
    		break;
    	}
    	
    	} // switch
    	
    	
    } // CompanySwitch Function
    
    private static void addCompnay_T() throws SQLException {
		
		while (true){
			
            System.out.print("NEW Company Name: ");
			String name = (userInputString());
			
            System.out.print("NEW Company Email: ");
            String email = (userInputString());
			
            System.out.print("NEW Company Password: ");
            String password = (userInputString());
			
			// check if the user put's somthing empty...
			
			if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
				System.out.println("\n" + "Error - the fields are empty!");
				printGoingBackToUsage();
				printUsageMainOptions();
				break;
			}
			Company c = new Company();
			CompanyDBDAO db = new CompanyDBDAO();
			
			c.setCompName(name);
			c.setEmail(email);
			c.setPassword(password);
			db.createCompany(c);
			
			if(SharingData.isFlag1() == true) {
				System.out.println(c.toString());
				System.out.println("------------ Company Added Successfully ----------" + "\n");
				printUsageCompany();
				
			}
			else {
				System.out.println(db.toString());
				System.out.println("\n" + "****************************************************");
    			System.out.println("Error - No Changes Were Made :(");
				System.out.println("\n" + "****************************************************");
			} // else
			
			
			System.out.println("Whould you keep adding companies? Type '1' for YES or any other Number for NO.");
			short choice1 = userInputShort();
			
			if (choice1 == 1) {
				continue;
			} // if 
			else {
				printUsageMainOptions();
				break;
			}
		
		} // while loop
	} // addCompnay - Function
	
    private static void removeCompany_T() {
    	// TODO: I need to build the removeFunction first.
    	
    	while(true) {
    		CompanyDBDAO coDBdao = new CompanyDBDAO();
    		
    		// Options menu:
    		System.out.println("You can Delete a Company By 2 Option:");
    		System.out.println("1. By The Company ID."
    				+ "\n" + "2. By The Company NAME."
    				+ "\n");
    		
    		// User Choice and 'if' checker.
    		short choice = userInputShort();
    		if (choice == 1) { // Delete By ID:
    			
    			System.out.print("Type Your Company ID: ");
    			SharingData.setLongNum1(userInputLong());
    			if (SharingData.getLongNum1() == 0) {
    				
    				printGoingBackToUsage();
    				printUsageMainOptions();
    				break;
    			}
    			
    			System.out.print("Type Your Company Password: ");
    			SharingData.setVarchar1(userInputString());
    			
    			IsExistDB.idPasswordExist(SharingData.getLongNum1(), SharingData.getVarchar1());
    			if(IsExistDB.getAnswer() == false && IsExistDB.getAnswer2() == false) {		
         		printNAMEnotExist();
         		printUsageMainOptions();
         		break;
//    			IsExistDB.idExist(SharingData.getLongNum1());
//    					if(IsExistDB.getAnswer() == false){
//    						printNAMEnotExist();
//    		         		printUsageMainOptions();
//    		         		break;
         		} // if
    			
    			else {	
    			      try {
				      coDBdao.removeCompany(SharingData.getLongNum1());
				      //System.out.println(SharingData.getVarchar4());
					  } catch (SQLException e) {
						System.out.println("Error:");
						System.out.println(e.getMessage());
					  }
        			  
    			      printCompanyRemoved();
    			} // else
    		} // if - choice = 1
    		else if(choice == 2) { // Delete By name:
    			
    			System.out.print("Type Your Company Name: ");
    			SharingData.setVarchar1(userInputString());
    			System.out.print("Type Your Company Password: ");
    			SharingData.setVarchar2(userInputString());
    			
    			IsExistDB.namePasswordExist(SharingData.getVarchar1(), SharingData.getVarchar2());
    			if(IsExistDB.getAnswer() == false) {
         				
         		printNAMEnotExist();
         		printUsageMainOptions();
         		break;
         		} // if
    		    else {
    			     try {
				     coDBdao.removeCompany(SharingData.getVarchar1());
				     printCompanyRemoved();
				     } catch (SQLException e) {
				     System.out.println("Error:");
			         System.out.println(e.getMessage());
				     } // catch
    			
    			     //System.out.println(c.toString());
    			     
    		    } // else
    		} // else if - choice = 2
    		/**
    		 * The next method (choice == 3) is for us, the developers.
    		 */
    		else if (choice == 3) { // Delete By Object (Company)
    			
    			System.out.print("Type Your Company ID: ");
    			SharingData.setLongNum1(userInputLong());
    			if (SharingData.getLongNum1() == 0) {
    				
    				printGoingBackToUsage();
    				printUsageMainOptions();
    				break;
    			}
    			
    			System.out.print("Type Your Company Password: ");
    			SharingData.setVarchar1(userInputString());
    			
    			IsExistDB.idPasswordExist(SharingData.getLongNum1(), SharingData.getVarchar1());
    			if(IsExistDB.getAnswer() == false) {		
         		printNAMEnotExist();
         		printUsageMainOptions();
         		break;
         		} // if
    			else {
    				try {
						Company c = new Company();
						c.setId(SharingData.getLongNum1());
						System.out.println(c.toString());
						coDBdao.removeCompany(c);
						printCompanyRemoved();
					} catch (SQLException e) {
						// TODO: handle exception
					} // catch
    			} // else
    		} // else if - choice 3
    		else {
    			printGoingBackToUsage();
    			printUsageMainOptions();
    			break;
    		
    		} // switch
			System.out.println("Whould you like to Removing more companies? Type '1' for YES or any other Number for NO.");
			short choice1 = userInputShort();
			
			if (choice1 == 1) {
				continue;
			} // if 
			else {
				printUsageMainOptions();
				break;
			} // else

    		} // while loop
    		
    	} // removeCompany - plan
	
    private static void updateCompany_T() {
    	
    	while(true) {
    		 
	     		
    		System.out.println("Update Company:" + "\n");
    		System.out.println("Please type your Company NAME and Password.");
    		//System.out.println("\n" + "Type The OLD Company Name:");
     		
    		System.out.print("Company Name: ");
            SharingData.setVarchar1(userInputString());;
            System.out.print("Comapny Password:");
            SharingData.setVarchar2(userInputString());
            
     		// Check if the NAME exist..
     		IsExistDB.namePasswordExist(SharingData.getVarchar1(), SharingData.getVarchar2());
     		if(IsExistDB.getAnswer() == false) {
     				
     		printNAMEnotExist();
     		printUsageMainOptions();
     		break;
     		} // if
     		else { // Move on to this block if we got 'TRUE' in the IF condition:
 	
     		printFoundNAME(); 
     				
     	    		
                    System.out.print("NEW Company Name: ");
     	    		//SharingData.setVarchar1(userInputString());
                    String name = userInputString();
     	    		
     	    		
     	    		System.out.print("NEW Email: ");
     	    		//SharingData.setVarchar2(userInputString());
     	    		String email = userInputString();
     	    		
     	    		
     	    		//System.out.print("New Password: ");
     	    		// Update Company!
     	    		//String password = userInputString();
     	    		
     	    		Company c = new Company();
     	    		CompanyDBDAO coDBdao = new CompanyDBDAO(); 
     		     	
     	    		c.setCompName(name);
     	    		c.setEmail(email);
     	    		//c.setPassword(password);
     	    		
     	     		coDBdao.updateComp2(c);

     	     		if(SharingData.isFlag1() == true) {
     	    			System.out.println("\n" + "------------ Company Updated Successfully ----------" + "\n");
     	    			System.out.println(SharingData.getVarchar4());
     	    			
     	    		}
     	    		else {
     	    			System.out.println("\n" + "****************************************************");
     	    			System.out.println("Error - No Changes Were Made :(");
     	    			System.out.println("****************************************************" + "\n");
     	    			printGoingBackToUsage();
     	    		} // else - flag
     			} // else

     		break;
    	} // while loop
    	
    } // updateCompany
    
    public static void getCompanyID_T() {
    	
    	while(true) {
        	System.out.println("Type The Company ID:");
        	
        	SharingData.setLongNum1(userInputLong());
        	IsExistDB.idExist(SharingData.getLongNum1());
        	
        	if (IsExistDB.getAnswer() == false) {
        		printIDnotExist();
    			printUsageMainOptions();
    			break;
        	} // if - isExist
        	else {
        		CompanyDBDAO co = new CompanyDBDAO();
        		co.getCompany(SharingData.getLongNum1());
        		
        		// Print the Company:
        		System.out.println(SharingData.getVarchar2());
        		
        		if(SharingData.isFlag1() == true) {
	    			System.out.println("\n" + "------------ Company Function (getID) Was Run Successfully ----------" + "\n");
        	}
        	} // else
        	
    	} // while loop

    	} // getCompanyID_T - Function

} // Class	