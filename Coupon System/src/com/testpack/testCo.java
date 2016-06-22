package com.testpack;

import java.sql.SQLException;

import com.added.functions.*;
import com.javabeans.*;
import com.mysql.fabric.xmlrpc.base.Data;
import com.testpack.testDBDAO;

public class testCo {

	//TODO: write here a nice description..
	
	/**
	 * @author Raziel
	 * This test class is only for training and checking the added Class/Methods: DataRowGetter
	 * ONLY FOR THE DEVELOPERS.
	 * @throws SQLException 
	 * 
	 */
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		Company c = new Company();
		c.setCompName("asus");
		c.setEmail("aceasfafgafr@gmail.com");
		c.setPassword("1234");
		c.setId(456634);
		
		DataRowGetter.getRow(c);
		if(DataRowGetter.isVerificationExistInDataB() == true) {
			System.out.println(c.toString() + "\n");
		}
		else {
			testDBDAO.printNoExistOrCurrect();
			System.out.println("Pay Attention, Type ONLY ACCORDING to the instructions Below: "
					+ "\n" + "1. UserName, Password." 
					+ "\n" + "2. UserName, Password, Email. "
					+ "\n" + "3. UserName, Password, ID. " 
					+ "\n" + "4. UserName, Password, ID, Email. ");
			
			System.out.println("You can be Wrong about the ID or Email.. the program will fixed it for you.");
			System.out.println("But you CAN'T be WRONG about the UserName and Password." + "\n");
		
		}
		
		//IsExistDB.idPassExistV2(c.getId(), c.getPassword());
		System.out.println("Number: " + DataRowGetter.getCountANDcalculate());
		System.out.println("IsExist: " + IsExistDB.getAnswer());
		System.out.println("DataRowGetter: " + DataRowGetter.isVerificationExistInDataB());
		System.out.println("Verify Nubmer: " + DataRowGetter.getVerifyByNumber());
		
//		System.out.println("Good morning :)");
//		
//		Collection<Company> companies = new HashSet<>();
//		
//		Company a = new Company("Asus", "1234", "Asus@gmail.com");
//		Company b = new Company("Philips", "1234", "gmail@gmail.com");
//		Company c = new Company("Playstation", "1234", "play@gmail.com");
//		Company d = new Company("Mizrahi", "1234", "Mizrahi@gmail.com");
//		Company e = new Company("Nike", "1234", "n@mail.com");
//
//		companies.add(a);
//		companies.add(b);
//		companies.add(c);
//		companies.add(d);
//		companies.add(e);
//		companies.size();
//		
//		System.out.println(companies.toString() + "\n" + "How Many Companies: " + companies.size());

	}

}
