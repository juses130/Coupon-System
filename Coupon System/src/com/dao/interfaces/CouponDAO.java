package com.dao.interfaces;

import java.util.*;

import com.exeptionerrors.DaoExeption;
import com.javabeans.*;

public interface CouponDAO {

	public Coupon createCoupon(Coupon newCoupons) throws DaoExeption;
	public void removeCoupon(Coupon remCoupon) throws DaoExeption;
	public Coupon updateCoupon(Coupon upCoupon) throws DaoExeption;
	public Coupon getCoupon(long id) throws DaoExeption;
	public Collection<Coupon> getAllCoupon() throws DaoExeption;
	
	public Set<Coupon> getCouponByPrice(String table, String colmun, long compID, double maxPrice) throws DaoExeption;
	public Set<Coupon> getCouponByType(String table, String colmun, long id, CouponType category) throws DaoExeption;
	public Set<Coupon> getCouponsOfCompany(long compID) throws DaoExeption;
}
