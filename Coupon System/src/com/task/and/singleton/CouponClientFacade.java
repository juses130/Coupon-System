package com.task.and.singleton;


import com.exceptionerrors.*;
import com.facade.ClientType;

/**
 * This is the interface 'CouponClientFacade'.
 * all the facades are implements from it and the login function has to implement by this class.
 * 
 * @author Raziel
 *
 */

public interface CouponClientFacade {
	public CouponClientFacade login(String userName, String password, ClientType type) throws LoginException, DaoException, ConnectorException ;
	
}
