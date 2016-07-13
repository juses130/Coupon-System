package com.javabeans;
import java.time.LocalDate;

import com.added.functions.IsExistDB;




public class Coupon {

	// Attributes

	
	
	private long id;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private CouponType category ;
	private String message;
	private double price;
	private String image;
	private long ownerID;
	
	
	// Constructor
	public Coupon(){}
	
	public Coupon(String title, LocalDate startDate, LocalDate endDate, int amount, CouponType category, String message, double price, String image, long ownerID){
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.category = category;
		this.message = message;
		this.price = price;
		this.image = image;
		this.ownerID = ownerID;
	}

	public Coupon(long id, String title, LocalDate stDate, LocalDate enDate, int amount, CouponType category,  String message, double price, String image) {
		this.id = id;
		this.title = title;
		this.startDate = stDate;
		this.amount = amount;
		this.category = category;
		this.endDate = enDate;
		this.message = message;
		this.price = price;
		this.image = image;
		
	}
	
	//Getters && Setters

	public Coupon(LocalDate endDate, int amount, String message, double price) {
		this.endDate = endDate;
		this.amount = amount;
		this.message = message;
		this.price = price;

	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
	// ToString
	@Override
	public String toString() {
		return "\n" + "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", Category= " + category + ", amount=" + amount + ", message=" + message + ", price=" + price + "$ " + ", image=" + image + "]";
	}

	public CouponType getType() {
		return category;
	}

	public void setType(CouponType category) {
		this.category = category;
	}

	public long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;

	}
}
