package com.dao.interfaces;

import java.util.*;

import com.exeptionerrors.DaoExeption;
import com.facade.ClientType;
import com.javabeans.*;

public interface CouponDAO {

	public Coupon createCoupon(Coupon coupon) throws DaoExeption;
	public void removeCoupon(Coupon coupon, ClientType client) throws DaoExeption;
	public Coupon updateCoupon(Coupon coupon) throws DaoExeption;
	public Coupon getCoupon(long id, ClientType client) throws DaoExeption;
	public Set<Coupon> getCoupons(long id, ClientType client) throws DaoExeption;
	//TODO: this 2 fucntions to much generals.
	public Set<Coupon> getCouponByPrice(long id, long price,ClientType client) throws DaoExeption;
	public Set<Coupon> getCouponByType(long id, CouponType category, ClientType client) throws DaoExeption;
	
}
