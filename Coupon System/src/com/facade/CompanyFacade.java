package com.facade;

import com.dbdao.CompanyDBDAO;

public class CompanyFacade {

	public CompanyFacade() {}
	
	public boolean login(String compName, String password) {
		
		CompanyDBDAO db = new CompanyDBDAO();
		boolean exsistOrNot = db.login(compName, password);
		
		if(exsistOrNot != true) {
			return false;
		}
		else {
			return true;
		} // else
	} // login - function
	
}
