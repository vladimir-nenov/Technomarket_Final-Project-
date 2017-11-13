package com.example.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.example.model.exceptions.InvalidOrderDataException;
import com.example.model.util.RegexValidator;



public class Order {
	
	private String userNames;
	private long orderId;
	private HashMap<Product, Integer> products;
	private BigDecimal price;
	private LocalDate time;
	private String address;
	private String userPhoneNumber;
	private String zip;
	private boolean isConfirmed;
	private String notes;
	private String payment;
	private boolean isPaid;
	private String shipingType;
	
	public Order(HashMap<Product, Integer> products, String address, String userPhoneNumber, String zip, String notes, String payment, LocalDate time, boolean isConfirmed, boolean isPaid) throws InvalidOrderDataException {
		this.products = products;
		//calculating the sum of all products and their numbers:
		this.price = calculatePriceOfOrder();
		
		//validating fields:
		if(isAddressValid(address)){
			this.address = address;
		}else{
			throw new InvalidOrderDataException();
		}
		if(RegexValidator.validateMobilePhoneNumber(userPhoneNumber)){
			this.userPhoneNumber = userPhoneNumber;
		}else{
			throw new InvalidOrderDataException();
		}
		if(isZipValid(zip)){
			this.zip = zip;
		}else{
			throw new InvalidOrderDataException();
		}
		if(payment != null){
			this.payment = payment;
		}else{
			throw new InvalidOrderDataException();
		}
		if(notes != null){
			this.notes = notes;
		}else{
			throw new InvalidOrderDataException();
		}
		
		
		//setting the date and time of creating a new order to .now():
		this.time = time;
		//setting boolean of status to false at the start:
		this.isConfirmed = isConfirmed;
		this.isPaid = isPaid;
	}

	public Order(){
	
	}
	
	
	// private system methods:
	

	private BigDecimal calculatePriceOfOrder() {
		BigDecimal totalSum = new BigDecimal(0);
		for (Iterator<Entry<Product, Integer>> iterator = products.entrySet().iterator(); iterator.hasNext();) {
			Entry<Product, Integer> orderedProduct = iterator.next();
			BigDecimal price = orderedProduct.getKey().getPrice();
			double promoDiff = ((double)orderedProduct.getKey().getPercentPromo())/100;
			if(orderedProduct.getKey().getPercentPromo() > 0){
				price.subtract(price.multiply(new BigDecimal(promoDiff)));
			}
			totalSum.add(price.multiply(new BigDecimal(orderedProduct.getValue())));
		}
		//Rounding sum to always have two decimal places:
		BigDecimal roundedResult = totalSum.setScale(2, RoundingMode.CEILING);
		System.out.println(roundedResult);
		return roundedResult;
	}

	private boolean isZipValid(String zip) {
		if(zip.length() != 4 || zip.charAt(0) == '0'){
			return false;
		}else{
			return true;
		}
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	

	private boolean isAddressValid(String address) {
		return address != null && !address.isEmpty();
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
    public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
    public String getUserNames() {
		return userNames;
	}

	public boolean getIsConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public boolean getIsPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public long getOrderId() {
		return orderId;
	}

	public LocalDate getTime() {
		return time;
	}
	public String getAddress() {
		return address;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public String getNotes() {
		return notes;
	}

   public void setShipingType(String shipingType) {
	this.shipingType = shipingType;
   }
   public String getShipingType() {
	return shipingType;
   }

	public String getPayment() {
		return payment;
	}






	public Map<Product, Integer> getProducts() {
      return Collections.unmodifiableMap(this.products);
	}






	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}




    public String getZip() {
		return zip;
	}

	public void setPrice(String price) {
		this.price = new BigDecimal(price);
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	

	public void setPayment(String payment) {
		this.payment = payment;
	}

	
	//hashCode and equals overrided for collections:
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (isConfirmed ? 1231 : 1237);
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((userPhoneNumber == null) ? 0 : userPhoneNumber.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (isConfirmed != other.isConfirmed)
			return false;
		if (isPaid != other.isPaid)
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (orderId != other.orderId)
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (userPhoneNumber == null) {
			if (other.userPhoneNumber != null)
				return false;
		} else if (!userPhoneNumber.equals(other.userPhoneNumber))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	
	
	
	
	

	
	
}
