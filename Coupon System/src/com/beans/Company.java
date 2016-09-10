package com.beans;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.exceptionerrors.FiledErrorException;
@XmlRootElement

public class Company {
	
	// Attributes
	// TODO: fixed the logical thinking here -> What to do with the Nulls and empty fileds? i Can't get a collection of objects when they have null because is doing checks to every object!
	private long id = 0;
	private String compName = "";
	private String password = "";
	private String email = "";
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
		return compName == null ? "" : compName;
	} // getCompName

	public void setCompName(String compName) throws FiledErrorException {
		
		// check if compName is null, if yes - put it as empty field.
		if(compName != null) {
			if(!compName.isEmpty()) {
				this.compName = compName;
			} // if
			else {
				throw new FiledErrorException("Error: Setting Company Name - FAILED (empty field)");	
			}
			
		} // if - null
		else {
			throw new FiledErrorException("Error: Setting Company Name - FAILED (NULL field)");
		} // else
	} // setCompName

	public String getEmail() {
		// The condition says "if email IS null, put empty String, else- return the email.
		return email == null ? "" : email;
	} // getEmail

	public void setEmail(String email) throws FiledErrorException{

		if(email != null) {
			if(email.contains("@")) {
				this.email = email;
			} // if
			else {
				throw new FiledErrorException("Error: Setting Email To Company - FAILED (maybe you forgot '@' ?)");
			} // else
		} // if - null
		else {
			throw new FiledErrorException("Error: Setting Email To Company - FAILED (NULL field)");
		} // else - null
	} // setEmail
	
	public String getPassword() {
		return password == null ? "" : password;
	} // getPassword
	
	public void setPassword(String password) throws FiledErrorException {
		
		// check if password is null, if yes - put it as empty field.
		if(password != null) {
			if(!password.isEmpty()) {
				this.password = password;
			} // if
			else {
				throw new FiledErrorException("Error: Setting Password - FAILED (empty field)");
			} // else
		} // if - null
		else {
			throw new FiledErrorException("Error: Setting Password - FAILED (NULL field)");
		} // else
		
		// Check if password is empty, if no - insert it into 'this.password' (Attribute)

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
