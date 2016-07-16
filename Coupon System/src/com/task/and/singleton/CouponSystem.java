package com.task.and.singleton;

import java.sql.ClientInfoStatus;
import java.sql.SQLException;

import com.added.functions.DBconnectorV3;
import com.dao.interfaces.*;
import com.dbdao.*;
import com.facade.AdminFacade;
import com.facade.ClientType;
import com.facade.CompanyFacade;
import com.facade.CustomerFacade;
import com.javabeans.Customer;
import com.unusedclasses.CouponClientFacade;

public class CouponSystem {

			public static CouponSystem instance = null;
			private CompanyDAO compDao = null;
			private CustomerDAO custDao = null;
			private CouponDAO couponDao = null;
			private DailyCouponExpirationTask dailyTask = null;
			private Thread dailyTaskThread = null;
			
			// Constructor
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
		
			public void stop() throws SQLException {
				dailyTask.stop();
				dailyTaskThread.interrupt();
				DBconnectorV3.getConnection().close();
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
			
			public boolean login(String userName, String password, ClientType type) {
				if(type == ClientType.ADMIN) {
					AdminFacade admF = new AdminFacade();
					boolean exsistOrNot = admF.login(userName, password);
					return exsistOrNot;
				} // if - it admin
				else if (type == ClientType.COMPANY) {
					CompanyFacade comF = new CompanyFacade();
					boolean exsistOrNot = comF.login(userName, password);
					return exsistOrNot;
				} // else if - its company
				else if (type == ClientType.CUSTOMER) {
					CustomerFacade cusF = new CustomerFacade();
					boolean exsistOrNot = cusF.login(userName, password);
					return exsistOrNot;
				} // else if - customer
				return false;
			}
	
}
