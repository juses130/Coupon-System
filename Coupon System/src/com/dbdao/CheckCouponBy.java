package com.dbdao;

/**
 * <p>CheckCouponBy</p>
 * This enum helps to the function {@code isExistInJoinTables } to check
 * if the coupon has a Name ({@code String}) or has an ID {@code long}.
 * Basiclly, Now the program using only {@code CheckCouponBy.BY_NAME}.
 * But if we will need in the future to send a coupon by the ID {@code long},
 * we can do it by sending the {@code CheckCouponBy.BY_ID} 
 * and the Function {@code isExistInJoinTables} will work with that 
 * 
 * 
 * @author Raziel
 * 
 */

public enum CheckCouponBy {

	BY_NAME, BY_ID
	
}
