package com.testpack;

import java.sql.*;
import java.util.*;

import javax.print.attribute.standard.PrinterMoreInfoManufacturer;

import com.added.functions.*;
import com.dbdao.*;
import com.javabeans.*;

/**
 * 
 * Read the next Description before using this program.
 * 
 * @author Raziel
 * This is a Test Class for our Project. 
 * Attention! -> THIS IS NOT THE REQUESTED TEST IN THE PROJECT GUIDE INSTRUCTIONS - it's only for us, the Developers.
 *
 * This Test-Class is divided to Sections.
 * Every section has couple of methods and functions.
 * Every section has a job here.
 * It is my first real program - so be nice.
 * If you just want to test the program as a user, click play and run it.
 * But if you want to test it as a developer, PLEASE READ the notes and 
 */

public class testDBDAO {

	
	
	// This static short is helping to the function userInputwww
	private static short counterWorngTimes = 0;
    
	/************************************************
	 ********      Main Section            **********
	 ************************************************
	 */
	
	public static void main(String[] args) throws SQLException {
		
		
		// Printing wellcom, loading JDBC Driver, Printing Main Usage.
		printWellcom();
		loadDriver();
		printUsageMainOptions();
		
		

		// boolean just for the MAIN while loop for keeping it running.
		boolean on = true;
		
		while (on) {
			
			//printUsageMainOptions();
			short userChoiceOfSideWork = userInputShort();
			
			//Check the user choice and switch it:
			switch (userChoiceOfSideWork) {
			
			
			case 1: {
				CompanyMenu();
				break;
			} // case 1
			case 2: {
				//printUsageMainOptions();
				CustomerMenu();
				break;
			}
			case 3: {
				//printUsageMainOptions();
				System.out.println("Still not ready..");
				
				break;
			}
			case 4: {
				//printUsageMainOptions();
				System.out.println("Still not ready..");
				break;
			}
			case 5: {
				//printUsageMainOptions();
				System.out.println("Still not ready..");
				break;
			}
			case 6: {
				//printUsageMainOptions();
				System.out.println("Still not ready..");
				break;
			}
			case 7: {
				//printUsageMainOptions();
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

	
	/************************************************
	 ********      Print Section           **********
	 ************************************************
	 *
	 * This is a Multi-Use Functions - Main Prints, userInputs, loadDriver and more.
	 *
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
	
	public static void printIDnotExist(String userType) {
		System.out.println("\n" + "****************************************************");
		System.out.println("Access Denied :( ");
		System.out.println("Error - your " + userType + " dosen't exist in the DataBase :(");
		System.out.println("****************************************************" + "\n");
		printGoingBackToUsage();
	}
	
	public static void printNoExistOrCurrect() {
		System.out.println("\n" + "****************************************************");
		System.out.println("Access Denied :( ");
		System.out.println("Error - ONE or More of the parameters: ' ID | NAME | PASSWORD | USER ' dosen't match OR exist in the DataBase!");
		System.out.println("****************************************************" + "\n");
		//printGoingBackToUsage();
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

	public static void printFoundInDB(String userType) {
		System.out.println("\n" + "****************************************************");
		System.out.println("Access Granted :)");
		System.out.println("Your "  + userType + " Was Found In The DataBase.");
		System.out.println("****************************************************" + "\n");

	}
	
	public static void printCompanyRemoved() {
		System.out.println("\n" + "------------ Company Removed Successfully ----------" + "\n");

	}
	
	public static void printCustomerRemoved() {
		System.out.println("\n" + "------------ Customer Removed Successfully ----------" + "\n");

	}
	
	public static void printGoingBackToUsage() {
		
		System.out.println("Going back to Main Usage.." + "\n");

	}
	
	/************************************************
	 ********    Scanner-INPUT Section     **********
	 ************************************************
	 *
	 * The next functions, allows us to create and close Scanners and Choices (for the menu)
	 * in any place on the test.`
	 *
	 */
	
	private static short userInputShort() {

		/*
		 * This is a Multi-Purpose Function.
		 * We will use this method anytime we want to input(By Scanner) SHORT variable.
		 */
		System.out.print("->> ");
		
		Scanner scanner = new Scanner(System.in);
		
		try {
			short choice = scanner.nextShort();
			if (choice < 8 && choice > -1 || choice == 822) {
				return choice;
			} // IF - choice
			else {
				throw (new IllegalArgumentException());
			} // else
		} // try
		// Catch the two problems we can get. Strings and Numbers.
		catch (InputMismatchException | IllegalArgumentException e) {
			System.out.println("Error - Please take a look again in the Usage or type 8 To access it.");
			// counter LEFT for counting back:
			int counterLEFT = 3 - counterWorngTimes;
			System.out.println("You have" + " ** " + counterLEFT + " ** " + "More Attempts To Fail.");

			printGoingBackToUsage();
			printUsageMainOptions();
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
	
	private static String userInputString(){
		System.out.print("->> ");
		Scanner scanner = new Scanner(System.in);
		//scanner.close();
		return scanner.nextLine();
		

	} // userInputString
	
	private boolean login_T() {

    	System.out.println("\n" + "Please type your Company-User and Password." + "\n");
    	System.out.print("Type Your Company Name: ");
    	String compName = userInputString();
    	System.out.print("Type Your Company Password: ");
    	String password = userInputString();
    	
    	CompanyDBDAO db = new CompanyDBDAO();
    	boolean existOrNot = db.login(compName, password);
    	
   		if(existOrNot == false){
        	printNoExistOrCurrect();
        	printUsageMainOptions();
        	return false;
        	} // if
        	else {
        		printFoundInDB("Company");
        		printUsageCompany();
        		return true;
        	}
    		}
	
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
	
	
	/************************************************
	 ********      Company Section         **********
	 ************************************************
	 *
	 * This the section of all the company function and method.
	 * Here we create the connection between all the methods of CompanyDB and CompanyDAO 
	 * from the packages in the Coupon System Project.
	 *
	 */
	
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

    private static void CompanyMenu() throws SQLException {
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
        	case 5: {
        		getAllCompanies_T();
        		printUsageMainOptions();
        		break;
        	}
        	case 822: { // Developers Option: Reset Table Company
        		resetTable_T();
        		printUsageMainOptions();
        		break;
        	}

        	case 0: {
        		//printUsageMainOptions();
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
				
			}
			else {
				System.out.println(db.toString());
				System.out.println("\n" + "****************************************************");
    			System.out.println("Error - No Changes Were Made :(");
				System.out.println("\n" + "****************************************************");
			} // else
			
			
			System.out.println("Whould you keep adding companies? Type '1' for YES or any other Number for NO.");
			short choice1 = userInputShort();
			
			if (choice1 != 1) {
				printUsageMainOptions();
				break;
			} // if 
			else {
				continue;
			}
		} // while loop
	} // addCompnay - Function
	
    private static void removeCompany_T() {
    	
    	
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
    			
    			IsExistDB.idPassExistV2Company(SharingData.getLongNum1(), SharingData.getVarchar1());
    			if(IsExistDB.getAnswer() == false && IsExistDB.getAnswer2() == false) {		
         		printNoExistOrCurrect();
         		printUsageMainOptions();
         		break;
    			}
    			else {	
    			      try {
				      coDBdao.removeCompany(SharingData.getLongNum1());
				      //System.out.println(SharingData.getVarchar4());
					  } catch (SQLException e) {
						System.out.println("Error:");
						System.out.println(e.getMessage());
					  }
        			  
    			      printCustomerRemoved();
    			} // else
    		} // if - choice = 1
    		else if(choice == 2) { // Delete By name:
    			
    			System.out.print("Type Your Customer Name: ");
    			SharingData.setVarchar1(userInputString());
    			System.out.print("Type Your Customer Password: ");
    			SharingData.setVarchar2(userInputString());
    			
    			IsExistDB.namePasswordExist(SharingData.getVarchar1(), SharingData.getVarchar2());
    			if(IsExistDB.getAnswer() == false) {
         				
         		printNoExistOrCurrect();
         		printUsageMainOptions();
         		break;
         		} // if
    		    else {
    			     try {
				     coDBdao.removeCompany(SharingData.getVarchar1());
				     printCustomerRemoved();
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
    			
    			System.out.println("Developers Only! Deleting By Company Object..");
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
         		printNoExistOrCurrect();
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
						
					} // catch
    			} // else
    		} // else if - choice 3
    		else if(choice == 4) { 
    			CompanyDBDAO db = new CompanyDBDAO();
    			Company c = new Company();
    			System.out.println("Developers Only! Deleting By Company Object..");
    			
    			System.out.println("Please enter the information below for verification" + "\n");
    			System.out.print("Type Your Company Name: ");
    			c.setCompName(userInputString());
    	    	System.out.print("Type Your Company Password: ");
    			c.setPassword(userInputString());
    			   			
    			boolean lastCheck = db.login(c.getCompName(), c.getPassword());
    			if(lastCheck == false) {
    				printIDnotExist("Company");
    				break;
    			}
    			else {
    				
    				try {
    					db.removeCompany(c);
					} catch (SQLException e) {
						e.printStackTrace();
					}
    				
    				
    			}
			}// else if - choice 4
    		else { // TODO
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
    		System.out.println("Please type your current Company ID and Password.");
    		System.out.println("Note: only because it's beta version test - we asking for Company ID.");
    		//System.out.println("\n" + "Type The OLD Company Name:");
     		
    		System.out.print("Company ID: ");
            SharingData.setLongNum1(userInputLong());
            System.out.print("Comapny Password:");
            SharingData.setVarchar1(userInputString());
            
     		// Check if the NAME exist..
            IsExistDB.idPasswordExist(SharingData.getLongNum1(), SharingData.getVarchar1());
            if(IsExistDB.getAnswer() == false) {
     				
     		printNoExistOrCurrect();
     		printUsageMainOptions();
     		break;
     		} // if
     		else { // Move on to this block if we got 'TRUE' in the IF condition:
 	
     		printFoundInDB("Company"); 
     				
     	    		
                    System.out.print("NEW Company Name: ");
                    String name = userInputString();
     	    		
     	    		System.out.print("NEW Email: ");
     	    		String email = userInputString();
     	    		     	    		
     	    		long id = SharingData.getLongNum1();
     	            String password = SharingData.getVarchar1();
     	    		
     	    		Company c = new Company();
     	    		CompanyDBDAO coDBdao = new CompanyDBDAO(); 
     		     	
     	    		c.setCompName(name);
     	    		c.setEmail(email);
     	    		c.setPassword(password);
     	    		c.setId(id);
     	    		//c.setPassword(password);
     	    		
     	     		coDBdao.updateCompany(c);

     	     		if(SharingData.isFlag1() == true) {
     	     			System.out.println("\n" + SharingData.getVarchar4());
     	     			System.out.println("------------ Company Updated Successfully ----------" + "\n");
     	     			printGoingBackToUsage();
     	     			printUsageMainOptions();
     	    		} // if - is updated
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
    
    private static void getCompanyID_T() {
    	
    	while(true) {
        	System.out.println("Type The Company ID:");
        	SharingData.setLongNum1(userInputLong());
        	
        	IsExistDB.idExist(SharingData.getLongNum1());
        	if(SharingData.getLongNum1() == 0) {
        		System.out.println("Typing 'Zero' is mean = quit..");
        		printGoingBackToUsage();
        		printUsageMainOptions();
        		break;
        	} // if - it 0 the program will break from this function.
        	
        	if (IsExistDB.getAnswer2() == false) { // checks if the ID exist in the DB.
        		printNoExistOrCurrect();
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

    private static void getAllCompanies_T() {

    	while(true) {
    		
    		System.out.println("Please Type Your Admin Password:");
    		long password = userInputLong();
    		
    		if(password == 123456789) {
       			CompanyDBDAO db = new CompanyDBDAO();
    			System.out.println("Here is your Companeis List: " + "\n");
       			
    			System.out.println(db.getAllCompanies().toString());
    			break;
    		} // if
    		else {
 
    			System.out.println("Error: worng password!");
    			printGoingBackToUsage();
    			break;
    			
    		} // else
    		
    		
    		
    		
    	} // while loop
    	
    } // getAllCompanies_T - Function
    
	
    /************************************************
	 ********      Customer Section         *********
	 ************************************************
	 *
	 * This the section of all the customer function and method.
	 * Here we create the connection between all the methods of CustomerDB and CustomerDAO 
	 * from the packages in the Coupon System Project.
	 *
	 */
    
    public static void printUsageCustomer() {

		
		System.out.println( "\n" + 
				 "Customer Side: " + "\n"
				 + "You Have Couple of Options Here (DB is DataBase): " + "\n");
		System.out.println("1. Add a Customer to the DB."
				+ "\n" + "2. Remove a Customer from the DB."
				+ "\n" + "3. Update a Customer in the DB."
				+ "\n" + "4. Get a Specific Customer (By ID) from the DB."
				+ "\n" + "5. Get the List of all Customers in the DB."
				+ "\n" + "0. To Quit" 
				+ "\n");
		
	}

    private static void CustomerMenu() throws SQLException {
    
    		printUsageCustomer();
        	short choice = userInputShort();
        	switch (choice) {
        	
        	case 1: {
        		addCustomer_T();
        		break;
        	}
        	case 2: {
        		removeCustomer_T();
        		break;
        	}
        	case 3: {
        		updateCustomer_T();
        		break;
        	}
        	case 4: {
        		break;
        	}
        	case 5: {
        		break;
        	}
        	case 822: { // Developers Option: Reset Table Company
        		resetTable_T();
        		printUsageMainOptions();
        		break;
        	}

        	case 0: {
        		printUsageMainOptions();
        		break;
        	}
        	
        	} // switch
    	
    } // CompanyMenu - Function

    private static void addCustomer_T() throws SQLException {
	
	while (true){
		
        System.out.print("NEW Customer Name: ");
		String name = (userInputString());
		
        System.out.print("NEW Customer Password: ");
        String password = (userInputString());
		
		// check if the user put's somthing empty...
		
		if(name.isEmpty() || password.isEmpty()) {
			System.out.println("\n" + "Error - the fields are empty!");
			printGoingBackToUsage();
			printUsageMainOptions();
			break;
		}
		Customer c = new Customer();
		CustomerDBDAO cdb = new CustomerDBDAO();
		
		c.setCustName(name);
		c.setPassword(password);
		cdb.createCustomer(c);
		
		if(SharingData.isFlag1() == true) {
			System.out.println(c.toString());
			System.out.println("------------ Company Added Successfully ----------" + "\n");
			printUsageCompany();
			
		}
		else {
			System.out.println(cdb.toString());
			System.out.println("\n" + "****************************************************");
			System.out.println("Error - No Changes Were Made :(");
			System.out.println("\n" + "****************************************************");
		} // else
		
		
		System.out.println("Whould you keep adding Customers? Type '1' for YES or any other Number for NO.");
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
    
    private static void removeCustomer_T() {
	
	
	while(true) {
		
		CustomerDBDAO cusDB = new CustomerDBDAO();
		
		// Options menu:
		System.out.println("You can Delete a Customer By 2 Option:");
		System.out.println("1. By The Customer ID."
				+ "\n" + "2. By The Customer NAME."
				+ "\n");
		
		// User Choice and 'if' checker.
		short choice = userInputShort();
		if (choice == 1) { // Delete By ID:
			
			System.out.print("Type Your Customer ID: ");
			SharingData.setLongNum1(userInputLong());
			if (SharingData.getLongNum1() == 0) {
				
				printGoingBackToUsage();
				printUsageMainOptions();
				break;
			}
			
			System.out.print("Type Your Customer Password: ");
			SharingData.setVarchar1(userInputString());
			
			IsExistDB.idPassExistV2Customer(SharingData.getLongNum1(), SharingData.getVarchar1());
			if(IsExistDB.getAnswer() == false && IsExistDB.getAnswer2() == false) {		
     		printNoExistOrCurrect();
     		printUsageMainOptions();
     		break;
			}
			else {	
			      try {
			      cusDB.removeCustomer(SharingData.getLongNum1());
			      //System.out.println(SharingData.getVarchar4());
				  } catch (SQLException e) {
					System.out.println("Error:");
					System.out.println(e.getMessage());
				  }
    			  
			      printCompanyRemoved();
			} // else
		} // if - choice = 1
		else if(choice == 2) { // Delete By name:
			
			System.out.print("Type Your Customer Name: ");
			SharingData.setVarchar1(userInputString());
			System.out.print("Type Your Customer Password: ");
			SharingData.setVarchar2(userInputString());
			
			IsExistDB.namePassExistV2Customer(SharingData.getVarchar1(), SharingData.getVarchar2());
			if(IsExistDB.getAnswer() == false) {
     				
     		printNoExistOrCurrect();
     		printUsageMainOptions();
     		break;
     		} // if
		    else {
			     try {
			    	 cusDB.removeCustomer(SharingData.getVarchar1());
			    	 printCompanyRemoved();
			     } catch (SQLException e) {
			     System.out.println("Error:");
		         System.out.println(e.getMessage());
			     } // catch
			
			     //System.out.println(c.toString());
			     
		    } // else
		} // else if - choice = 2
		else { 
			printGoingBackToUsage();
			printUsageMainOptions();
			break;
		
		} // switch
		System.out.println("Whould you like to Removing more Customers? Type '1' for YES or any other Number for NO.");
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
   
    private static void updateCustomer_T() {
    	
    	while(true) {
    		 
    		System.out.println("Update Customer:" + "\n");
    		System.out.println("Please type your current Customer ID and Password.");
    		System.out.println("Note: only because it's beta version test - we asking for Customer ID.");
    		//System.out.println("\n" + "Type The OLD Company Name:");
     		
    		System.out.print("Customer ID: ");
            SharingData.setLongNum1(userInputLong());
            System.out.print("Customer Password:");
            SharingData.setVarchar1(userInputString());
            
     		// Check if the NAME exist..
            IsExistDB.idPassExistV2Customer(SharingData.getLongNum1(), SharingData.getVarchar1());
            if(IsExistDB.getAnswer() == false) {
     				
     		printNoExistOrCurrect();
     		printUsageMainOptions();
     		break;
     		} // if
     		else { // Move on to this block if we got 'TRUE' in the IF condition:
 	
     		printFoundInDB("Customer"); 
     				
     	    		
                    System.out.print("NEW Customer Name: ");
                    String name = userInputString();
     	    		
     	    		System.out.print("NEW Password: ");
     	    		String password = userInputString();
     	    		     	    		
     	    		long id = SharingData.getLongNum1();
     	    		
     	    		Customer c = new Customer();
     	    		CustomerDBDAO cusDB = new CustomerDBDAO(); 
     		     	
     	    		c.setCustName(name);
     	    		c.setPassword(password);
     	    		c.setId(id);
     	    		
     	    		cusDB.updateCustomer(c);

     	     		if(SharingData.isFlag1() == true) {
     	     			System.out.println("\n" + SharingData.getVarchar4());
     	     			System.out.println("------------ Customer Updated Successfully ----------" + "\n");
     	     			printGoingBackToUsage();
     	     			printUsageMainOptions();
     	    		} // if - is updated
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
    
    /************************************************
	 *****      Miscellaneous Section            ****
	 ************************************************
	 *
	 * All the methods and the functions in this section are not requested by
	 * the project guide.
	 * They are only for help and maintenance.
	 *
	 */
    
    private static void resetTable_T() throws SQLException {
    	
    	System.out.println("\n" + "This is a Developer method - Truncate Tabels (reset)");
    	System.out.println("Would you like to rest the Company Table?" + "\n");
		System.out.println("1. YES (delete and rest all the table data)."
				+ "\n" + "2. NO (exit to the main usage)"
				+ "\n");
		short choice = userInputShort();
		
		switch (choice) {
		
		case 1: { // Delete and Reset the Table
			
			try {
				DBconnector.getCon();
				String sql = "truncate table company";
				Statement stat = DBconnector.getInstatce().createStatement();
				stat.executeUpdate(sql);
				System.out.println("The table has been RESET!");
				
				stat.close();
				
			} catch (SQLException e) {
				e.getMessage();
			} // catch
			finally {
				DBconnector.getInstatce().close();
			}
		
			printGoingBackToUsage();
			break;
		} // case 1
		case 2: {
			
			break;
		}
		} // switch
    	
    }
    
} // Class	