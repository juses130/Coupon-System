package com.task.and.singleton;

import com.dao.interfaces.*;
import com.dbdao.*;

public class CouponSystem {

			public static CouponSystem instance = null;
			private CompanyDAO compDao = null;
			private CustomerDAO custDao = null;
			private CouponDAO couponDao = null;
			private DailyCouponExpirationTask dailyTask = null;
			private Thread dailyTaskThread = null;
			
			private CouponSystem() {
				compDao = new CompanyDBDAO();
				custDao = new CustomerDBDAO();
				couponDao = new CouponDBDAO();
				dailyTask = new DailyCouponExpirationTask(compDao, custDao, couponDao);
				dailyTaskThread = new Thread(dailyTask);
				dailyTaskThread.start();		
			}
			
			public static CouponSystem getInstance() {
				if (instance == null) {
					instance = new CouponSystem();
				}
				return instance;
			}
		
			public void stop() {
				dailyTask.stop();
				dailyTaskThread.interrupt();
			}
			public CompanyDAO getCompDao() {
				return compDao;
			}
		
			public CustomerDAO getCustDao() {
				return custDao;
			}
		
			public CouponDAO getCouponDao() {
				return couponDao;
			}
	
}
