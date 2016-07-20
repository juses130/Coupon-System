package com.javabeans;
import java.time.LocalDate;

import com.exeptionerrors.FiledErrorException;
import com.sun.corba.se.spi.orbutil.fsm.State;

import sun.security.krb5.internal.PAData.SaltAndParams;



public class Coupon {

	// Attributes

	
	
	private long id;
	private String title;
	private LocalDate startDate = LocalDate.now();
	private LocalDate endDate = LocalDate.now();
	private int amount;
	private CouponType category ;
	private String message;
	private double price;
	private String image;
	private long ownerID;
	
	
	// Constructor
	public Coupon(){}
	
	public Coupon(String title, LocalDate startDate, LocalDate endDate, int amount, CouponType category, String message, double price, String image, long ownerID) throws FiledErrorException{
		
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(category);
		setMessage(message);
		setPrice(price);
		setImage(image);
		setOwnerID(ownerID);
		
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

	public Coupon(long id, String title, LocalDate startDate, LocalDate endDate, int amount, CouponType category,  String message, double price, String image) throws FiledErrorException {
		
		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(category);
		setMessage(message);
		setPrice(price);
		setImage(image);
		
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.amount = amount;
		this.category = category;
		this.endDate = endDate;
		this.message = message;
		this.price = price;
		this.image = image;
		
	}
	
	//Getters && Setters

	public Coupon(LocalDate endDate, int amount, String message, double price) throws FiledErrorException {
		
		setEndDate(endDate);
		setMessage(message);
		setAmount(amount);
		setPrice(price);
		
		this.endDate = endDate;
		this.message = message;
		this.amount = amount;
		this.price = price;

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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) throws FiledErrorException {
		
		if(title.isEmpty()) {
			throw new FiledErrorException("Error: Set Title - FAILED (empty field)");
		}
		else {
			this.title = title;
		}
	}


	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) throws FiledErrorException {

		if(startDate.isAfter(LocalDate.now()) ) {
			this.startDate = startDate;
		}
		else {
			throw new FiledErrorException("Error: The End-Date Of The Coupon Has To Be AFTER Today AND The Start-Date!");
		}
		
	}

	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) throws FiledErrorException {
		
		if(endDate.isBefore(LocalDate.now())) { // if the STARTDate is before today, throw exception
			throw new FiledErrorException("Error: The End-Date Of The Coupon Has To Be AFTER Today!");
		}
		else {
			this.endDate = endDate;
		}
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) throws FiledErrorException {
		
		if(amount < 0) {
			throw new FiledErrorException("Error: Setting Amount - FAILED (Field Empty or Under Zero!)");
		}
		else 
		{
			this.amount = amount;
		}
		
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) throws FiledErrorException {

		if(message.isEmpty()) {
			throw new FiledErrorException("Error: Set Message - FAILED (empty field)");
		}
		else {
			this.message = message;
		}
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) throws FiledErrorException {
		
		if(price < 0) {
			throw new FiledErrorException("Error: Setting Price - FAILED (Field Empty or Under Zero!)");
		}
		else {
			this.price = price;
		}
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		
		if(image.isEmpty()) {
			image = "No Image";
			this.image = image;
		}
		else {
			this.image = image;
		}
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
		
		if(ownerID > 0) {// if id is UNDER '0' then reset 'this.id' to 0.
			ownerID = 0;
			this.ownerID = ownerID;
		}
		else {
			this.ownerID = ownerID;
		}

	}

	// ToString
	@Override
	public String toString() {
		return "\n" + "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", Category= " + category + ", amount=" + amount + ", message=" + message + ", price=" + price + "$ " + ", image=" + image + "Owner ID=" + ownerID + "]";
	}
}
