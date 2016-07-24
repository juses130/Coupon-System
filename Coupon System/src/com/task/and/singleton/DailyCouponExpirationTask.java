package com.task.and.singleton;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.added.functions.DBconnectorV3;
import com.dao.interfaces.*;
import com.exeptionerrors.ConnectorExeption;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.FiledErrorException;
import com.facade.CompanyFacade;
import com.javabeans.*;
import com.sun.corba.se.spi.orbutil.fsm.State;

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
		
		try {
			/* I think it's more good when the task will delete expired coupons BY AN SQL-COMMAND..
			 * Because when it will be in Internet Platforme and connected to Server.. it will use
			 * the internet Clock-TIME of the server / sql Database and the user cannot manipulated that.
			 */
			while (running) {
			System.out.println("\n" + "Wait! - " +"[Deleting Expired Coupons]");
			deleteCoupon();
			
			running = false;

		} //while - running
		
				TimeUnit.HOURS.sleep(24);
				Thread.currentThread().interrupt();
				} // try 
				catch (InterruptedException | DaoExeption e) {
					e.printStackTrace();
					
				} // catch
		
	} // run()
	private void deleteCoupon() throws DaoExeption {
			
			try {
				String sqlSelectByEndDate = "DELETE coupon.*, company_coupon.*, customer_coupon.* "
						+ "FROM coupon "
						+ "LEFT JOIN company_coupon USING (coup_id) "
						+ "LEFT JOIN customer_coupon USING (coup_id) "
						+ "WHERE coupon.End_Date < CURDATE() "
						+ "AND coupon.coup_id IS NOT NULL;";
				PreparedStatement prep = null;
				prep = DBconnectorV3.getConnection().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				prep.executeUpdate();
			} catch (SQLException e) {
				throw new DaoExeption("Error: Deleting Expired Coupon - FAILD (something went wrong..)");
			}


	}

	public void stop() {
		running = false;
	}

}

