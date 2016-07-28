package com.javabeans;

import java.util.Collection;

import com.exceptionerrors.FiledErrorException;

public class Company {
	
	// Attributes
	
	private long id = 0;
	private String compName = null;
	private String password = null;
	private String email = null;
	private Collection<Coupon> coupons;
	
	// Constructor
	public Company(){}
	
	public Company(String compName, String password, String email) throws FiledErrorException {
		
		setCompName(compName);
		setEmail(email);
		setPassword(password);
		
		this.compName = compName;
		this.email = email;
		this.password = password;
	}

	public Company(long id, String compName, String password, String email) throws FiledErrorException {
		
//		setId(id);
		setCompName(compName);
		setEmail(email);
		setPassword(password);
		
		this.id = id;
		this.compName = compName;
		this.email = email;
		this.password = password;
	}
	//Getters && Setters (I didn't gave here getter for Password)
	
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

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) throws FiledErrorException {
		
		if(compName.isEmpty()) {
			throw new FiledErrorException("Error: Setting Company Name - FAILED (empty field)");
		}
		else {
			this.compName = compName;
		}
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws FiledErrorException{
		
		if(email.contains("@")) {
			this.email = email;
		}
		else {
			throw new FiledErrorException("Error: Setting Email To Company - FAILED (maybe you forgot '@' ?)");
		}
		
	}
	
	public String getPassword() {
		return password;
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
		return "\n" + "Company [ID=" + id + ", Name=" + compName + ", Email=" + email + "]";
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	
}
