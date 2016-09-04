package com.testpack;

import com.exceptionerrors.ConnectorException;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.exceptionerrors.LoginException;
import com.facade.AdminFacade;
import com.facade.ClientType;
import com.javabeans.Company;

public class beforeTest {

	public static void main(String[] args) {
		
		
		try {
			
			AdminFacade admf = new AdminFacade();
			admf = admf.login("admin", "1234", ClientType.ADMIN);
			Company company = new Company(29,"dubai", "1234", "dabush@gmail.com");
//			admf.createCompany(company);

			
//			company.setId(60);
//			System.out.println(company.getCompName());
			admf.updateCompany(company);
//			
//			System.out.println("Your Input: " + admf.getCompany(company.getId()));
//			System.out.println(admf.getCompany(company.getPassword()));
		} catch (ConnectorException | DaoException | FiledErrorException | LoginException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


	}

}
