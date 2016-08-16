package com.task.and.singleton;


import com.exceptionerrors.*;
import com.facade.ClientType;

/**
 * This is the CouponClientFacade {@code Interface}.
 * </br>
 * All the Facades Classes are implements from this {@code Interface} 
 * and the Login {@code Function} has to implement by this class.
 * @author Raziel
 *
 */

public interface CouponClientFacade {
	
	/**
	 * Returns a CouponClientFacade {@code Interface} if the log-in was successful, 
	 * otherwise it will Return an Error {@code Exception}. 
	 * 
	 * @param userName {@code String} of the Client
	 * @param password {@code long} of the Client
	 * @param clientType {@code ClientType} 
	 * @return a {@code CouponClientFacade} Interface Or Exception
	 * @throws LoginException
	 * @throws DaoException
	 * @throws ConnectorException
	 */
	
	public CouponClientFacade login(String userName, String password, ClientType clientType) throws LoginException, DaoException, ConnectorException ;
	
}
