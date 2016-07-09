package com.testpack;

import java.sql.*;
import java.time.*;
import java.util.*;

import com.added.*;
import com.added.functions.DBconnector;
import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dbdao.*;
import com.facade.*;
import com.javabeans.*;
import com.sun.webkit.SharedBuffer;
import com.*;

import sun.invoke.empty.Empty;
import sun.security.jgss.LoginConfigImpl;

/**
 * 
 * Read the next Description before using this program.
 * 
 * 
 * This is a Test Class for our Project. 
 * Attention! -> THIS IS NOT THE REQUESTED TEST IN THE PROJECT GUIDE INSTRUCTIONS - it's only for us, the Developers.
 *
 * This Test-Class is divided to Sections.
 * Every section has couple of methods and functions.
 * Every section has a job here.
 * It is my first real program - so be nice.
 * If you just want to test the program as a user, click play and run it.
 * But if you want to test it as a developer, PLEASE READ the notes in the section and the methods.
 * 
 * @author Raziel
 */

public class testDeveloers {

	
	
	// This static short is helping to the function userInput
	private static short counterWorngTimes = 0;
    
	/************************************************
	 ********     Section A: Main Class     *********
	 ************************************************
	 */
	
	public static void main(String[] args) throws SQLException {
		
		
		// Printing wellcom, loading JDBC Driver, Printing Main Usage.
		printWellcom();
		loadDriver();
		printMainTestChoice();
		
		

		// boolean just for the MAIN while loop for keeping it running.
		boolean on = true;
		
		while (on) {
			
			//printDbDAOMenu();
			short userChoiceOfSideWork = userInputShort();
			
			//Check the user choice and switch it:
			switch (userChoiceOfSideWork) {
			
			
			case 1: {
				Facade_T();
				break;
			} // case 1
			case 2: {
				DbDAO_T();
				break;
			}
			case 3: {
				printMainTestChoice();
				continue;
			}
			case 4: {
				printMainTestChoice();
				continue;
			}
			case 5: {
				printMainTestChoice();
				continue;
			}
			case 6: {
				printMainTestChoice();
				continue;
			}
			case 7: {
				printMainTestChoice();
				continue;

			}
			case 8: {
				printMainTestChoice();
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

	
	/*/**********************************************
	 ********      Section B: Print Part     ********
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

	public static void printDbDAOMenu(){ 
		
		System.out.println( "\n" + 
				 "DBDAO Usage: " + "\n"
				 + "You have 3 Main Option of Using this JavaProgram - Database: " + "\n");
		System.out.println("1. Compnay - Add, Remove, Update.."
				+ "\n" + "2. Customer - same as above."
				+ "\n" + "3. Coupon - same as above."
				+ "\n" + "8. Go Back The Main Usage Guide."
				+ "\n" + "0. To Quit" 
				+ "\n");
		
	}
	
	public static void printFacadeMenu() {
    	System.out.println( "\n" + 
				 "Facade Usage: " + "\n"
				 + "You have 3 Option: " + "\n");
		System.out.println("1. Admin Access."
				+ "\n" + "2. Company Access."
				+ "\n" + "3. Customer Access."
				+ "\n" + "8. Go Back The Main Usage Guide."
				+ "\n" + "0. To Quit" 
				+ "\n");
    } // printFacadeMenu - Function
	
	public static void printAdminFacadeMenu() {
		System.out.println( "\n" + 
				 "***** Admin Facade Usage ***** " + "\n");
		System.out.println("Company Part:");
		System.out.println("1. Create Company."
				+ "\n" + "2. Remove Company."
				+ "\n" + "3. Update Company"
				+ "\n" + "4. Get Company By ID."
				+ "\n" + "5. Get All Companies."
				+ "\n");
		System.out.println("Customer Part:");
		System.out.println("6. Create Customer."
				+ "\n" + "7. Remove Customer."
				+ "\n" + "8. Update Customer"
				+ "\n" + "9. Get Customer By ID."
				+ "\n" + "10. Get All Customers."
				+ "\n" + "11. Go Back The Main Usage Guide."
				+ "\n" + "0. Quit."
				+ "\n");
		
		
	} // printAdminFacadeMenu
	
	public static void printCompanyFacadeMenu() {
		System.out.println( "\n" + 
				 "***** Company Facade Usage ***** " + "\n");
		System.out.println("Options:");
		System.out.println("1. Create Coupon."
				+ "\n" + "2. Remove Coupon."
				+ "\n" + "3. Update Coupon"
				+ "\n" + "4. Get Coupon By ID."
				+ "\n" + "5. Get All Coupons of The Company."
				+ "\n" + "6. Get Coupons By Type."
				+ "\n");
	} // printCompanyFacadeMenu
	
	public static void printCustomerFacadeMenu() {
		
		System.out.println( "\n" + 
				 "Customer Side: " + "\n");
		System.out.println("1. Purchase Coupon."
				+ "\n" + "2. Get All Purchase Coupons."
				+ "\n" + "3. Get All Purchase Coupons By Type."
				+ "\n" + "4. Get All Purchase Coupons By Price."
				+ "\n" + "0. To Quit" 
				+ "\n");
		
	}
	
	public static void printMainTestChoice() {
		
		System.out.println( "\n" + 
				 "Developers Test Options: " + "\n"
				 + "You have 2 Options here: " + "\n");
		System.out.println("1. Facades Way - Only."
				+ "\n" + "2. DbDAO Way - Only."
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
	
	public static void printCouponRemoved() {
		System.out.println("\n" + "------------ Coupon Removed Successfully ----------" + "\n");

	}
	
	public static void printCustomerRemoved() {
		System.out.println("\n" + "------------ Customer Removed Successfully ----------" + "\n");

	}
	
	public static void printGoingBackToUsage() {
		
		System.out.println("Going back to Menu.." + "\n");

	}
	

	/*/***********************************************
	 ******   Section C: Scanner-INPUT Section  *****
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
			if (choice <= 8 && choice > -1 || choice == 822) {
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
			printMainTestChoice();
			// Counting the worng times of the user inputs:
			counterWorngTimes++;
			
			if (counterWorngTimes > 3 || counterWorngTimes < 0) {
				System.out.println("You've reached the 3 attempts You had. Exiting Program..");
				return 0;
			} // if - counterWorngTimes 
			return 8;
		} // catch
		
			
	} // userInputShort

	private static short userInputFadacesShort() {
		/*
		 * This is a Multi-Purpose Function.
		 * We will use this method anytime we want to input(By Scanner) SHORT variable FOR Facades.
		 */
        System.out.print("->> ");
		Scanner scanner = new Scanner(System.in);
		
		try {
			short choice = scanner.nextShort();
			if (choice < 11 && choice > -1) {
				return choice;
			} // IF - choice
			else {
				throw (new IllegalArgumentException());
			} // else
		} // try
		catch (InputMismatchException | IllegalArgumentException e) {
			System.out.println("Error - Please take a look again in the Usage.");
			// counter LEFT for counting back:
						int counterLEFT = 3 - counterWorngTimes;
						System.out.println("You have" + " ** " + counterLEFT + " ** " + "More Attempts To Fail.");

						printGoingBackToUsage();
						printMainTestChoice();
						// Counting the worng times of the user inputs:
						counterWorngTimes++;
						
						if (counterWorngTimes > 3 || counterWorngTimes < 0) {
							System.out.println("You've reached the 3 attempts You had. Exiting Program..");
							return 0;
						} // if - counterWorngTimes 
						return 8;
		}// catch
		
	}
	
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
	
	private static int userInputInt() {
		
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("->> ");
		
		try {
			int input = scanner.nextInt();
			return input;
		} catch ( InputMismatchException e) {
			System.out.println("\n" + "Error - YOU SHOULD TYPE A *NUMBERS ONLY* AND NOT A STRING!");
			System.out.println("When an ERROR like this occurs - the program will send ZERO (number) as default." + "\n");
		} // catch

	return 0;
	
}

	/*/***********************************************
	 ********     Section D: login & LoadDrive   ****
	 ************************************************
	 *
	 * This is the function Login and loadDriver.
	 * The login() will helps us in the next stages
	 * here.
	 *
	 */
	
	private static boolean login_T() {

		short choice = SharingData.getShortNum1();
		
		// Admin choice
		if (choice == 1) {
			System.out.println("\n" + "Please type your Admin-User and Password." + "\n");
	    	System.out.print("Type Your Admin User: ");
	    	String userName = userInputString();
	    	System.out.print("Type Your Admin Password: ");
	    	int password = userInputInt();
	    	
	    	// In the admin, we're just need to check pass&user without to go to the DB.
	    	AdminFacade ad = new AdminFacade();
	    	boolean existOrNot = ad.login(userName, password);
	    	
	   		if(existOrNot == false){
	        	printNoExistOrCurrect();
	        	printFacadeMenu();
	        	return false;
	        	} // if
	        	else {
	        		printAdminFacadeMenu();
	        		return true;
	        	} // else
		} // if - Admin
		// Company choice
		else if (choice == 2) {
			System.out.println("\n" + "Please type your Company-ID and Password." + "\n");
	    	System.out.print("Type Your Company ID: ");
	    	long compID = userInputLong();
	    	System.out.print("Type Your Company Password: ");
	    	String password = userInputString();
	    	
	    	CompanyFacade co = new CompanyFacade();
	    	boolean existOrNot = co.login(compID, password);
	    	
	   		if(existOrNot == false){
	        	printNoExistOrCurrect();
	        	return false;
	        	} // if
	        	else {
	        		printFoundInDB("Company");
	        		SharingData.setIdsShare(compID);
	        		return true;
	        	} // else
		} // else if - Company
		// Customer choice
		else if (choice == 3) {
			System.out.println("\n" + "Please type your Customer-ID and Password." + "\n");
	    	System.out.print("Type Your Customer ID: ");
	    	long custID = userInputLong();
	    	System.out.print("Type Your Customer Password: ");
	    	String password = userInputString();
	    	
	    	CustomerFacade cusF = new CustomerFacade();
	    	boolean existOrNot = cusF.login(custID, password);
	    	
	   		if(existOrNot == false){
	        	printNoExistOrCurrect();
	        	printFacadeMenu();
	        	return false;
	        	} // if
	        	else {
	        		printFoundInDB("Customer User");
	        		return true;
	        	} // else
		} // else if - Customer
		
		return false;

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
	
	/*/***********************************************
	 ********   Section E: DbDAO Switch Part   *******
	 *************************************************
	 *
	 * This the section of all the DbDAO functions and methods.
	 * Here we create the connection between all the methods of DAO and DB.
	 *
	 */
    
    private static void DbDAO_T() throws SQLException{
    	
    	printDbDAOMenu();
    	//printDbDAOMenu();
		short userChoiceOfSideWork = userInputShort();
		
		//Check the user choice and switch it:
		switch (userChoiceOfSideWork) {
		
		
		case 1: {
			CompanyMenu();
			break;
		} // case 1
		case 2: {
			//printDbDAOMenu();
			CustomerMenu();
			break;
		}
		case 3: {
			CouponMenu();
			break;
		}
		case 4: {
			//printDbDAOMenu();
			System.out.println("Still not ready..");
			break;
		}
		case 5: {
			//printDbDAOMenu();
			System.out.println("Still not ready..");
			break;
		}
		case 6: {
			//printDbDAOMenu();
			System.out.println("Still not ready..");
			break;
		}
		case 7: {
			//printDbDAOMenu();
			System.out.println("Still not ready..");
			break;

		}
		case 8: {
			printDbDAOMenu();
			
		}
		case 0: {
			System.out.println("Thank You! Bye Bye :) ");
			break;
		}
		} // Switch

    	
    }
	
	/*/***********************************************
	 ********      Section F: CompanyDB & DAO  ******
	 ************************************************
	 *
	 * This the section of all the company function and method.
	 * Here we create the connection between all the methods of CompanyDB and CompanyDAO 
	 * from the packages in the Coupon System Project.
	 *
	 */
	
	 // TODO: to change all of this section to Facades access!

    
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
        		//printDbDAOMenu();
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
        		printDbDAOMenu();
        		break;
        	}
        	case 822: { // Developers Option: Reset Table Company
        		resetTable_T();
        		printDbDAOMenu();
        		break;
        	}

        	case 0: {
        		//printDbDAOMenu();
        		break;
        	}
        	
        	} // switch
    	
    	
    } // CompanySwitch Function
    
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
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
				break;
			}
			Company c = new Company();
			AdminFacade ad = new AdminFacade();
			
