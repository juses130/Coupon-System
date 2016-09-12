package com.testpack;

import java.time.LocalDate;
import com.beans.Company;
import com.beans.Coupon;
import com.beans.CouponType;
import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.exceptionerrors.ConnectorException;
import com.exceptionerrors.DaoException;
import com.exceptionerrors.FiledErrorException;
import com.exceptionerrors.LoginException;
import com.facade.AdminFacade;
import com.facade.ClientType;

public class beforeTest {

	public static void main(String[] args) throws FiledErrorException, ConnectorException, DaoException, LoginException {
		
		AdminFacade admf = new AdminFacade();
		CompanyDBDAO cdb = new CompanyDBDAO();
		CouponDBDAO coupdb = new CouponDBDAO();
		Company company = new Company();
		company.setId(35);

		admf.login("admin", "1234", ClientType.ADMIN);
		int amount = 100;
		for(int x = 0; x < amount; x++) {
//			Company company = new Company("test" + x, "1234" , "test@" + x);
			Coupon coupon = new Coupon(0, "testCoupon" + x ,LocalDate.of(2016, 10, 20), LocalDate.of(2019, 8, 20), 5 + x, CouponType.MUSIC.toString(), "No messege", 20 +x, "no image", 3);
			coupdb.createCoupon(coupon, company);
		}
		
		
		// Test if arrays can store null
//		Collection<Company> list = new HashSet<>();
//		Company c1 = new Company(201, "TEST1", "1234", "null@hghbgfb");
//		Company c2 = new Company(202, "TEST2", "1234", "asfafdsf@");
//		
//		list.add(c1);
//		list.add(c2);
//		
//		System.out.println(list.toString());
		
//		System.out.println(DatabaseName.getDBname());
//		try {
//			
//			AdminFacade admf = new AdminFacade();
//			admf = admf.login("admin", "1234", ClientType.ADMIN);
//			Company company = new Company(29,"dubai", "1234", "dabush@gmail.com");
//			admf.createCompany(company);

			
//			company.setId(60);
//			System.out.println(company.getCompName());
//			admf.updateCompany(company);
//			
//			System.out.println("Your Input: " + admf.getCompany(company.getId()));
//			System.out.println(admf.getCompany(company.getPassword()));
//		} catch (ConnectorException | DaoException | FiledErrorException | LoginException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}


	}

}
