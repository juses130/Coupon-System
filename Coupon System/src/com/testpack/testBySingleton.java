package com.testpack;

import java.time.LocalDate;

import com.added.functions.DBconnectorV3;
import com.exeptionerrors.ConnectorExeption;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.AdminFacade;
import com.facade.CompanyFacade;
import com.facade.CustomerFacade;
import com.javabeans.Company;
import com.javabeans.Coupon;
import com.javabeans.CouponType;

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
			CompanyFacade compF = new CompanyFacade();
			CustomerFacade custF = new CustomerFacade();
					
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
			
			Company company = compF.viewCompay(3500);
//			Coupon coupon = new Coupon("XML", LocalDate.of(2016, 7, 21), LocalDate.of(2018, 1, 1), 50, CouponType.CAMPING, "no", 250, "no", company.getId());
			System.out.println(company.toString());
//			System.out.println(compF.getCoupon(coupon));

//			compF.createCoupon(company, coupon);
				
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
//				System.out.println(company.toString());
//				System.out.println(compF.getCoupon(coupon));
//				System.out.println(admF.getCompany(company.getCompName()));
			} catch (ConnectorExeption | DaoExeption e) {
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