			c.setCompName(name);
			c.setEmail(email);
			c.setPassword(password);
			ad.createCompanyA(c);
			
			if(SharingData.isFlag1() == true) {
				System.out.println(c.toString());
				System.out.println("------------ Company Added Successfully ----------" + "\n");
				
			}
			else {
				System.out.println(c.toString());
				System.out.println("\n" + "****************************************************");
    			System.out.println("Error - No Changes Were Made :(");
				System.out.println("\n" + "****************************************************");
			} // else
			
			
			System.out.println("Whould you keep adding companies? Type '1' for YES or any other Number for NO.");
			short choice1 = userInputShort();
			
			if (choice1 != 1) {
				printAdminFacadeMenu();
				break;
			} // if 
			else {
				continue;
			}
		} // while loop
	} // addCompnay - Function
	
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void removeCompany_T() {
    	
    	while(true) {
    		
    		Company company = new Company();
    		AdminFacade adminF = new AdminFacade();
    			
    			System.out.print("Type Your Company ID: ");
    			SharingData.setLongNum1(userInputLong());
    			if (SharingData.getLongNum1() == 0) {
    				
    				printGoingBackToUsage();
    				//printAdminFacadeMenu();
    				break;
    			}
    			
    			IsExistDB.idExist(SharingData.getLongNum1());
    			if(IsExistDB.getAnswer2() == false) {		
         		printNoExistOrCurrect();
         		//printAdminFacadeMenu();
         		break;
    			}
    			else {	
    			      try {
    			    	  company.setId(SharingData.getLongNum1());
    			    	  adminF.removeCompanyA(company);
    			    	  
					  } catch (Exception e) {
						System.out.println("Error:");
						System.out.println(e.getMessage());
					  }
        			  
    			      printCompanyRemoved();
    			} // else
    		
    			System.out.println("Whould you like to Removing more companies? Type '1' for YES or any other Number for NO.");
    			short choice = userInputShort();
    			
    			if (choice == 1) {
    				continue;
    			} // if 
    			else {
    				printAdminFacadeMenu();
    				break;
    			} // else
    		} // while
    } // removeCompany_T
    		
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void updateCompany_T() {
    	
    	while(true) {
    		Company c = new Company();
    		
    		System.out.println("Update Company:" + "\n");
    		System.out.println("Please type your current Company ID and Password.");
    		System.out.println("Note: only because it's beta version test - we asking for Company ID.");
    		//System.out.println("\n" + "Type The OLD Company Name:");
     		
    		System.out.print("Company ID: ");
            c.setId(userInputLong());
                  

     		// Check if the NAME exist..
            IsExistDB.idExist(c.getId());
            if(IsExistDB.getAnswer2() == false) {
     				
     		printNoExistOrCurrect();
     		printAdminFacadeMenu();
     		break;
     		} // if
     		else { // Move on to this block if we got 'TRUE' in the IF condition:
 	    		AdminFacade admF = new AdminFacade();
 	    		
 	    		Company ca = new Company();
 	    		ca = admF.getCompanyA(c.getId());
     		        printFoundInDB("Company"); 
     	    		System.out.print("NEW Email: ");
     	    		ca.setEmail(userInputString());
     	            ca.setPassword(c.getPassword());
     	            
     		     	
     	    		System.out.println(c.getCompName());
     	    		
     	    		//ca.setCompName(c.getCompName());
     	    		ca.setEmail(ca.getEmail());
     	    		ca.setPassword(ca.getPassword());
     	    		//ca.setId(c.getId());
     	    		//c.setPassword(password);
     	    		
     	     		admF.updateCompanyA(ca);

     	     		if(SharingData.isFlag1() == true) {
     	     			System.out.println("\n" + SharingData.getVarchar4());
     	     			System.out.println("------------ Company Updated Successfully ----------" + "\n");
     	     			printGoingBackToUsage();
     	     			//printAdminFacadeMenu();
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
    
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void getCompanyID_T() {
    	
    	while(true) {
        	System.out.println("Type The Company ID:");
        	SharingData.setLongNum1(userInputLong());
        	
        	IsExistDB.idExist(SharingData.getLongNum1());
        	if(SharingData.getLongNum1() == 0) {
        		System.out.println("Typing 'Zero' is mean = quit..");
        		printGoingBackToUsage();
        		//printDbDAOMenu();
        		break;
        	} // if - it 0 the program will break from this function.
        	
        	if (IsExistDB.getAnswer2() == false) { // checks if the ID exist in the DB.
        		printNoExistOrCurrect();
    			//printAdminFacadeMenu();
    			break;
        	} // if - isExist
        	else {
        		AdminFacade admF = new AdminFacade();
        		admF.getCompanyA(SharingData.getLongNum1());
        		
        		// Print the Company:
        		System.out.println(SharingData.getVarchar2());
        		
        		if(SharingData.isFlag1() == true) {
	    			System.out.println("\n" + "------------ Company Function (getID) Was Run Successfully ----------" + "\n");
        	}
        	} // else
        	
    	} // while loop

    	} // getCompanyID_T - Function

    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void getAllCompanies_T() {

    	while(true) {
       			AdminFacade admF = new AdminFacade();
    			System.out.println("Here is your Companeis List: " + "\n");
       			
    			System.out.println(admF.getAllCompaniesA().toString());
    			break;
    	} // while loop
    	
    } // getAllCompanies_T - Function
    
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void addCustomer_T() {
    	
    	while (true){
    		
            System.out.print("NEW Customer Name: ");
    		String name = (userInputString());
    		
            System.out.print("NEW Customer Password: ");
            String password = (userInputString());
    		
    		// check if the user put's somthing empty...
    		
    		if(name.isEmpty() || password.isEmpty()) {
    			System.out.println("\n" + "Error - the fields are empty!");
    			printGoingBackToUsage();
    			printAdminFacadeMenu();
    			break;
    		}
    		Customer c = new Customer();
    		AdminFacade admF = new AdminFacade();
    		
    		c.setCustName(name);
    		c.setPassword(password);
    		admF.createCustomerA(c);
    		
    		if(SharingData.isFlag1() == true) {
    			System.out.println(c.toString());
    			System.out.println("------------ Customer Added Successfully ----------" + "\n");
        		printUsageCustomer();
    			
    		}
    		else {
    			System.out.println(admF.toString());
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
    			printDbDAOMenu();
    			break;
    		}
    	
    	} // while loop
    } // addCompnay - Function
        
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void removeCustomer_T() {
    	
    	
    	while(true) {
    		
    		AdminFacade admF = new AdminFacade();
    		
    			System.out.print("Type Your Customer ID: ");
    			SharingData.setLongNum1(userInputLong());
    			if (SharingData.getLongNum1() == 0) {
    				
    				printGoingBackToUsage();
    				printDbDAOMenu();
    				break;
    			}
    			
    			IsExistDB.idExistV2Customer(SharingData.getLongNum1());
    			if(IsExistDB.getAnswer2() == false) {		
         		printNoExistOrCurrect();
         		printAdminFacadeMenu();
         		break;
    			}
    			else {	
    				Customer cus = new Customer();
    				cus.setId(SharingData.getLongNum1());
    			      admF.removeCustomerA(cus);
    			      //System.out.println(SharingData.getVarchar4());
        			  
    			      printCustomerRemoved();
    			} // else
    		
    		System.out.println("Whould you like to Removing more Customers? Type '1' for YES or any other Number for NO.");
    		short choice1 = userInputShort();
    		
    		if (choice1 == 1) {
    			continue;
    		} // if 
    		else {
    			printDbDAOMenu();
    			break;
    		} // else
    		} // while loop
    		
    	} // removeCompany - plan
       
	
    /*/***********************************************
	 ********   Section G: Customer Section   *******
	 ************************************************
	 *
	 * This the section of all the customer function and method.
	 * Here we create the connection between all the methods of CustomerDB and CustomerDAO 
	 * from the packages in the Coupon System Project.
	 *
	 */
    
	 // TODO: to change all of this section to Facades access!

    public static void printUsageCustomer() {

    	// this function is unused in Facade section!
    	// it's only for the DbDAO check part.
		
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
        		getCustomerID_T();
        		break;
        	}
        	case 5: {
        		getAllCustomers_T();
        		break;
        	}
        	case 822: { // Developers Option: Reset Table Company
        		resetTable_T();
        		printDbDAOMenu();
        		break;
        	}

        	case 0: {
        		printDbDAOMenu();
        		break;
        	}
        	
        	} // switch
    	
    } // CompanyMenu - Function

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
     		printDbDAOMenu();
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
     	     			printDbDAOMenu();
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
    
    private static void getCustomerID_T() {
    	
    	while(true) {
        	System.out.println("Type The Customer ID:");
        	SharingData.setLongNum1(userInputLong());
        	
        	IsExistDB.idExistV2Customer(SharingData.getLongNum1());
        	if(SharingData.getLongNum1() == 0) {
        		System.out.println("Typing 'Zero' is mean = quit..");
        		printGoingBackToUsage();
        		printDbDAOMenu();
        		break;
        	} // if - it 0 the program will break from this function.
        	
        	if (IsExistDB.getAnswer2() == false) { // checks if the ID exist in the DB.
        		printNoExistOrCurrect();
    			printDbDAOMenu();
    			break;
        	} // if - isExist
        	else {
        		CustomerDBDAO cust = new CustomerDBDAO();
        		cust.getCustomer(SharingData.getLongNum1());
        		
        		// Print the Customer:
        		System.out.println(SharingData.getVarchar2());
        		
        		if(SharingData.isFlag1() == true) {
	    			System.out.println("\n" + "------------ Customer Function (getID) Was Run Successfully ----------" + "\n");
        	}
        	} // else
        	
    	} // while loop

    	} // getCompanyID_T - Function
    private static void getAllCustomers_T() {

    	while(true) {
    		
    		System.out.println("Please Type Your Admin Password:");
    		long password = userInputLong();
    		
    		if(password == 123456789) {
       			CustomerDBDAO db = new CustomerDBDAO();
    			System.out.println("Here is your Companeis List: " + "\n");
       			
    			System.out.println("\n" + db.getAllCustomers().toString());
    			break;
    		} // if
    		else {
 
    			System.out.println("Error: worng password!");
    			printGoingBackToUsage();
    			break;
    			
    		} // else    		
    	} // while loop
    	
    } // getAllCompanies_T - Function
    
    
    /**
     * This Function is blong ONLY to CustomerFacade Access.
     * @category CustomerFacade Section
     * @author Raziel
     */
    
    private static void purchaseCoupon_T() {
    	Scanner sc = new Scanner(System.in);

    	while(true) {

    	System.out.println("\n" + "Now you can purchase a coupon by is ID. " 
    			+ "\n" + "But if you don'y know the ID or which coupon do your want, "
    			+ "please type '0' and go back to Customer Side Menu and check with Get's Options." + "\n");
    	
    	System.out.println("Type the Coupon ID (Or '0' for exit): ");
    	long id = sc.nextShort();
    	if(id == 0) {
    		printGoingBackToUsage();
    		break;
    	}
    	IsExistDB.idExistV2Coupon(id, "Coupon");
		if(IsExistDB.getAnswer2() == false) {
			break;
		} // if - false
		else {
	    	CustomerFacade cusF = new CustomerFacade();
	    	Coupon coupon = new Coupon();
	    	coupon.setId(id);
	    	coupon = cusF.purchaseCoupon(coupon);
	    	
	    	System.out.println(coupon.toString());
	    	System.out.println("------------ Coupon Added Successfully ----------" + "\n");
		}
    	
    	} // while loop
    	
    } // purchaseCoupon_T
    
    
    /*/***********************************************
	 ********   Section H: Coupon Section   *******
	 ************************************************
	 *
	 * This the section of all the coupon function and method.
	 * Here we create the connection between all the methods of CouponDB and CouponDAO 
	 * from the packages in the Coupon System Project.
	 */
	 // TODO: to change all of this section to Facades access!
    
    public static void printUsageCoupon() {

		
		System.out.println( "\n" + 
				 "Coupon Side: " + "\n"
				 + "You Have Couple of Options Here (DB is DataBase): " + "\n");
		System.out.println("1. Add a Coupon to the DB."
				+ "\n" + "2. Remove a Coupon from the DB."
				+ "\n" + "3. Update a Coupon in the DB."
				+ "\n" + "4. Get a Specific Coupon (By ID) from the DB."
				+ "\n" + "5. Get the List of all Coupons in the DB."
				+ "\n" + "0. To Quit" 
				+ "\n");
		
	}
    
    private static void CouponMenu() throws SQLException {
        
    	printUsageCoupon();
    	short choice = userInputShort();
    	switch (choice) {
    	
    	case 1: {
    		addCoupon_T();
    		break;
    	}
    	case 2: {
    		removeCoupon_T();
    		break;
    	}
    	case 3: {
    		updateCoupon_T();
    		break;
    	}
    	case 4: {
    		getCoupon_T();
    		break;
    	}
    	case 5: {
    		getAllCoupon_T();
    		break;
    	}
    	case 822: { // Developers Option: Reset Table Company
    		resetTable_T();
    		printDbDAOMenu();
    		break;
    	}

    	case 0: {
    		printDbDAOMenu();
    		break;
    	}
    	
    	} // switch
	
} // CompanyMenu - Function
    
    private static void addCoupon_T() throws SQLException {
    	
	while (true){

		Coupon coup = new Coupon();
		CompanyFacade comF = new CompanyFacade();
		
		System.out.print("NEW Title: ");
        String title = userInputString();
    	
    	System.out.print("NEW Coupon StartDate.. ");
		LocalDate startDate = null;
		System.out.print("Day: ");
		int startDay = userInputInt();
		System.out.print("Month: ");
		int startMonth = userInputInt();
		System.out.print("Year: ");
		int startYear = userInputInt();
		startDate = LocalDate.of(startYear, startMonth, startDay);
		
		System.out.print("NEW Coupon EndDate.. ");
		LocalDate endDate = null;
		System.out.print("Day: ");
		int endDay = userInputInt();
		System.out.print("Month: ");
		int endMonth = userInputInt();
		System.out.print("Year: ");
		int endYear = userInputInt();
		endDate = LocalDate.of(endYear, endMonth, endDay);
    	
		System.out.print("NEW Amount: ");
        int amount = userInputInt();
		
        System.out.print("In Category: ");
        String category = userInputString();
        
        System.out.print("NEW Massage: ");
        String message = userInputString();
        
        System.out.print("NEW Price: ");
        Scanner sc = new Scanner(System.in);
        double price = sc.nextDouble();
        
        //sc.close();
        
        System.out.print("NEW Imag link: ");
        String imag = userInputString();
        
        long ownerID = SharingData.getIdsShare();
        
		// putting all the variables 
		coup = new Coupon(title, startDate, endDate, amount, CouponType.valueOf(category), message, price, imag, ownerID);
        
		// check if the user put's somthing empty...
		if(title.isEmpty() || message.isEmpty() || category.isEmpty() || message.isEmpty() || imag.isEmpty() || price == 0) {
			
			System.out.println("\n" + "Error - the fields are empty!");
			printGoingBackToUsage();
			printDbDAOMenu();
			break;
		}
		
		comF.createCouponF(coup);
		
		if(SharingData.isFlag1() == true) {
			System.out.println(coup.toString());
			System.out.println("------------ Coupon Added Successfully ----------" + "\n");
		}
		else {
			//System.out.println(coup.toString());
			System.out.println("\n" + "****************************************************");
			System.out.println("Error - No Changes Were Made :(");
			System.out.println("\n" + "****************************************************");
		} // else
				
		System.out.println("Whould you keep adding Coupons? Type '1' for YES or any other Number for NO.");
		short choice1 = userInputShort();
		
		if (choice1 == 1) {
			continue;
		} // if 
		else {
			printDbDAOMenu();
			break;
		}
	
	} // while loop
} // addCompnay - Function
    
    private static void removeCoupon_T() {
    	
    	while(true) { 
    		
    		CouponDBDAO db = new CouponDBDAO();
    		
    		System.out.println("Developers Only! Deleting By Coupon Object..");
    		System.out.print("Type Your Coupon ID: ");
    		SharingData.setLongNum1(userInputLong());
    		if (SharingData.getLongNum1() == 0) {
    			
    			printGoingBackToUsage();
    			printDbDAOMenu();
    			break;
    		}
    		
    		IsExistDB.idExistV2Coupon(SharingData.getLongNum1(), "coupon");
    		if(IsExistDB.getAnswer2() == false) {		
    			IsExistDB.idExistV2Coupon(SharingData.getLongNum1(), "company_coupon");	
    			} // if - coupon
    		else if(IsExistDB.getAnswer2() == false) {		
        		IsExistDB.idExistV2Coupon(SharingData.getLongNum1(), "customer_coupon");
			} // if - company_coupon
    		else if(IsExistDB.getAnswer2() == false) {		
				printNoExistOrCurrect();
				printDbDAOMenu();
				break;
			} // if - customer_coupon
    		else {
    			Coupon c = new Coupon();
				c.setId(SharingData.getLongNum1());
				System.out.println(c.toString());
				db.removeCoupon(c);
				printCouponRemoved();
    		} // else
    	} // while loop
    	
    } // removeCoupon
    
    private static void updateCoupon_T() {
    	
    	while(true) {
    	// TODO: ATTENTION! I have to put here ownerID from the company ID!
		System.out.print("Coupon ID: ");
        SharingData.setLongNum1(userInputLong());
    	
        // Check if the NAME exist..
        IsExistDB.idExistV2Coupon(SharingData.getLongNum1(), "coupon");
        if(IsExistDB.getAnswer2() == false) {
 				
 		printNoExistOrCurrect();
 		printDbDAOMenu();
 		break;
 		} // if
        else {
        	printFoundInDB("Coupon");
        	
        	// Object Coupon
        	Coupon coup;
        	
        	// DBDAO Coupon
        	CouponDBDAO dbcou = new CouponDBDAO();
        	
        	//System.out.print("Please Provide The ID Coupon: ");
            long id = SharingData.getLongNum1();
        	
        	System.out.print("NEW Title: ");
            String title = userInputString();
	    	
            System.out.print("In Category: ");
            String category = userInputString();
            CouponType type = CouponType.valueOf(category);
            
	    	System.out.print("NEW Coupon StartDate.. ");
			LocalDate startDate = null;
			System.out.print("Day: ");
			int startDay = userInputInt();
			System.out.print("Month: ");
			int startMonth = userInputInt();
			System.out.print("Year: ");
			int startYear = userInputInt();
			startDate = LocalDate.of(startYear, startMonth, startDay);
			
			System.out.print("NEW Coupon EndDate.. ");
			LocalDate endDate = null;
			System.out.print("Day: ");
			int endDay = userInputInt();
			System.out.print("Month: ");
			int endMonth = userInputInt();
			System.out.print("Year: ");
			int endYear = userInputInt();
			endDate = LocalDate.of(endYear, endMonth, endDay);
	    	
			System.out.print("NEW Amount: ");
            int amount = userInputInt();
            
            System.out.print("NEW Massage: ");
            String massage = userInputString();
            
            System.out.print("NEW Price: ");
            Scanner sc = new Scanner(System.in);
            double price = sc.nextDouble();
            //sc.close();
            
            System.out.print("NEW Imag link: ");
            String imag = userInputString();
            
            // TODO: change it to CompanyID after I will get here in the CompanyFacade.
            long ownerID = 0;
            
            // TODO: keep it - and be aware to the category.
			// putting all the variables 
           
			coup = new Coupon(title, startDate, endDate, amount, type, massage, price, imag, ownerID);
			coup.setId(id);
			dbcou.updateCoupon(coup);
			System.out.println("\n" + coup.toString() + "\n");
			break;
        }
        
    	} // while loop
    }
    
    private static void getCoupon_T() {
    	
    	while(true) {
        	System.out.println("Type The Coupon ID:");
        	SharingData.setLongNum1(userInputLong());
        	
        	IsExistDB.idExistV2Coupon(SharingData.getLongNum1(), "coupon");
        	if(SharingData.getLongNum1() == 0) {
        		System.out.println("Typing 'Zero' is mean = quit..");
        		printGoingBackToUsage();
        		printDbDAOMenu();
        		break;
        	} // if - it 0 the program will break from this function.
        	
        	if (IsExistDB.getAnswer2() == false) { // checks if the ID exist in the DB.
        		printNoExistOrCurrect();
    			printDbDAOMenu();
    			break;
        	} // if - isExist
        	else {
        		CouponDBDAO coup = new CouponDBDAO();
        		coup.getCoupon(SharingData.getLongNum1());
        		
        		// Print the Coupon:
        		System.out.println(SharingData.getVarchar2());
        		
        		if(SharingData.isFlag1() == true) {
	    			System.out.println("\n" + "------------ Coupon Function (getID) Was Run Successfully ----------" + "\n");
        	}
        	} // else
        	
    	} // while loop

    	} // getCouponID_T - Function
    private static void getAllCoupon_T() {

    	while(true) {
    		
       			CompanyFacade compF = new CompanyFacade();
    			System.out.println("Here is your Couponos List: " + "\n");
       			System.out.println(compF.getAllCoupons(SharingData.getIdsShare()));
    			break;
    			
    	} // while loop
    	
    } // getAllCoupon_T - Function
      
    /*/***********************************************
	 ********   Section I: Facade Test Section   *******
	 ************************************************
	 *
	 * This the section of all the Facades functions and methods.
	 * Here you can test it by the 3 UserType below (in the 'prinFacadeMenu()').
	 *
	 */
    
    

    private static void AdminFacade_T() throws SQLException {
    	
    	boolean existOrNot = login_T();
    	
    	if(existOrNot != true) {
    		printIDnotExist("User");
    	}
    	else {
    		
    	    boolean on = true;
    	    while(on == true) {
        	
        	printAdminFacadeMenu();
        	
    		short userChoiceOfSideWork = userInputFadacesShort();
    		//AdminFacade adminF = new AdminFacade();
    		
    		
    		//Check the user choice and switch it:
    		switch (userChoiceOfSideWork) {
    		
    		
    		case 1: {
    			addCompnay_T();
    			break;
    		} // case 1
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
    			break;
    		}
    		case 6: {
    			addCustomer_T();
    			break;
    		}
    		case 7: {
    			removeCustomer_T();
    			break;

    		}
    		case 8: {
    			printGoingBackToUsage();
    			printFacadeMenu();
    			break;
    		}
    		case 0: {
    			System.out.println("Thank You! Bye Bye :) ");
    			on = false;
    			break;
    		}
    		} // Switch
        	} // while loop
    	} // else
    } // AdminFacade_T
    
    private static void CompanyFacade_T() throws SQLException {
    	
        boolean existOrNot = login_T();
    	
    	if(existOrNot != true) {
    		//printIDnotExist("Company");
    	}
    	else {
    		
    	    boolean on = true;
    	    while(on == true) {
        	
        	printCompanyFacadeMenu();
        	
    		short userChoiceOfSideWork = userInputFadacesShort();
    		//AdminFacade adminF = new AdminFacade();
    		
    		
    		//Check the user choice and switch it:
    		switch (userChoiceOfSideWork) {
    		
    		
    		case 1: {
    			addCoupon_T();
    			break;
    		} // case 1
    		case 2: {
    			removeCoupon_T();
    			break;
    		}
    		case 3: {
    		System.out.println("Still not ready..");
    			continue;
    		}
    		case 4: {
        		System.out.println("Still not ready..");
    			continue;
    		}
    		case 5: {
        		getAllCoupon_T();
    			break;
    		}
    		case 6: {
        		System.out.println("Still not ready..");
    			continue;
    		}
    		case 7: {
        		System.out.println("Still not ready..");
    			continue;

    		}
    		case 8: {
    			printGoingBackToUsage();
    			printFacadeMenu();
    			break;
    		}
    		case 0: {
    			System.out.println("Thank You! Bye Bye :) ");
    			on = false;
    			break;
    		}
    		} // Switch
        	} // while loop
    	} // else
    	
    } // CompanyFacade_T
    
    private static void CustomerFacade_T() throws SQLException{
    	
    	boolean existOrNot = login_T();
     	
     	if(existOrNot != true) {
     		printIDnotExist("Customer");
     	}
     	else {
     		
     	    boolean on = true;
     	    while(on == true) {
         	printCustomerFacadeMenu();
         	
     		short userChoiceOfSideWork = userInputFadacesShort();
  
     		//Check the user choice and switch it:
     		switch (userChoiceOfSideWork) {   		
     		
     		case 1: {
     			purchaseCoupon_T();
     			break;
     		} // case 1
     		case 2: {
     			removeCoupon_T();
     			break;
     		}
     		case 3: {
     		System.out.println("Still not ready..");
     			continue;
     		}
     		case 4: {
         		System.out.println("Still not ready..");
     			continue;
     		}
     		case 5: {
         		System.out.println("Still not ready..");
     			continue;
     		}
     		case 6: {
         		System.out.println("Still not ready..");
     			continue;
     		}
     		case 7: {
         		System.out.println("Still not ready..");
     			continue;

     		}
     		case 8: {
     			printGoingBackToUsage();
     			printFacadeMenu();
     			break;
     		}
     		case 0: {
     			System.out.println("Thank You! Bye Bye :) ");
     			on = false;
     			break;
     		}
     		} // Switch
         	} // while loop
     	} // else
     
    	
    } // CustomerFacade_T
    
    private static void Facade_T() throws SQLException{
    	
    	// just for the loop.
    	boolean on = true;
    	
    	while(on == true) {
    	
    	printFacadeMenu();
    	
		SharingData.setShortNum1(userInputShort());
		
		//Check the user choice and switch it:
		switch (SharingData.getShortNum1()) {
		
		
		case 1: {
			AdminFacade_T();
			break;
		} // case 1
		case 2: {
			CompanyFacade_T();
			break;
		}
		case 3: {
			CustomerFacade_T();
			break;
		}
		case 4: {
			//printFacadeMenu();
			continue;
		}
		case 5: {
			//printFacadeMenu();
			continue;
		}
		case 6: {
			//printFacadeMenu();
			continue;
		}
		case 7: {
			//printFacadeMenu();
			continue;

		}
		case 8: {
			printGoingBackToUsage();
			break;
		}
		case 0: {
			System.out.println("Thank You! Bye Bye :) ");
			on = false;
			printGoingBackToUsage();
			printMainTestChoice();
			break;
		}
		
		} // Switch
    	} // while loop

    	
    }


    /*/***********************************************
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