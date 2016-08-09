package com.dbdao;

/**
 * <p>CheckCouponBy</p>
 * This enum helps the Class {@code CouponFoundInDatabase} to check
 * if the coupon has a Name ({@code String}) or has ID {@code long}.
 * Basically, Now the program using only {@code CheckCouponBy.BY_NAME}.
 * But if we will need in the future to send a coupon by the ID {@code long},
 * we can do it by sending the {@code CheckCouponBy.BY_ID} 
 * and the Class {@code CouponFoundInDatabase} will work with that 
 * 
 * @author Raziel
 */

public enum CheckCouponBy {

	BY_NAME, BY_ID
	
}
