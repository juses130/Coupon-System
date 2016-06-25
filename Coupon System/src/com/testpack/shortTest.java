package com.testpack;
import java.sql.SQLException;
import java.time.LocalDate;

import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.javabeans.Company;
import com.javabeans.Coupon;
import com.javabeans.CouponType;

/**
 * This is a Test Class.
 * It's more pratical class. Is short, and check the Objects and the function in the fast way.
 * The other Test is just a beta version of test with scanners and User experience.
 * 
 * I moved to work with the short Test because I don't think I will have enough time for Scanners and Visual in
 * this point. we just need the pratical thing. I WILL use the visual test and terminal or 
 * maybe only the structure of it. 
 * 
 * @author Raziel
 *
 */

public class shortTest {
	
	public static void main(String[] args) throws SQLException {

		CouponDBDAO dbcoup = new CouponDBDAO();
		CompanyDBDAO dbcomp = new CompanyDBDAO();
		
		Coupon c1 = new Coupon();
		Coupon c2;
		Coupon c3;
		Coupon c4;
		
		Company co = new Company();
		
		// coupon add.
//		c1 = new Coupon("Villa",LocalDate.now(),LocalDate.of(2018, 7, 29), 10, CouponType.TRAVEL, "Hotel Coupon", 150.90, "/Users/Kill130/Google Drive/m.jpg");
//		dbcoup.createCoupon(c1);
//		System.out.println(c1.toString());
		
		//Coupon remove
//		c1.setId(3);
//		dbcoup.removeCoupon(c1);
//		System.out.println(c1.toString());
		
//		co.setId(23);
//		dbcomp.removeCompany(co);
//		System.out.println(co.toString());
		
		
//		Coupon c5 = dbcoup.getCoupon(1);
//		//dbcoup.getCoupon(1);
//		System.out.println(c5);
		
		dbcoup.getAllCoupon();
		
	}

}


