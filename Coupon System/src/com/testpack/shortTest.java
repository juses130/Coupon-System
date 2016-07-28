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

import com.added.functions.DBconnectorV3;
import com.added.functions.SharingData;
import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.dbdao.CustomerDBDAO;
import com.exeptionerrors.ConnectorExeption;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.AdminFacade;
import com.facade.CompanyFacade;
import com.facade.CustomerFacade;
import com.javabeans.Company;
import com.javabeans.Coupon;
import com.javabeans.CouponType;
import com.javabeans.Customer;
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
		
		CompanyFacade comF = null;
		try {
			comF = new CompanyFacade();
		} catch (ConnectorExeption e2) {
			System.out.println(e2.getMessage());
		}
		
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
//			coup.stop();
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
//		CustomerDBDAO customerDBDAO = null;
//		try {
//			AdminFacade adminFacade = new AdminFacade();
//			customerDBDAO = new CustomerDBDAO();
//			Customer c = new Customer();
//			CouponSystem.getInstance();
//		} catch (ConnectorExeption e1) {
//			
//			System.out.println(e1.getMessage());;
//		}
		
		
		
//		coup.setCustName("bizu");
//		coup.setPassword("1234");
//		
//		try {
//			System.out.println(customerDBDAO.getCustomer("bizu"));
//			adminFacade.createCustomer(c);
//			CustomerDBDAO customerDBDAO = new CustomerDBDAO();
//			System.out.println(customerDBDAO.getCustomer(coup.getCustName()).toString());;
//		} catch (DaoExeption | FiledErrorException e) {
			
//			System.out.println(e.getMessage());;
//			e.printStackTrace();
//		}
//		System.out.println(coup.toString());
		try {
			addCouponsByCompany(8);
//			Coupon coupon = new Coupon();
//			Company company = new Company();
//			coupon.setTitle("ComputerA0");
//			company.setId(4);
//			coupon = comF.getCoupon(coupon, company);
//			System.out.println(coupon.toString());

				CouponSystem.getInstance().stop();
			} catch (ConnectorExeption | SQLException | DaoExeption e) {

				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	
		
	} // main
	
	public static void addCouponsByCompany(long id) throws DaoExeption {
		CompanyFacade comF;
		CustomerFacade custF;
		
		try {
			comF = new CompanyFacade();
			custF = new CustomerFacade();
			
			for(int i = 1; i < 100; i++) {
				Coupon coup = new Coupon();
				Company comp = new Company();
//				Customer customer = new Customer();
				
//				comp.setCompName("acer");
				comp.setId(id);
				
				coup.setAmount(i);
				coup.setMessage("check message");
				coup.setTitle("iphone" + i );
				coup.setPrice(i + 50);
				coup.setStartDate(LocalDate.of(2018, 1, 1));
				coup.setType(CouponType.valueOf("ELECTRONICS"));
				coup.setEndDate(LocalDate.of(2020, 1, 1));
				coup.setImage("no image");
				coup.setOwnerID(id);

//				coup.setOwnerID(comF.viewCompay(8).getId());
				
				comF.addCoupon(coup, comp);
//				custF.purchaseCoupon(coup);
				System.out.println(coup.toString());
			} // for
		} catch (ConnectorExeption | FiledErrorException e) {
			
			System.out.println(e.getMessage());;
		}

		
	}
	
	public static void addCouponsByCustomer(long id) throws DaoExeption {
		CustomerFacade custF;
		Customer cust = new Customer();
		Coupon coup = new Coupon();
		Company comp = new Company();
		
		try {
			custF = new CustomerFacade();
			
			for(int i = 0; i < 50; i++) {
				
				
				coup.setId(10);
				custF.purchaseCoupon(coup, cust);
//				custF.purchaseCoupon(coup);
				System.out.println(coup.toString());
			} // for
		} catch (ConnectorExeption | FiledErrorException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	public static void deleteCheck() {
		ResultSet rs1 = null;
		Set<Long> ids = new HashSet<>();
				
		try {
		
		String sqlSelectByEndDate = "SELECT * FROM coupon WHERE End_Date < CURDATE()";
		PreparedStatement prep1 = DBconnectorV3.getConnection().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		rs1 = prep1.executeQuery(sqlSelectByEndDate);
		
		while(rs1.next()) { 
			ids.add(rs1.getLong(1));
			rs1.first();
			rs1.deleteRow();
		}
		prep1.close();
		rs1.close();
		
		String sqlDeleteByID1 = "DELETE FROM company_coupon WHERE coup_id=?";
		PreparedStatement prep2 = DBconnectorV3.getConnection().prepareStatement(sqlDeleteByID1);
		
		for(Long i : ids) {
			prep2.setLong(1 ,i.longValue());
			prep2.executeUpdate();
		}
		prep2.close();
		
		String sqlDeleteByID2 = "DELETE FROM customer_coupon WHERE coup_id=?";
		PreparedStatement prep3 = DBconnectorV3.getConnection().prepareStatement(sqlDeleteByID2);
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


