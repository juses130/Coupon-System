package com.javabeans;

import java.util.Collection;

public class Customer {
	
	// Attributes
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	
	// Constructor
	public Customer() {}

	//Getters && Setters
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	// ToString
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", coupons=" + coupons + "]";
	}
	
	
	
}
