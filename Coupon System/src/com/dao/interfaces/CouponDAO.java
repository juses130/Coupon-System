package com.dao.interfaces;

import java.util.*;
import com.javabeans.*;

public interface CouponDAO {

	public long createCoupon(Coupon newCoupon);
	public void removeCoupon(Coupon remCoupon);
	public void updateCoupon(Coupon upCoupon);
	public Coupon getCoupon(long id);
	public Collection<Coupon> getAllCoupon();
	/**
	 * Iv'e added this function for the ownerID. the NEW column in Coupon Table.
	 * It makes sense to see which company ownes this coupon (by id).
	 * @param ownerID
	 * @author Raziel
	 */
	public void removeCouponOwnerID(long ownerID);
	/**
	 * You can see the explain of this method in CouponDBDAO.
	 * 
	 * @param companyCreator
	 * @author Raziel
	 */
	public void setCreator(short companyCreator);
	Set<Coupon> getCouponByPrice(String table, String colmun, long compID, double maxPrice);
	Set<Coupon> getCouponByType(String table, String colmun, long id, CouponType category);
}
