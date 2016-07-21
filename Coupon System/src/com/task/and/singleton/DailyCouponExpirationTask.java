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
			/* I think it's more good when the task will delete expired coupons BY THE SQL-COMMEND..
			 * Because when it will be in Internet Platforme and connected to Server.. it will use
			 * the time of the server / sql Database and the user cannot manipulated that.
			 */
			
			// TODO: chagne the method of delete - I will delete this directly from SQL.
			System.out.println("\n" + "Wait! - " +"[Deleting Expired Coupons]");

	while (running) {
				//TODO: now I have a method that will delete the rows from all tables at ones! just added to here!
		String sqlSelectByEndDate = "DELETE coupon.*, company_coupon.*, customer_coupon.* FROM coupon LEFT JOIN company_coupon USING (coup_id) LEFT JOIN customer_coupon USING (coup_id) WHERE coupon.End_Date < CURDATE() AND coupon.coup_id IS NOT NULL;";
		PreparedStatement prep = null;
		prep = DBconnectorV3.getConnection().prepareStatement(sqlSelectByEndDate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
//		prep.executeUpdate();
		running = false;
//		rs.next();
//		Set<Long> couponIDs = new HashSet<>();
////		ArrayList<Long> couponIDs = new ArrayList<>();
//		
//		if(rs.next() != true) {
//			running = false;
//			System.out.println("Error: No expired Coupons was detected in the DataBase");
//			break;
//		}
//		else {
//			while(rs.next()) { 
				/* save all the ids of the coupons by the expired date. 
				 * I'm not using Object Coupon because it's will build all the instances + setting them.. each itaration (rs.next)! 
				 * and in the Join tables, Iv'e only have ids.. so It is requested.
				 * and here, after we saved the id to the Set<> it will delete by the ResultSet.
				 */
//				couponIDs.add(rs.getLong(1));
//				rs.deleteRow();
//				couponIDs.iterator().hasNext();
//				if(couponIDs.isEmpty()) {
//					running = false;
//					break;
//				}
//				System.out.println(couponIDs.toString());
			} //while - running
			
//					prep.clearBatch();
//					System.out.println("Coupon ID: " + couponIDs.size());
//					int arraySize = couponIDs.size();
//					int counter = arraySize - 1 ;
//					prep.clearBatch();
//					
//					String sqlSelectFromCompanyCouTable = "DELETE FROM company_coupon WHERE coup_id=?" ;
//					prep = DBconnectorV3.getConnection().prepareStatement(sqlSelectFromCompanyCouTable);
//					prep.setLong(1, x);	
//						
//					long id = couponIDs.iterator().next().longValue();
//					System.out.println("Coupon ID Delete: " + id);
					
						

			
//					break;
				

//		} // else - main

//		} // while  - running
		

//				TimeUnit.HOURS.sleep(24);
//				Thread.currentThread().interrupt();
				} // try 
				catch (SQLException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					
				} // catch
		
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

