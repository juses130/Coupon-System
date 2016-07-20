package com.task.and.singleton;


import java.net.ConnectException;

import com.facade.ClientType;

import ExeptionErrors.ConnectorExeption;
import ExeptionErrors.DaoExeption;
import ExeptionErrors.LoginException;

/**
 * This is the interface 'CouponClientFacade'.
 * all the facades are implements from it and the login function has to implement by this class.
 * 
 * @author Raziel
 *
 */

public interface CouponClientFacade {
	public CouponClientFacade login(String userName, String password, ClientType type) throws LoginException, DaoExeption, ConnectorExeption ;
	
}
