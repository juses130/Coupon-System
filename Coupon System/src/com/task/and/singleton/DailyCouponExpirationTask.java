package com.task.and.singleton;

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
	
	private boolean running = true;
	
	// constructor
	public DailyCouponExpirationTask(CompanyDAO compDao, CustomerDAO custDao, CouponDAO couponDao) {

	}

	@Override
	public void run() {
		Set<Coupon> coupons = new HashSet<>();
		ResultSet rs = null;
		
		try {
		String sqlSelectByEndDate = "SELECT * FROM coupon WHERE End_Date < CURDATE()";
		PreparedStatement prep = DBconnectorV3.getConnection().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		rs = prep.executeQuery(sqlSelectByEndDate);
		CouponDBDAO coupDB = new CouponDBDAO();
		
		while(rs.next()) { 
			coupons.add(coupDB.getCoupon(rs.getLong(1)));
		}
		prep.clearBatch();
		} // try
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} // catch
		
		Company company = new Company();
		company.setCoupons(coupons);
					while (running) {
						System.out.println("\n" + "Wait! - " +"[Deleting Expired Coupons]");
							for (Coupon coupon : company.getCoupons()) {
								if (coupon.getEndDate().isBefore(LocalDate.now())) {
									deleteCoupon(coupon);
								}
						}
						try {
							TimeUnit.HOURS.sleep(24);
						} catch (InterruptedException e) {
							SharingData.setExeptionMessage(e.getMessage());
							Thread.currentThread().interrupt();
						}
					}
				}
	
	private void deleteCoupon(Coupon coupon) {
		// Remove coupon from company  customer_coupon  company_coupon
		CompanyFacade compF = new CompanyFacade();
		compF.removeCouponA(coupon);

	}

	public void stop() {
		running = false;
	}

}

