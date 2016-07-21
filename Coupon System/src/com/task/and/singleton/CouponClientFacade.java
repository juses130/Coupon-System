package com.task.and.singleton;


import com.exeptionerrors.ConnectorExeption;
import com.exeptionerrors.DaoExeption;
import com.exeptionerrors.LoginException;
import com.facade.ClientType;

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
