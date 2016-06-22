package com.javabeans;

import java.util.Collection;

public class Company {
	
	// Attributes
	
	private long id = 0;
	private String compName = null;
	private String password = null;
	private String email = null;
	private Collection<Coupon> coupons;
	
	// My new Instance only for the choice parts in the main Class.
	private short choice;
	
	// Constructor
	public Company(){}
	
	public Company(String compName, String password, String email) {
		this.compName = compName;
		this.email = email;
		this.password = password;
	}

	public Company(long id, String compName, String password, String email) {
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
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public short getChoice() {
		return choice;
	}
	
	public void setChoice(short choice) {
		this.choice = choice;
	}
	// ToString
	@Override
	public String toString() {
		return "\n" + "Company [ID=" + id + ", Name=" + compName + ", Email=" + email + "]";
	}

	
}
