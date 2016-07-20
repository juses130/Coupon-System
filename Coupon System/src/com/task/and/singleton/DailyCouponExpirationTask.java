package com.task.and.singleton;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.added.functions.DBconnectorV2;
import com.added.functions.DBconnectorV3;
import com.added.functions.SharingData;
import com.dao.interfaces.*;
import com.dbdao.CouponDBDAO;
import com.exeptionerrors.ConnectorExeption;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.ClientType;
import com.facade.CompanyFacade;
import com.facade.CustomerFacade;
import com.javabeans.*;

/**
 * This is Version 2 of this Class.
 * 
 * @author Raziel
 *
 */

public class DailyCouponExpirationTask implements Runnable {

	// attributes
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO coupDao = null;
	private boolean running = true;
	
	// constructor
	public DailyCouponExpirationTask(CompanyDAO compDao, CustomerDAO custDao, CouponDAO couponDao) {
		this.compDao = compDao;
		this.custDao = custDao;
		this.coupDao = couponDao;
	}

	@Override
	public void run() {
		Set<Coupon> coupons = new HashSet<>();
		ResultSet rs = null;
		
		try {
			/* I think it's more good when the task will delete expired coupons BY THE SQL-COMMEND..
			 * Because when it will be in Internet Platforme and connected to Server.. it will use
			 * the time of the server / sql Database and the user cannot manipulated that.
			 */
		String sqlSelectByEndDate = "SELECT * FROM coupon WHERE End_Date < CURDATE()";
		PreparedStatement prep = null;
		prep = DBconnectorV3.getConnection().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		rs = prep.executeQuery(sqlSelectByEndDate);
		
		while(rs.next()) { 
			
			String title, message, image;
			Date stDate, enDate ;	
			int amount;
			CouponType type = null;
			double price;
			long id;
			
			title = rs.getString("Title");
			stDate = rs.getDate("start_date");
			enDate = rs.getDate("end_date");
			amount = rs.getInt("amount");
			type = CouponType.valueOf(rs.getString("Category").toUpperCase());
			message = rs.getString("Message");
			price = rs.getDouble("Price");
			image = rs.getString("image");
			id = rs.getLong("coup_id");

			Coupon coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, type,  message, price, image);
			coupons.add(coupon);
		} // while
	
		
		Company company = new Company();
		company.setCoupons(coupons);
					while (running) {
						System.out.println("\n" + "Wait! - " +"[Deleting Expired Coupons]");
							for (Coupon coupon : company.getCoupons()) {
								if (coupon.getEndDate().isBefore(LocalDate.now())) {
									deleteCoupon(coupon);
								} // if
						} // for
							break;
					} // while
					TimeUnit.HOURS.sleep(24);
					} // try 
					catch (InterruptedException | DaoExeption | SQLException | FiledErrorException e) {
						System.out.println(e.getMessage());
						Thread.currentThread().interrupt();
						}
				}
	private void deleteCoupon(Coupon coupon) throws DaoExeption {
		// Remove coupon from company  customer_coupon  company_coupon
		CompanyFacade compF = null;
		try {
			compF = new CompanyFacade();
			compF.removeCoupon(coupon);
		} catch (DaoExeption | ConnectorExeption e) {
			throw new DaoExeption("Error: Deleting Expired Coupon - FAILD" + "Coupon: "  + "\n"+ coupon.toString());
		} // catch

	}

	public void stop() {
		running = false;
	}

}

