package com.task.and.singleton;

import java.sql.SQLException;


import com.added.functions.DBconnectorV3;
import com.dao.interfaces.*;
import com.dbdao.*;
import com.facade.AdminFacade;
import com.facade.ClientType;
import com.facade.CompanyFacade;
import com.facade.CustomerFacade;
import ExeptionErrors.*;

public class CouponSystem implements CouponClientFacade {

			public static CouponSystem instance = null;
			private CompanyDAO compDao = null;
			private CustomerDAO custDao = null;
			private CouponDAO couponDao = null;
			private DailyCouponExpirationTask dailyTask = null;
			private Thread dailyTaskThread = null;
			
			
			// Constructor
			private CouponSystem() throws ConnectorExeption {
				compDao = new CompanyDBDAO();
				custDao = new CustomerDBDAO();
				couponDao = new CouponDBDAO();
				dailyTask = new DailyCouponExpirationTask(compDao, custDao, couponDao);
				dailyTaskThread = new Thread(dailyTask);
				
				DBconnectorV3.startPool();
				dailyTaskThread.start();
				
			}
			
			public static CouponSystem getInstance() throws ConnectorExeption {
				if (instance == null) {
					instance = new CouponSystem();
				}
				return instance;
			}
		
			public void stop() throws ConnectorExeption, SQLException {
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
			
			public CouponClientFacade login(String userName, String password, ClientType type) throws LoginException, DaoExeption, ConnectorExeption{
				
				CouponClientFacade client = null;
				
				if(type.equals(ClientType.ADMIN) ) {
					AdminFacade admF = new AdminFacade();
					client = admF.login(userName, password, type);
					return client;
				}
				else if (type == ClientType.COMPANY) {
					CompanyFacade compF = new CompanyFacade();			
					client = compF.login(userName, password, type);

					return client;
				}
				else if (type.equals(ClientType.CUSTOMER)) {
					CustomerFacade custF = new CustomerFacade();
					client = custF.login(userName, password, type);
					return client;
				}
				return null;
				}
			
			
	
}
