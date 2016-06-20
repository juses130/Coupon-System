package com.dao.interfaces;

import java.util.*;
import com.javabeans.*;

public interface CouponDAO {

	public void createCoupon(Coupon newCoupon);
	public void removeCoupon(Coupon remCoupon);
	public void updateCoupon(Coupon upCoupon);
	public Coupon getCoupon();
	public Collection<Coupon> getAllCoupon();
	public Collection<Coupon> getCouponByType();
}
