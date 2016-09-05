package com.javabeans;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.exceptionerrors.FiledErrorException;
@XmlRootElement

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
	} // Constructor

	public Company(long id, String compName, String password, String email) throws FiledErrorException {
		
		setCompName(compName);
		setEmail(email);
		setPassword(password);
		
		this.id = id;
		this.compName = compName;
		this.email = email;
		this.password = password;
	} // // Constructor

	public long getId() {
		return id;
	} // getId

	public void setId(long id) {
		if(id < 0) { // if id is UNDER '0' then reset 'this.id' to 0.
			id = 0;
			this.id = id;
		} // if
		else {
			this.id = id;
		} // else
	} // setId

	public String getCompName() {
		return compName;
	} // getCompName

	public void setCompName(String compName) throws FiledErrorException {
		
		if(compName.isEmpty()) {
			throw new FiledErrorException("Error: Setting Company Name - FAILED (empty field)");
		} // if
		else {
			this.compName = compName;
		} // else
	} // setCompName

	public String getEmail() {
		return email;
	} // getEmail

	public void setEmail(String email) throws FiledErrorException{
		
		if(email.contains("@")) {
			this.email = email;
		} // if
		else {
			throw new FiledErrorException("Error: Setting Email To Company - FAILED (maybe you forgot '@' ?)");
		} // else
	} // setEmail
	
	public String getPassword() {
		return password;
	} // getPassword
	
	public void setPassword(String password) throws FiledErrorException {
		if(password.isEmpty()) {
			throw new FiledErrorException("Error: Setting Password - FAILED (empty field)");
		} // if
		else {
			this.password = password;
		} // else
	} // setPassword
	
	// ToString
	@Override
	public String toString() {
		return "\n" + "Company [ID=" + id + ", Name=" + compName + ", Email=" + email + "]";
	} // toString

	public Collection<Coupon> getCoupons() {
		return coupons;
	} // getCoupons

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	} // setCoupons
	
} // Class
