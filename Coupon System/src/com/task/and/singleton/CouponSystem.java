package com.task.and.singleton;

import java.sql.SQLException;


import com.added.functions.DBconnectorV3;
import com.dao.interfaces.*;
import com.dbdao.*;
import com.exceptionerrors.*;
import com.facade.AdminFacade;
import com.facade.ClientType;
import com.facade.CompanyFacade;
import com.facade.CustomerFacade;

public class CouponSystem implements CouponClientFacade {

			public static CouponSystem instance = null;
			private CompanyDAO compDao = null;
			private CustomerDAO custDao = null;
			private CouponDAO couponDao = null;
			private DailyCouponExpirationTask dailyTask = null;
			private Thread dailyTaskThread = null;
			
			
			// Constructor
			private CouponSystem() throws ConnectorException {
				compDao = new CompanyDBDAO();
				custDao = new CustomerDBDAO();
				couponDao = new CouponDBDAO();
				dailyTask = new DailyCouponExpirationTask(compDao, custDao, couponDao);
				dailyTaskThread = new Thread(dailyTask);
				
				DBconnectorV3.startPool();
				dailyTaskThread.start();
				
			}
			
			public static CouponSystem getInstance() throws ConnectorException {
				if (instance == null) {
					instance = new CouponSystem();
				}
				return instance;
			}
		
			public void stop() throws ConnectorException, SQLException {
				
				dailyTaskThread.interrupt();
				dailyTask.stop();
				
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
			
			public CouponClientFacade login(String userName, String password, ClientType client) throws LoginException, DaoException, ConnectorException{
				
				CouponClientFacade clientFacade = null;
				
				if(client.equals(ClientType.ADMIN) ) {
					AdminFacade admF = new AdminFacade();
					clientFacade = admF.login(userName, password, client);
					return clientFacade;
				}
				else if (client.equals(ClientType.COMPANY)) {
					CompanyFacade compF = new CompanyFacade();			
					clientFacade = compF.login(userName, password, client);

					return clientFacade;
				}
				else if (client.equals(ClientType.CUSTOMER)) {
					CustomerFacade custF = new CustomerFacade();
					clientFacade = custF.login(userName, password, client);
					return clientFacade;
				}
				return null;
				}
			
			
	
}
