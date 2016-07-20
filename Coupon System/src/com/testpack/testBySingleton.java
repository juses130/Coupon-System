package com.testpack;

import com.added.functions.DBconnectorV3;
import com.exeptionerrors.ConnectorExeption;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.AdminFacade;

public class testBySingleton { 
	public static void main(String[] args) {
		
		try {
//			CouponSystem.getInstance();
			DBconnectorV3.startPool();
		} catch (ConnectorExeption e) {
			System.out.println(e.getMessage());
		}
		
		try {
//			Company company = new Company();
//			Customer customer = new Customer(0, null, null);
			AdminFacade admF = new AdminFacade();	
//			company.setCompName("unity");
//			System.out.println(company.getId());
//			try {
//				Coupon c = new Coupon();
//				c.setTitle("TRAVEL TIME");
//				c.setStartDate(LocalDate.of(2016, 7, 20));
//				c.setEndDate(LocalDate.of(2016, 8, 20));
//				c.setType(CouponType.CAMPING);
//				c.setMessage("messgae!");
//				c.setPrice(58);
//				c.setImage("no imag");
//				c.setAmount(12);
//				admF.getCompany("mahmus");
				
				
//				TimeUnit.SECONDS.sleep(2);
//				System.out.println("[Expired Coupos Deleted!]");
//			} catch (InterruptedException e) {
//				System.out.println(e.getMessage());
//			} // catch
	    	System.out.println("*************************");
	    	System.out.println("[System Loaded]");
	    	System.out.println("*************************");
//			System.out.println(LocalDate.now());
//				admF.createCompany(company);
				System.out.println(admF.getCustomer(12));
//				System.out.println(admF.getCompany(company.getCompName()));
			} catch (ConnectorExeption | DaoExeption | FiledErrorException e) {
				System.out.println(e.getMessage());;
			}
		
		// When we finished - close the connection + Daily Task Thread.
//				try {
//					CouponSystem.getInstance().stop();
//				} catch (ConnectorExeption | SQLException e) {	
//					System.out.println(e.getMessage());
//				}
	}

}
