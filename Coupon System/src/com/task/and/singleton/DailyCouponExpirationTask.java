package com.task.and.singleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.added.functions.DBconnector;
import com.dao.interfaces.*;
import com.dbdao.CouponDBDAO;
import com.facade.AdminFacade;
import com.javabeans.*;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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
	private CouponDAO couponDao = null;
	private boolean running = true;
	
	// constructor
	public DailyCouponExpirationTask(CompanyDAO compDao, CustomerDAO custDao, CouponDAO couponDao) {
		this.compDao = compDao;
		this.custDao = custDao;
		this.couponDao = couponDao;

	}

	@Override
	public void run() {
		Set<Coupon> coupons = new HashSet<>();
		ResultSet rs = null;
		DBconnector.getCon();
		
		try {
		String sqlSelectByEndDate = "SELECT * FROM coupon WHERE End_Date < CURDATE()";
		PreparedStatement prep = DBconnector.getInstatce().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
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
						System.out.println("\n" + "Wait! - " +"[Deleting Expired Coupons]" + "\n");
							for (Coupon coupon : company.getCoupons()) {
								if (coupon.getEndDate().isBefore(LocalDate.now())) {
									deleteCoupon(coupon);
								}
						}
						try {
							TimeUnit.HOURS.sleep(24);
						} catch (InterruptedException e) {
							e.printStackTrace();
							Thread.currentThread().interrupt();
						}
					}
				}
	
	private void deleteCoupon(Coupon coupon) {
		// Remove coupon from company  customer_coupon  company_coupon
		CouponDBDAO coupDB = new CouponDBDAO();
		coupDB.removeCoupon(coupon);

	}

	public void stop() {
		running = false;
	}

}

