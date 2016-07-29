package com.dao.interfaces;

import java.util.*;

import com.exceptionerrors.DaoException;
import com.facade.ClientType;
import com.javabeans.*;

public interface CouponDAO {

	
	public Coupon createCoupon(Coupon coupon) throws DaoException;
	public void removeCoupon(Coupon coupon, ClientType client) throws DaoException;
	public Coupon updateCoupon(Coupon coupon) throws DaoException;
	public Coupon getCoupon(long id, ClientType client) throws DaoException;
	public Set<Coupon> getCoupons(long id, ClientType client) throws DaoException;
	//TODO: this 2 fucntions to much generals.
	public Set<Coupon> getCouponByPrice(long id, double price,ClientType client) throws DaoException;
	public Set<Coupon> getCouponByType(long id, CouponType category, ClientType client) throws DaoException;
	
}
