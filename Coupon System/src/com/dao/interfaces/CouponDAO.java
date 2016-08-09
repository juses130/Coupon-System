package com.dao.interfaces;

import java.util.*;

import com.exceptionerrors.DaoException;
import com.facade.ClientType;
import com.javabeans.*;

public interface CouponDAO {

	/**
	 * Adds an Coupon from the underlying database (or any other persistence storage).</br>
	 * (But before that, it will check if the coupon is already exist in the Company Database)
	 * </br>
	 * You may ask why do we need the {@code Company} Object? </br>
	 * The thing is, we need to make sure that the {@code Company} <b>dosen't have the NEW {@code Coupon} more then ones.</b> </br>
	 * So when we have them both {@code Company} && {@code Coupon} Objects - we can compare them to the Database and check it.
	 * @param company a {@code Company} Object
	 * @param coupon a {@code Coupon	} Object
	 * @throws DaoException
	 */
	public Coupon createCoupon(Coupon coupon, Company company) throws DaoException;
	
	/**
	 * Remove a Coupon from the underlying database (or any other persistence storage).
	 * 
	 * @param coupon a {@code Coupon} Object
	 * @param client a {@code ClientType} Enum
	 * @throws DaoException
	 */
	public void removeCoupon(Coupon coupon, ClientType client) throws DaoException;
	
	/**
	 * Updates a Coupon from the underlying database (or any other persistence storage).
	 * 
	 * @param coupon a {@code Coupon} Object
	 * @return a {@code Coupon} Object
	 * @throws DaoException
	 */
	public Coupon updateCoupon(Coupon coupon) throws DaoException;
	
	/**
	 * Returns a {@code Coupon} object from the underlying database 
	 * (or any other persistence storage).
	 * 
	 * @param id {@code long} of the Client (it can be Customer or Company)
	 * @param client {@code ClientType} Enum
	 * @return a {@code Coupon} Object
	 * @throws DaoException
	 */
	public Coupon getCoupon(long id, ClientType client) throws DaoException;
	
	/**
	 * Returns a {@code Set<Coupon>} of all Coupons from the 
	 * underlying database (or any other persistence storage).
	 * 
	 * @param id {@code long} of the Client (it can be Customer or Company)
	 * @param client {@code ClientType} Enum 
	 * @return a {@code Set<Coupon>}
	 * @throws DaoException
	 */
	public Set<Coupon> getCoupons(long id, ClientType client) throws DaoException;
	
	/**
	 * Returns a {@code Set<Coupon>} of all Coupons by the maximum price {@code double} from the 
	 * underlying database (or any other persistence storage).
	 * 
	 * @param id {@code long} of the Client (it can be Customer or Company)
	 * @param price {@code double} of the Coupons price
	 * @param client {@code ClientType} Enum
	 * @return a {@code Set<Coupon>} 
	 * @throws DaoException
	 */
	public Set<Coupon> getCouponByPrice(long id, double price,ClientType client) throws DaoException;
	
	/**
	 * Returns a {@code Set<Coupon>} of all Coupons by the Category {@code CouponType} from the 
	 * underlying database (or any other persistence storage).
	 * 
	 * @param id {@code long} of the Client (it can be Customer or Company)
	 * @param category {@code CouponType} Enum
	 * @param client {@code ClientType} Enum
	 * @returna {@code Set<Coupon>} 
	 * @throws DaoException
	 */
	public Set<Coupon> getCouponByType(long id, CouponType category, ClientType client) throws DaoException;
	
}
