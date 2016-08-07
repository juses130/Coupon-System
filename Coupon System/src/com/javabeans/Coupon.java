package com.javabeans;
import java.time.LocalDate;
import java.util.HashSet;

import com.exceptionerrors.FiledErrorException;



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
	
	public Coupon(long id, String title, LocalDate startDate, LocalDate endDate, int amount, String category, String message, double price, String image, long ownerID) throws FiledErrorException{
		
		/**
		 * This part of the setters - is just for security. 
		 * When the user create new coupon = the constructor will have to set the values to the setters 
		 * before setting them to himself and actually create a new coupon.
		 * 
		 * And in the setters Iv'e added checking conditions. for example, in the setTitle() I want to make sure 
		 * that the user puts some real value. so if the title is NULL or empty the coupon will not create and it
		 * will send an Exception with message of "empty fileds". 
		 * 
		 * Same as Dates. we can't let the anyone to create a coupon with an EXPIRED DATES. 
		 * we have the make sure it will not happend. not for the Title or Dates or any coupon's attribute.
		 */
		
		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setCategory(category);
		setMessage(message);
		setPrice(price);
		setImage(image);
		setOwnerID(ownerID);
		
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.category = CouponType.valueOf(category.toUpperCase());
		this.message = message;
		this.price = price;
		this.image = image;
		this.ownerID = ownerID;
	}
	
	//Getters && Setters

	public long getId() {
		return id;
	}


	public void setId(long id) throws FiledErrorException {
		
		if(id < 0) { // if id is UNDER '0' then throw Exception.
			throw new FiledErrorException("Error: Set ID - FAILED");
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
			throw new FiledErrorException("Error: Set Title - FAILED (empty field!)");
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
		else if(startDate.isBefore(LocalDate.now())) {
			// Default start date will be LocalDate.now()
			startDate = LocalDate.now();
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
		
		if(amount <= 0) {
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

	public CouponType getCategory() {
		return category;
	}

	public void setCategory(String category) throws FiledErrorException  {		
		/*
		 * After testing the program, Iv'e got to a dead end when
		 * I'm putting strings into the Category (when it was CouponType) 
		 * and the program crashed.. the only way was to put the check in the TEST
		 * and that is ridiculous idea.
		 * 
		 * So Iv'e changed this setter that the user can send here 
		 * Strings. and here we will covert them to UpperCase and
		 * make some checks before accepting the inputs.
		 */
		
		category = category.toUpperCase();
		HashSet<String> values = new HashSet<String>();
		
		// putting all the categories in HashSet.
		for(CouponType c : CouponType.values()) {
        	if(c.name().equals(category)) {
        		values.add(c.name());
        	}
        	
        } // for
		if(values.contains(category)) {
			CouponType typeCategory = CouponType.valueOf(category);
			this.category = typeCategory;
		}
		else {
    		throw new FiledErrorException("Error: Setting Category - FAILED (category dosen't exist)");
    	}
		
	}

	public long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(long ownerID) throws FiledErrorException {
		
		if(ownerID < 0) { // if id is UNDER '0' then throw Exception.
			throw new FiledErrorException("Error: Setting Owner ID - FAILED (Field Empty or Under Zero!)");

		}
		else {
			this.ownerID = ownerID;
		}

	}

	// ToString
	@Override
	public String toString() {
		return "\n" + "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", Category= " + category + ", amount=" + amount + ", message=" + message + ", price=" + price + "$ " + ", image=" + image + " Owner ID=" + ownerID + "]";
	}
}
