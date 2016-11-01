package com.beans;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.exceptionerrors.FiledErrorException;

@XmlAccessorType(XmlAccessType.FIELD)

public class Customer {
	
	// Attributes
	
	private long id;
	private String custName = "";
	@XmlTransient private String password = "";
	private Collection<Coupon> coupons;
	
	// Constructor
	public Customer() {}
	
	public Customer(long id, String custName, String password) throws FiledErrorException {
		
		setId(id);
		setCustName(custName);
		setPassword(password);
		
		this.id = id;
		this.custName = custName;
		this.password = password;
	} // Constructor
	
	public Customer(String custName, String password) throws FiledErrorException {
		
		setCustName(custName);
		setPassword(password);
		
		this.custName = custName;
		this.password = password;
	} // Constructor
	
	//Getters && Setters

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

	public String getCustName() {
		return custName == null  ? "" : custName;
	} // getCustName

	public void setCustName(String custName) throws FiledErrorException {
		
		if(custName.isEmpty()) {
			throw new FiledErrorException("Error: Setting Customer Name - FAILED (empty field)");
		} // if
		else {
			this.custName = custName;
		} // else
	} // setCustName

	public Collection<Coupon> getCoupons() {
		return coupons;
	} // getCoupons

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	} // setCoupons
	
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
		return "\n" + "Customer [id=" + id + ", custName=" + custName + ", coupons=" + coupons + "]";
	} // toString

	public String getPassword() {
		return password == null ? "" : password;
	} // getPassword
	
} // Class
