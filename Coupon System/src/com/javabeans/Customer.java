package com.javabeans;

import java.util.Collection;

import com.exeptionerrors.FiledErrorException;

public class Customer {
	
	// Attributes
	
	private long id;
	private String custName = "";
	private String password = "";
	private Collection<Coupon> coupons;
	
	// Constructor
	public Customer(long id, String custName, String password) throws FiledErrorException {
		
		setId(id);
		setCustName(custName);
		setPassword(password);
		
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	
	//Getters && Setters

	public Customer() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		
		if(id < 0) { // if id is UNDER '0' then reset 'this.id' to 0.
			id = 0;
			this.id = id;
		}
		else {
			this.id = id;
		}
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) throws FiledErrorException {
		
		if(custName.isEmpty()) {
			throw new FiledErrorException("Error: Setting Customer Name - FAILED (empty field)");
		}
		else {
			this.custName = custName;
		}
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public void setPassword(String password) throws FiledErrorException {
		if(password.isEmpty()) {
			throw new FiledErrorException("Error: Setting Password - FAILED (empty field)");
		}
		else {
			this.password = password;
		}
	}

	// ToString
	@Override
	public String toString() {
		return "\n" + "Customer [id=" + id + ", custName=" + custName + ", coupons=" + coupons + "]";
	}

	public String getPassword() {
		return password;
	}

	
	
}
