package checkingpack;

import java.sql.ClientInfoStatus;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;

import com.added.functions.DBconnectorV3;
import com.added.functions.SharingData;
import com.dao.interfaces.*;
import com.dbdao.*;
import com.facade.AdminFacade;
import com.facade.ClientType;
import com.facade.CompanyFacade;
import com.facade.CustomerFacade;
import com.javabeans.CouponType;
import com.javabeans.Customer;
import com.mysql.fabric.xmlrpc.Client;
import com.task.and.singleton.DailyCouponExpirationTask;

import ExeptionErrors.DaoExeption;

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
				DBconnectorV3.startPool();
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
				
				try {
					DBconnectorV3.getConnection().close();
				} catch (SQLException e) {
					SharingData.setExeptionMessage(e.getMessage());
				} // catch
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
			
			public ClientType login(String userName, String password, ClientType type)  {
				
				ClientType clientTypeResult = null;
				// TODO: פה הלוגיקה של השאלה צריך לדעת מה להחיר לו נכון
				if(type == ClientType.ADMIN) {
					AdminFacade admF = new AdminFacade();
					try {
						admF = admF.login(userName, password, ClientType.ADMIN);
						if(admF != null) {
							clientTypeResult = ClientType.ADMIN;
							return clientTypeResult;
						}
					} catch (LoginException e) {
						e.getMessage();
					} // catch
					
//					return ClientType.ADMIN;
				} // if - it admin
				else if (type == ClientType.COMPANY) {
					CompanyFacade comF = new CompanyFacade();
					try {
						comF = comF.login(userName, password, ClientType.COMPANY);
					} catch (LoginException e) {
						e.getMessage();
					} catch (DaoExeption e) {
						e.getMessage();
					} // catch
					
					return ClientType.COMPANY;
				} // else if - its company
				else if (type == ClientType.CUSTOMER) {
					CustomerFacade cusF = new CustomerFacade();
					try {
						cusF = cusF.login(userName, password, ClientType.CUSTOMER);
					} catch (LoginException e) {
						e.getMessage();
					} catch (DaoExeption e) {
						e.getMessage();
					} // catch
					
					return ClientType.CUSTOMER;
				} // else if - customer
				else {
					return null;
				}
				return type;
			}
	
}
