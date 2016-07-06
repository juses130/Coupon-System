package com.facade;

import com.dbdao.CustomerDBDAO;

public class CustomerFacade {

	public CustomerFacade(){}
	
     public boolean login(String compName, String password) {
		
    	CustomerDBDAO cus = new CustomerDBDAO();
		boolean exsistOrNot = cus.login(compName, password);
		
		if(exsistOrNot != true) {
			return false;
		}
		else {
			return true;
		} // else
	} // login - function
	
}
