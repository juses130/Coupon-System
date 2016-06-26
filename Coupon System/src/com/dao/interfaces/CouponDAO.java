package com.dao.interfaces;

import java.util.*;
import com.javabeans.*;

public interface CouponDAO {

	public long createCoupon(Coupon newCoupon);
	public void removeCoupon(Coupon remCoupon);
	public void updateCoupon(Coupon upCoupon);
	public Coupon getCoupon(long id);
	public Collection<Coupon> getAllCoupon();
	public Collection<Coupon> getCouponByType(CouponType category);
}
