package com.testpack;

import java.sql.*;
import java.time.*;
import java.util.*;

import com.beans.*;
import com.exceptionerrors.*;
import com.facade.*;
import com.task.and.singleton.CouponClientFacade;
import com.task.and.singleton.CouponSystem;
import com.task.and.singleton.DBconnector;


/**
 * 
 * Read the next Description before using this program</p>
 * 
 * 
 * This is a Test Class for our Project.</p> 
 * This test was built during the construction methods and other 
 * requirements for the first phase of the project.
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
	
	private static CouponClientFacade client;
	private static CompanyFacade compF;
	private static CustomerFacade cusF;
	private static AdminFacade admF;
    
	/**
	 ********     Section A: Main Class     *********
	 *
	 * This is the main class of the program.
	 * we have here only 'switch' who calling to all options and function.
	 *
	 *@author Raziel
	 */
	
	public static void main(String[] args) throws SQLException {
		
		
		// Printing wellcom, loading JDBC Driver.
		printWellcom();
		loadDriver();
		
		Facade_T();
		
		
		// When we finished - close the connection + Daily Task Thread.
		try {
			CouponSystem.getInstance().stop();
		} catch (ConnectorException e) {	
			System.out.println(e.getMessage());
		}

	} // main
	
	/*/**********************************************
	 ********      Section B: Print Part     ********
	 ************************************************
	 *
	 * This is a Multi-Use Functions - Main Prints, userInputs, loadDriver and more.
	 *
	 */

	public static void printWellcom() {
		System.out.println("Wellcom To Our Coupon System Management - Working with DataBase (Based on MySQL) :)");
		System.out.println("Written By Raziel, Avi and Yiftach.");
		System.out.println("Beta Version: 1.0");
		System.out.println("******************");

	}
	
	public static void printFacadeMenu() {
    	System.out.println( "\n" + 
				 "Facade Usage: " + "\n"
				 + "You have 2 Option: " + "\n");
		System.out.println("1. Admin Access."
				+ "\n" + "2. Company Access."
				+ "\n" + "3. Customer Access."
				+ "\n" + "0. To Quit" 
				+ "\n");
    } // printFacadeMenu - Function
	
	public static void printAdminFacadeMenu() {
		System.out.println( "\n" + 
				 "***** Admin Facade Usage ***** " + "\n");
		System.out.println("Company Part: ");
		System.out.println("1. Create Company."
				+ "\n" + "2. Remove Company."
				+ "\n" + "3. Update Company"
				+ "\n" + "4. Get Company By ID."
				+ "\n" + "5. Get All Companies."
				+ "\n");
		System.out.println("Customer Part: ");
		System.out.println("6. Create Customer."
				+ "\n" + "7. Remove Customer."
				+ "\n" + "8. Update Customer"
				+ "\n" + "9. Get Customer By ID."
				+ "\n" + "10. Get All Customers."
				+ "\n");
		System.out.print("Extra Part: ");
		System.out.print(
				  "\n" + "11. Remove Coupon."
				+ "\n" + "12. Get Coupons By Type (Category)."
				+ "\n" + "13. Get All The Coupons Up to Maxium-Price."
				+ "\n" + "14. Get All Coupons in the DataBase."
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
				+ "\n" + "4. Get Your Company Details."
				+ "\n" + "5. Get Coupon By ID."
				+ "\n" + "6. Get All Coupons of The Company."
				+ "\n" + "7. Get Coupons By Type."
				+ "\n" + "8. Get Coupons By Price."
				+ "\n" + "0. Quit."
				+ "\n");
	} // printCompanyFacadeMenu
	
	public static void printCustomerFacadeMenu() {
		
		System.out.println( "\n" + 
				 "Customer Side: " + "\n");
		System.out.println("1. Purchase Coupon."
				+ "\n" + "2. Get All Purchase Coupons."
				+ "\n" + "3. Get All Purchase Coupons By Type."
				+ "\n" + "4. Get All Purchase Coupons By Price."
				+ "\n" + "5. Get Your Customer Details." 
				+ "\n" + "6. Get Your FULL Customer Details (with your coupons)." 
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
	
	public static void printDuplicatedName(String type) {
		System.out.println("Duplicate " + type + " name!");
		System.out.println("Please insert another NAME.");
	}

	public static void printExceptionAsMessages() {
		System.out.println("\n" + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("Error: " + SharingData.getExceptionMessage());
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+ "\n");

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
		
		@SuppressWarnings("resource")
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
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		try {
			short choice = scanner.nextShort();
			if (choice <= 14 && choice > -1) {
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
						//printMainTestChoice();
						// Counting the worng times of the user inputs:
						counterWorngTimes++;
						
						if (counterWorngTimes > 3 || counterWorngTimes < 0) {
							System.out.println("You've reached the 3 attempts You had. Exiting Program..");
							return 0;
						} // if - counterWorngTimes 
						return 0;
		}// catch
		
	}
	private static long userInputLong() {
		
		
			@SuppressWarnings("resource")
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
		//@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		//scanner.close();
		return scanner.nextLine();
		

	} // userInputString
	
	private static Double userInputDouble(){
		System.out.print("->> ");
		//@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		//scanner.close();
		return scanner.nextDouble();
		

	} // userInputString
	
	private static int userInputInt() {
		
		
		@SuppressWarnings("resource")
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

		// Choice is the User-choice of the main menu.
		short choice = SharingData.getShortNum1();
		boolean running = false;
		
		while(!running) {
			
			// Admin choice
			if (choice == 1) {
				System.out.println("\n" + "Please type your Admin-User and Password." + "\n");
		    	System.out.print("Type Your Admin User: ");
		    	String userName = userInputString();
		    	System.out.print("Type Your Admin Password: ");
		    	String password = userInputString();
		    	
		    	// In the admin, we're just need to check pass&user without to go to the DB.
		    	
		    	CouponClientFacade adminClient = null;
				try {
					AdminFacade ad = new AdminFacade();
					client = CouponSystem.getInstance().login(userName, password, ClientType.ADMIN);
					adminClient = client;
					admF = ad.login(userName, password, ClientType.ADMIN);
					
				} catch (LoginException | ConnectorException e) {
					System.out.println("\n" + e.getMessage());
					break;
				} catch (DaoException e) {
					
					System.out.println("\n" + e.getMessage());
					break;
				}
		    	boolean flag = false;
		    	
		    	/*
		    	 * Explain of this IF condition:
		    	 * The question is ONLY if 'client' is not Null && 'client'.class is equals to adminfacade.class then it's ok.
		    	 */
		    	
		    	if(client.equals(adminClient)) {
		    		flag = true;
		    		running = false;
		        	return flag;
		        	} // if
		        	else {
		        		return false;
		        	} // else
			} // if - Admin
			// Company choice
			else if (choice == 2) {
				
//				CompanyFacade compF = null;
//		    	CouponClientFacade client = null;
		    	
				System.out.println("\n" + "Please type your Company-ID and Password." + "\n");
		    	System.out.print("Type Your Company Name: ");
		    	String userName = userInputString();
		    	System.out.print("Type Your Company Password: ");
		    	String password = userInputString();
		    	// share this with company create and checks.. we are login with NAME
		    	SharingData.setVarchar4(userName);
		    	CouponClientFacade companyClient = null;

			    try {
			    	CompanyFacade co = new CompanyFacade();
					client = CouponSystem.getInstance().login(userName, password, ClientType.COMPANY);
					companyClient = client;
					compF = co.login(userName, password, ClientType.COMPANY);
				} catch (LoginException | DaoException | ConnectorException e) {
					System.out.println("\n" + e.getMessage());
					break;
				}
		
		    	boolean flag = false;
		    	
		    	if(client.equals(companyClient)) {
		    		flag = true;
		    		running = false;
		        	return flag;
		        	} // if
		        	else {
		        		return false;
		        	} // else
			} // else if - Company
			// Customer choice
			else if (choice == 3) {
				
				
				System.out.println("\n" + "Please type your Customer-ID and Password." + "\n");
		    	System.out.print("Type Your Customer Full Name: ");
		    	String userName = userInputString();
		    	System.out.print("Type Your Customer Password: ");
		    	String password = userInputString();

		    	CouponClientFacade customerClient = null;

		    	
		    	try {
					CustomerFacade cu = new CustomerFacade();
		    		client = CouponSystem.getInstance().login(userName, password, ClientType.CUSTOMER);
		    		customerClient = client;
		    		cusF = cu.login(userName, password, ClientType.CUSTOMER);
		    		
				} catch (LoginException | ConnectorException | DaoException e) {
					System.out.println("\n" + e.getMessage());
					break;
			    } // catch
		    	boolean flag = false;
		    	if(client.equals(customerClient)) {
		    		flag = true;
		    		running = false;
		        	return flag;
		        	} // if
		        	else {
		        		return false;
		        	} // else
			}// else if - Customer
			else {
				return false;
			} // else - NO ClientType.
			
		} // while - flag
		return false;
		
		} // login_T

 
		
	
	private static void loadDriver() {

		try {
			CouponSystem.getInstance();
		} catch (ConnectorException e) {
			System.out.println(e.getMessage());
		}
		
		
			try {
				if(DBconnector.getConnection().isClosed() == false) {
					System.out.println("----------- DRIVER LOADED -----------------" + "\n");
				}
			} catch (NullPointerException | SQLException e) {
				try {
					throw new ConnectorException("Error: Connection to the Data Base - FAILED");
				} catch (ConnectorException e1) {
					System.out.println(e1.getMessage());;
				}
			} // catch		
	} // loadDriver
	
	/*/***********************************************
	 ********      Section F: CompanyDB & DAO  ******
	 ************************************************
	 *
	 * This the section of all the company function and method.
	 * Here we create the connection between all the methods of CompanyDB and CompanyDAO 
	 * from the packages in the Coupon System Project.
	 *
	 */
 
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void addCompnay_T()  {
		
		while (true){
			
            System.out.print("NEW Company Name: ");
			String name = (userInputString());
			
            System.out.print("NEW Company Email: ");
            String email = (userInputString());
			
            System.out.print("NEW Company Password: ");
            String password = (userInputString());
			
			// check if the user put's somthing empty...

			Company company = null;
			try {
			company = new Company();
			company.setCompName(name);
			company.setEmail(email);
			company.setPassword(password);
//			CouponSystem.getInstance().getCompDao().createCompany(company);
			
				admF.createCompany(company);
			} catch (DaoException | FiledErrorException e) {
				
				System.out.println(e.getMessage());;
			}

			
			if(company.getId() != 0) {
				System.out.println(company.toString());
				System.out.println("------------ Company Added Successfully ----------" + "\n");
			}
			

			System.out.println("Whould you keep adding companies? Type '1' for YES or any other Number for NO.");
			short choice1 = userInputShort();
			
			if (choice1 == 1) {
				continue;
			} // if 
			else {
				printAdminFacadeMenu();
				break;
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
    			
    			System.out.print("Type Your Company ID: ");
    			SharingData.setLongNum1(userInputLong());
    			if (SharingData.getLongNum1() == 0) {
    				
    				printGoingBackToUsage();
    				//printAdminFacadeMenu();
    				break;
    			}
    			else {	
    			      try {
    			    	  company.setId(SharingData.getLongNum1());
    			    	  CouponSystem.getInstance().getCompDao().removeCompanyByID(company);
    			    	  
					  } catch (Exception e) {
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
//    		System.out.println("Please type your current Company ID and Password.");
//    		System.out.println("Note: only because it's beta version test - we asking for Company ID.");
    		//System.out.println("\n" + "Type The OLD Company Name:");
     		
    		System.out.print("Company ID: ");
            c.setId(userInputLong());
    	    		
 	    		Company company = new Company();
				try {
					admF = new AdminFacade();
				} catch (ConnectorException e1) {
					System.out.println(e1.getMessage());
				}
				try {
					company = admF.getCompany(c.getId());
					 printFoundInDB("Company"); 
	     	    		System.out.print("NEW Email: ");
	     	    		company.setEmail(userInputString());
	     	    		System.out.print("New Password: ");
	     	    		company.setPassword(userInputString());
	     	           
						admF.updateCompany(company);
				} catch (DaoException | FiledErrorException e) {
					
					System.out.println(e.getMessage());
//					break;
				}
     	     		if(company.getCompName() != null) {
     	     			System.out.println(company.toString());
     	     			System.out.println("------------ Company Updated Successfully ----------" + "\n");
     	     			printGoingBackToUsage();
     	     			printAdminFacadeMenu();
     	    		} // if - is updated
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
        	long id = userInputLong();
        	
        	if(id == 0) {
        		System.out.println("Typing 'Zero' is mean = quit..");
        		printGoingBackToUsage();
        		break;
        	} // if - it 0 the program will break from this function.
        	
        	
        	else {
        		try {
					Company c = admF.getCompany(id);
				
					System.out.println(c.toString());
					System.out.println("\n" + "------------ Company Function (getCompany) Was Run Successfully ----------" + "\n");

			    } catch (DaoException e) {
				
				System.out.println(e.getMessage());;
			} // else
					printAdminFacadeMenu();
					break;
				} // if - isExist
				
        	
    	} // while loop

    	} // getCompanyID_T - Function

    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    
    private static void getAllCompanies_T() {

    	while(true) {
    		try {	
    		System.out.println("Here is your Companeis List: " + "\n");
       			
    			
			System.out.println(admF.getAllCompanies().toString());
			System.out.println("\n" + "Results: [" + admF.getAllCompanies().size() + "] Companies");
			} catch (DaoException e) {
					
					System.out.println(e.getMessage());;
				}
        		printAdminFacadeMenu();

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
    		
    	
    		Customer c = null;
    		try {
    			c = new Customer();
        		c.setCustName(name);
        		c.setPassword(password);
				admF.createCustomer(c);
			} catch (DaoException | FiledErrorException e) {
				System.out.println(e.getMessage());
			}
    		
    		if(c.getId() > 0) {
    			System.out.println(c.toString());
    			System.out.println("------------ Customer Added Successfully ----------" + "\n");
        		//printAdminFacadeMenu();
    		}
    		else {
    			System.out.println("\n" + "****************************************************");
    			System.out.println("Error - No Changes Were Made :(");
    			System.out.println("\n" + "****************************************************");
    		}
    			
    		
    		
			System.out.println("Whould you keep adding Customers? Type '1' for YES or any other Number for NO.");
    		short choice1 = userInputShort();
    		
    		if (choice1 == 1) {
    			continue;
    		} // if 
    		else {
    			printAdminFacadeMenu();
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
    		
    			System.out.print("Type Your Customer ID: ");
    			SharingData.setLongNum1(userInputLong());
    			if (SharingData.getLongNum1() == 0) {
    				printGoingBackToUsage();
    				break;
    			}
//    			
//    			IsExistDB.idExistV2Customer(SharingData.getLongNum1());
//    			if(IsExistDB.getAnswer2() == false) {		
//         		printNoExistOrCurrect();
//         		break;
//    			}
    			else {	
    				Customer cus = new Customer();
    				cus.setId(SharingData.getLongNum1());
    			      try {
						admF.removeCustomer(cus);
						printCustomerRemoved();
					} catch (DaoException | FiledErrorException e) {
						System.out.println(e.getMessage());
					} // catch

    			} // else
    		
    		System.out.println("Whould you like to Removing more Customers? Type '1' for YES or any other Number for NO.");
    		short choice1 = userInputShort();
    		
    		if (choice1 == 1) {
    			continue;
    		} // if 
    		else {
    			break;
    		} // else
    		} // while loop
    		
    	} // removeCompany - plan
    
    /**
     * This Function is blong ONLY to AdminFacade Access.
     * @category AdminFacede Section
     * @author Raziel
     */
    private static void updateCustomer_T() {
    	
    	while(true) {
    		 
    		Customer c = new Customer();
    		
    		System.out.println("Update Customer:" + "\n");
    		System.out.println("Please type your current Customer ID.");
     		
    		System.out.print("Customer ID: ");
            c.setId(userInputLong());
//            System.out.print("Customer Name:");
//            c.setCustName(userInputString());
     
     		printFoundInDB("Customer"); 
            System.out.print("NEW Customer Name: ");
            String name = userInputString();
     	    
     	    System.out.print("NEW Password: ");
     	    String password = userInputString();
     	    		
     	    if(name.isEmpty() || password.isEmpty()) {
     	    	System.out.println("\n" + "Error - the fields are empty!");
    			printGoingBackToUsage();
    			printAdminFacadeMenu();
    			break;
     	    }

     	    		
     	    try {
     	    	c.setCustName(name);
          	    c.setPassword(password);
				admF.updateCustomer(c);
				System.out.println(c.toString());
	     	    System.out.println("------------ Customer Updated Successfully ----------" + "\n");
			} catch (DaoException | FiledErrorException e) {
				
				System.out.println(e.getMessage());;
			}
 
//     	    System.out.println("\n" + "****************************************************");
//     	    System.out.println("Error - No Changes Were Made :(");
//     	    System.out.println("****************************************************" + "\n");
     	    printGoingBackToUsage();
     	    printAdminFacadeMenu();

     		break;
    	} // while loop
    	
    } // updateCompany
    
    /**
     * This Function is blong ONLY to CompanyFacade Access.
     * @category CompanyFacade Section
     * @author Raziel
     */
    
    private static void getAllCouponOfCompany_T() {
    	
    	System.out.println("Here is your Company Coupons List: ");
    	
    	Collection<Coupon> coupons;
		try {

			coupons = compF.getAllCoupons();
			System.out.println(coupons.toString());
		} catch (DaoException e) {
			System.out.println(e.getMessage());;
		}
    	
    	
    }
    
    /** This Function is blong ONLY to CompanyFacade Access.
    * @category CompanyFacade Section
    * @author Raziel
    */
    private static void getAllCompanyCouponsByType_T() {
   	
   	System.out.println("Search By Category :)");
   	System.out.print("Please insert your CATEGORY: ");
   	
   	try {
   		Collection<Coupon> couponsByType = compF.getCouponsByType(userInputString());
	   	System.out.println(couponsByType.toString());
	} catch (DaoException | FiledErrorException e) {
		System.out.println(e.getMessage());
	}
   } // getAllCouponsByType_T
    
    private static void getAllCouponsOfCompanyByPrice_T() {
    	System.out.println("Here is your Company Coupons List: ");
    	
    	System.out.print("Type your Maximum Price: ");
    	double maxPrice = userInputDouble();

    	
    	try {
    		Collection<Coupon> coupons = compF.getCouponsOfCompanyByPrice(maxPrice);
			System.out.println(coupons.toString() + "\n");
			System.out.println("Results: [" + coupons.size() + "] Coupons");
		} catch (DaoException e) {
			System.out.println(e.getMessage());
		}
    	
    	
    	if(SharingData.getExceptionMessage() != null) {
        	System.out.println(SharingData.getExceptionMessage());
        	}
    }
    
    /*/***********************************************
	 ********   Section G: Customer Section   *******
	 ************************************************
	 *
	 * This the section of all the customer function and method.
	 * Here we create the connection between all the methods of CustomerDB and CustomerDAO 
	 * from the packages in the Coupon System Project.
	 *
	 */
    
    private static void getCustomerID_T() {
    	
    	while(true) {
        	System.out.print("Type The Customer ID:");
        	long id = userInputLong();
        	
        		try {
					Customer c = admF.getCustomer(id);
					System.out.println(c.toString());
	    			System.out.println("\n" + "------------ Customer Function (getID) Was Run Successfully ----------" + "\n");
				} catch (DaoException | FiledErrorException e) {
					System.out.println(e.getMessage());
				}
        		
        		// Print the Customer
        	
    	} // while loop

    	} // getCompanyID_T - Function
    private static void getAllCustomers_T() {

    	while(true) {
    		
    		
    		
    		try {
    			System.out.println("Here is your Customers List: " + "\n");
				System.out.println("\n" + admF.getAllCustomers());
			} catch (DaoException e) {
				System.out.println(e.getMessage());
			}
    		
    		printAdminFacadeMenu();
    		break;		
    	} // while loop
    	
    } // getAllCompanies_T - Function

    /**
     * This Function is blong ONLY to CustomerFacade Access.
     * @category CustomerFacade Section
     * @author Raziel
     */
    private static void purchaseCoupon_T() {
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

    	while(true) {

    	System.out.println("\n" + "Now you can purchase a coupon by is ID. " 
    			+ "\n" + "But if you don't know the ID or which coupon do your want, "
    			+ "please type '0' and go back to Customer Side Menu and check with Get's Options." + "\n");
//    	
//    		System.out.println("Before You will buy coupon, We have to be sure you're the customer.");
//    		System.out.print("Enter Your ID: ");
//    		long id = userInputLong();
//    		System.out.print("Enter Your Password: ");
//    		SharingData.setVarchar2(userInputString());
    		try {
    							
    	
    	System.out.print("Type the Coupon ID (Or '0' for exit): ");
    	long coupID = sc.nextLong();
    	if(coupID == 0) {
    		printGoingBackToUsage();
    		break;
    	}
		    	Coupon coupon = new Coupon();

		    	coupon.setId(coupID);
		    	coupon = cusF.purchaseCoupon(coupon);
		    	if(coupon.getTitle() != null) {
			    	System.out.println(coupon.toString());
		    	}
			} catch (DaoException | FiledErrorException e) {
				System.out.println(e.getMessage());
			}

	    	break;
    	} // while loop
    	
    } // purchaseCoupon_T

    /**


     * This Function is blong ONLY to CustomerFacade Access.
     * @category CustomerFacade Section
     * @author Raziel
     */
    private static void getAllPurchasedCoupon_T() {
    	
    	Collection<Coupon> c = null;
		try {
			c = cusF.getCoupons();
		} catch (DaoException e) {
			System.out.println(e.getMessage());
		}
    	if (c != null) {
    		System.out.println(c.toString());
    	}
//    	else {
//    		System.out.println("No coupons prchased");
//    	}
    }

    /**

    * This Function is blong ONLY to CustomerFacade Access.
     * @category CustomerFacade Section
     * @author Raziel
     */
    private static void getAllCustomerCouponsByType_T() {
    	
//    	CustomerFacade cusF = new CustomerFacade();
    	
    	System.out.println("Search By Category :)");
    	System.out.print("Please insert your CATEGORY: ");
    	String type = userInputString();
    	Collection<Coupon> couponsByType = null;
    	try {
    		couponsByType = cusF.getAllCouponsByType(type);
        	System.out.println(couponsByType.toString());
    	}
    	catch (IllegalArgumentException | NullPointerException | DaoException | FiledErrorException e) {
    		System.out.println(e.getMessage());;
    	}
    } // getAllCouponsByType_T
    
    /**
    * This Function is blong ONLY to CustomerFacade Access.
     * @category CustomerFacade Section
     * @author Raziel
     */
    @SuppressWarnings("resource")
	private static void getAllCouponsByPrice_T() {
    	System.out.println("Here is your Customer Coupons List: ");
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Type your Maximum Price: ");
    	double maxPrice = sc.nextDouble();
    	
//    	CustomerFacade cusF = new CustomerFacade();
    	
    	Collection<Coupon> coupons = new HashSet<>();
		try {
			coupons = cusF.getAllCouponsByPrice(maxPrice);
		} catch (DaoException e) {
			
			System.out.println(e.getMessage());
			}
    	System.out.println(coupons.toString());
    	
    } // getAllCouponsByType_T

    /*/***********************************************
	 ********   Section H: Coupon Section   *******
	 ************************************************
	 *
	 * This the section of all the coupon function and method.
	 * Here we create the connection between all the methods of CouponDB and CouponDAO 
	 * from the packages in the Coupon System Project.
	 */
   
    private static void addCoupon_T() {
    	
    	Coupon coup = new Coupon();
	
		try { 
			
		while (true){
		System.out.print("NEW Title: ");
        String title = userInputString();
        coup.setTitle(title);
    	
    	System.out.print("NEW Coupon StartDate.. ");
		LocalDate startDate = null;
		System.out.print("Day: ");
		int startDay = userInputInt();
		System.out.print("Month: ");
		int startMonth = userInputInt();
		System.out.print("Year: ");
		int startYear = userInputInt();
		
		if(startDay == 0 || startMonth == 0 || startYear == 0) {
			throw new FiledErrorException("Error: Invaild Values!");
		}
		else {
			startDate = LocalDate.of(startYear, startMonth, startDay);
			coup.setStartDate(startDate);
		}
		
		System.out.print("NEW Coupon EndDate.. ");
		LocalDate endDate = null;
		System.out.print("Day: ");
		int endDay = userInputInt();
		System.out.print("Month: ");
		int endMonth = userInputInt();
		System.out.print("Year: ");
		int endYear = userInputInt();
		if(endDay == 0 || endDay == 0 || endYear == 0) {
			throw new FiledErrorException("Error: Invaild Values!");
		}
		else {
			endDate = LocalDate.of(endYear, endMonth, endDay);
	    	coup.setEndDate(endDate);
		}

		System.out.print("NEW Amount: ");
        int amount = userInputInt();
		coup.setAmount(amount);
        System.out.print("In Category: ");
        String category = userInputString();
        coup.setCategory(category);
        
        System.out.print("NEW Massage: ");
        String message = userInputString();
        coup.setMessage(message);
        System.out.print("NEW Price: ");
		Scanner sc = new Scanner(System.in);
        double price = sc.nextDouble();
        coup.setPrice(price);
        System.out.print("NEW Imag link: ");
        String image = userInputString();
		coup.setImage(image);
		
//        String ownerName = SharingData.getVarchar4();
		Company company = new Company();

		// putting all the variables
//        try {
//			AdminFacade admF = new AdminFacade();
//			System.out.println(ownerName);
//			company = admF.getCompany(ownerName);

			compF.addCoupon(coup);
			System.out.println(coup.toString());
			System.out.println("------------ Coupon Added Successfully ----------" + "\n");

				
		System.out.println("Whould you keep adding Coupons? Type '1' for YES or any other Number for NO.");
		short choice1 = userInputShort();
		
		if (choice1 == 1) {
			printCompanyFacadeMenu();
			continue;
		} // if 
		else {
			break;
		}
	
	} // while loop
		} catch (DaoException | FiledErrorException | InputMismatchException e) {
			System.out.println(e.getMessage());
		}
	
} // addCompnay - Function
    
    private static void removeCoupon_T() {
    	
    	while(true) { 
    		
    		
    		System.out.print("Type Your Coupon ID: ");
    		SharingData.setLongNum1(userInputLong());
    		if (SharingData.getLongNum1() == 0) {
    			
    			printGoingBackToUsage();
    			break;
    		}
    		try {
    			Coupon c = new Coupon();
				c.setId(SharingData.getLongNum1());
				if(c.getTitle() != null) {
					System.out.println(c.toString());
				}
				
				
					compF.removeCoupon(c);
					printCouponRemoved();
				} catch (DaoException | FiledErrorException e) {
					System.out.println(e.getMessage());
				}

    	} // while loop
    	
    } // removeCoupon
    
    private static void updateCoupon_T() {
    	
    	while(true) {
    	
		System.out.print("Coupon ID: ");
        SharingData.setIdsShare(userInputLong());
    	
        	// Object Coupon
        	Coupon coup;
        	
        	// DBDAO Coupon
        	try {
        	long id = SharingData.getIdsShare();
        	
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
            
            System.out.print("NEW Message: ");
            String message = userInputString();
            
            System.out.print("NEW Price: ");
			Scanner sc = new Scanner(System.in);
            double price = sc.nextDouble();
                        
			// putting all the variables 

			
				coup = new Coupon();
				coup.setId(id);
				coup.setEndDate(endDate);
				coup.setAmount(amount);
				coup.setMessage(message);
				coup.setPrice(price);
				
				compF.updateCoupon(coup);
			} catch (DaoException | DateTimeException | FiledErrorException e) {
				System.out.println(e.getMessage());;
			}			
			break;
        
    	} // while loop
    } // updateCoupon_T
    
    private static void getCoupon_T() {
    	
    	while(true) {
        	System.out.print("Type The Coupon ID:");
        	SharingData.setIdsShare(userInputLong());
//        	System.out.print("Type Your Company ID: ");
//        	SharingData.setLongNum1(userInputLong());
//        	
        		Coupon coupon = new Coupon();
        		try {
            		 long id = SharingData.getIdsShare();
//        			Company company = new Company();
//        			company.setId(SharingData.getLongNum1());
        			coupon = compF.getCoupon(id);
	        		
        			//check isEmpty for priniting only..
//        			if(!coupon.getTitle().isEmpty()) {
        				System.out.println(coupon.toString());
    		    		System.out.println("\n" + "------------ Coupon Function (getID) Was Run Successfully ----------" + "\n");
//        			}

				} catch (DaoException | FiledErrorException e) {
					System.out.println(e.getMessage());
				}
        		break;        	
    	} // while loop

    	} // getCouponID_T - Function
    
    /*/***********************************************
	 ********   Section I: Facade Test Section   *******
	 ************************************************
	 *
	 * This the section of all the Facades functions and methods.
	 * Here you can test it by the 3 UserType below (in the 'prinFacadeMenu()').
	 *
	 */

    private static void AdminFacade_T() {
    	
    	// Note: AdminFacade Usage Print is in the login Option.
    	boolean existOrNot = login_T();
 
    	if(existOrNot != true) {
    		printNoExistOrCurrect();
    		printGoingBackToUsage();
    	}
    	else {
    		printAdminFacadeMenu();
    	    boolean on = true;
    	    while(on == true) {   	
    		short userChoiceOfSideWork = userInputFadacesShort();
    		
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
    			updateCustomer_T();
    			break;
    		}
    		case 9: {
    			getCustomerID_T();
    		}
    		case 10: {
    			getAllCustomers_T();
    			break;
    		}
    		case 11: { // Remove Coupon
    			System.out.println("Remove specific Coupon.." + "\n");
    			System.out.print("Enter Coupon ID: ");
    			long id = userInputLong();
    			
    			try {
    			Coupon coupon = new Coupon();
    			
					coupon.setId(id);
	    			admF.removeCoupon(coupon);
	    			System.out.println();

				} catch (FiledErrorException | DaoException e) {
					System.out.println(e.getMessage());;
				}
    			break;
    		}
    		case 12: { // Get Coupons By Category
    			try {
    				Collection<Coupon> coupons = new HashSet<>();
    				System.out.print("Type your Category: ");
    				String category = userInputString();
    				coupons = admF.getCouponByType(category);
    				
					System.out.println(coupons.toString() + "\n");
					System.out.println("Results: [" + coupons.size() + "] Coupons");
				} catch (DaoException | IllegalArgumentException | FiledErrorException e) {
					System.out.println(e.getMessage());;
				}
    			printAdminFacadeMenu();
    			break;
    		} // case 12
    		case 13: { // Get All coupon Up to price
    			try {
    				System.out.print("Type Your Maximum Price: ");
    				double price = userInputDouble();
    				System.out.println("Max Price: [" + price + "]");
    				System.out.println("List OF all Coupons By Maximum Price: ");
    				Collection<Coupon> coupons = new HashSet<>();
    				coupons = admF.getCouponByPrice(price);
    				
					System.out.println(coupons.toString() + "\n");
					System.out.println("Results: [" + coupons.size() + "] Coupons");

				} catch (DaoException | IllegalArgumentException e) {
					System.out.println(e.getMessage());;
				}
    			printAdminFacadeMenu();
    			break;
    		}
    		case 14: { // Get All Coupons
    			try {
    				System.out.println("List OF all Coupons: ");
    				Collection<Coupon> coupons = new HashSet<>();
    				coupons = admF.getAllCoupons();
    				
					System.out.println(coupons.toString() + "\n");
					System.out.println("Results: [" + coupons.size() + "] Coupons");

				} catch (DaoException | IllegalArgumentException e) {
					System.out.println(e.getMessage());;
				}
    			printAdminFacadeMenu();
    			break;
    	
    		} // case 14
    		case 0: {
    			on = false;
    			printAdminFacadeMenu();
    			break;
    		}
    		} // Switch
        	} // while loop
    	} // else
    } // AdminFacade_T
    
    private static void CompanyFacade_T() {
    	
        boolean existOrNot = login_T();
    	
    	if(existOrNot != true) {
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
    		    updateCoupon_T();
    			break;
    		}
    		case 4: {
    			try {
    				System.out.println(compF.viewCompay());
    				
				} catch (DaoException e) {
					System.out.println(e.getMessage());;
				}
    			break;
    		}
    		case 5: {
    			getCoupon_T();
    			break;
    		}
    		case 6: {
    			getAllCouponOfCompany_T();
    			break;
    		}
    		case 7: {
    			getAllCompanyCouponsByType_T();
    			break;
    		}
    		case 8: {
    			getAllCouponsOfCompanyByPrice_T();
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
    
    private static void CustomerFacade_T(){
    	
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
     			getAllPurchasedCoupon_T();
     			break;
     		}
     		case 3: {
     			getAllCustomerCouponsByType_T();
     			break;
     		}
     		case 4: {
     			getAllCouponsByPrice_T();
     			break;
     		}
     		case 5: {
     			System.out.println("Your Customer Details: ");
     			try {
					System.out.print(cusF.getCustomer());
				} catch (DaoException e) {
					System.out.println(e.getMessage());
				}
     			break;
     		}
     		case 6: {
     			
     			System.out.println("Here is the FULL Details: ");
     			try {
					System.out.println(cusF.getCustomerAndCoupons());;
				} catch (DaoException e) {
					System.out.println(e.getMessage());
				} // catch
     			break;
     		} // case 6
     		case 0: {
     			System.out.println("Thank You! Bye Bye :) ");
     			on = false;
     			break;
     		}
     		} // Switch
         	} // while loop
     	} // else
     
    	
    } // CustomerFacade_T
    
    private static void Facade_T() {
    	    	
    	// checks if we have some error
    	
    	
    	try {
    		/* Loading Bar - just nice looking.
    		 * but the real one should be conditional on the DailyTask. 
    		 */
    		for (long i = 0; i < 50; i++) {
                System.out.print("=");

                if (i % 20 == 100) {
                    System.out.print("\r                   \r");
                }
                System.out.flush();
        		Thread.sleep(100);

            }
			System.out.println("\n" + "[Expired Coupos Deleted!]" + "\n");


    		// This letting the Daily Task 5 seconds untill we will done.
    		System.out.println();


		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} // catch
    	System.out.println("*************************");
    	System.out.println("[System Loaded]");
    	System.out.println("*************************");

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
		case 0: {
			System.out.println("Thank You! Bye Bye :) ");
			on = false;
			break;
		}
		
		} // Switch
    	} // while loop

    	
    }


} // Class	