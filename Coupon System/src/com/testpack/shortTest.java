package com.testpack;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.added.functions.DBconnector;
import com.added.functions.DBconnectorV2;
import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.dbdao.CustomerDBDAO;
import com.exeptionerrors.DaoExeption;
import com.facade.AdminFacade;
import com.facade.CompanyFacade;
import com.javabeans.Company;
import com.javabeans.Coupon;
import com.javabeans.CouponType;
import com.javabeans.Customer;
import com.sun.org.apache.bcel.internal.generic.DADD;
import com.task.and.singleton.CouponSystem;
import com.task.and.singleton.DailyCouponExpirationTask;

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
	
	public static void main(String[] args) {

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
		
		CouponType type = null;
//		
//		DBconnector.getCon();
//		try {
//			
//			
//			String sql = "insert into coupon (coup_id, title, Category) " + "values ('7', 'Hilton', %s)".format(type.name());
//			PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sql);
//			prep.executeUpdate();
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
//		Coupon coup = dbcoup.getCoupon(3);
//		System.out.println(coup.toString());
//		System.out.println(dbcoup.getAllCoupon().toString());
		
		
		
		
//		System.out.println(dbcoup.getCouponByType(CouponType.ELECTRONICS).toString());
	
//		CompanyDBDAO db = new CompanyDBDAO();
//		System.out.println(db.getCoupons(1));
//		
//		System.out.println("Test getByPriceV2");
//		CouponDBDAO coupDB = new CouponDBDAO();
		//coupDB.getCouponByPriceV3("owner_id", 3, 1, 70);
	
		//System.out.println(coupDB.getCouponByPriceV2("owner_id", 3, 1, 1000));
//
//		try {
//			CouponSystem c = CouponSystem.getInstance();
//			TimeUnit.SECONDS.sleep(6);
//			c.stop();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		CouponSystem.getInstance();
		
		AdminFacade adminFacade = new AdminFacade();
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer c = new Customer();
		
//		c.setCustName("bizu");
//		c.setPassword("1234");
		
		try {
			System.out.println(customerDBDAO.getCustomer("bizu"));
//			adminFacade.createCustomer(c);
//			CustomerDBDAO customerDBDAO = new CustomerDBDAO();
//			System.out.println(customerDBDAO.getCustomer(c.getCustName()).toString());;
		} catch (DaoExeption e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());;
//			e.printStackTrace();
		}
//		System.out.println(c.toString());
//		addCouponsFor(7);

		CouponSystem.getInstance().stop();
		
	} // main
	
	public static void addCouponsFor(long id) throws DaoExeption {
		CompanyFacade comF = new CompanyFacade();

		for(int i = 0; i < 100; i++) {
			Coupon c = new Coupon();
			c.setAmount(i);
			c.setMessage("check message");
			c.setTitle("Check Title" + i);
			c.setPrice(i + 50);
			c.setStartDate(LocalDate.of(2010, 1, 1));
			c.setType(CouponType.valueOf("TRAVEL"));
			c.setEndDate(LocalDate.of(2016, 1, 1));
			c.setOwnerID(id);
			comF.createCoupon(c);
			
			System.out.println(c.toString());
		} // for
	}
	
	public static void deleteCheck() {
		ResultSet rs1 = null;
		Set<Long> ids = new HashSet<>();
				
		try {
		
		String sqlSelectByEndDate = "SELECT * FROM coupon WHERE End_Date < CURDATE()";
		PreparedStatement prep1 = DBconnectorV2.getConnection().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		rs1 = prep1.executeQuery(sqlSelectByEndDate);
		
		while(rs1.next()) { 
			ids.add(rs1.getLong(1));
			rs1.first();
			rs1.deleteRow();
		}
		prep1.close();
		rs1.close();
		
		String sqlDeleteByID1 = "DELETE FROM company_coupon WHERE coup_id=?";
		PreparedStatement prep2 = DBconnectorV2.getConnection().prepareStatement(sqlDeleteByID1);
		
		for(Long i : ids) {
			prep2.setLong(1 ,i.longValue());
			prep2.executeUpdate();
		}
		prep2.close();
		
		String sqlDeleteByID2 = "DELETE FROM customer_coupon WHERE coup_id=?";
		PreparedStatement prep3 = DBconnectorV2.getConnection().prepareStatement(sqlDeleteByID2);
		for(Long i : ids) {
			prep3.setLong(1, i.longValue());
			prep3.executeUpdate();
		}
		prep3.close();
		
		System.out.println(ids.toString());
		
		} //try
		catch (SQLException e){
			SharingData.setExeptionMessage(e.getMessage());
		}
	}

}


