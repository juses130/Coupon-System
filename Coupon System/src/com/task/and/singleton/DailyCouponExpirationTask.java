package com.task.and.singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.dao.interfaces.*;
import com.exceptionerrors.DaoException;

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
	
	// Constructor
	public DailyCouponExpirationTask(CompanyDAO compDao, CustomerDAO custDao, CouponDAO couponDao) {
		this.compDao = compDao;
		this.custDao = custDao;
		this.coupDao = couponDao;
	} // Constructor

	@Override
	public void run() {
		
		try {
			/* I think it's more good when the task will delete expired coupons BY AN SQL-COMMAND..
			 * Because when it will be in Internet Platform and connected to Server.. it will use
			 * the Internet Clock-TIME of the server / sql Database. and the user cannot manipulated that.
			 */
			while (running) {
			System.out.println("\n" + "Wait! - " +"[Deleting Expired Coupons]");
			deleteExpiredCoupon();

			running = false;
			
		} //while - running
				TimeUnit.HOURS.sleep(24);

				} // try 
				catch (InterruptedException | DaoException e) {
					e.getMessage();
					assert false;
				} // catch
	} // run()
	
	private void deleteExpiredCoupon() throws DaoException {
			
			try {
				String sqlSelectByEndDate = "DELETE coupon.*, company_coupon.*, customer_coupon.* "
						+ "FROM coupon "
						+ "LEFT JOIN company_coupon USING (coup_id) "
						+ "LEFT JOIN customer_coupon USING (coup_id) "
						+ "WHERE coupon.End_Date < CURDATE() "
						+ "AND coupon.coup_id IS NOT NULL;";
				
				 PreparedStatement prep = DBconnector.getConnection().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				prep.executeUpdate();
				prep.clearBatch();
				
			} catch (SQLException e) {
				throw new DaoException("Error: Deleting Expired Coupon - FAILD (something went wrong..)");
			} // catch
	} // deleteCoupon

	public void stop() {
		running = false;
	} // stop

} // Class

