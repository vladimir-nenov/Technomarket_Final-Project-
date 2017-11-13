package com.example.model;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.model.exceptions.InvalidStoreDataException;
import com.example.model.util.RegexValidator;



public class Store {
	private String city;
	private String address;
	private long storeId;
	private HashMap<Product, Integer> product;
	private String phoneNumber;
	private String email;
	private String workingTime;
	private String gps;
	private String storeImageUrl;
	
	public Store(){
		
	}
	
	public Store(String phoneNumber, String email,
			String workingTime, String gps, String city, String address, String storeImageUrl) throws InvalidStoreDataException {
		
		if(city != null && !city.isEmpty()){
			this.city  = city;
		}else{
			throw new InvalidStoreDataException();
		}
		if(address != null && !address.isEmpty()){
			this.address = address;
		}else{
			throw new InvalidStoreDataException();
		}
		//////////////////////////////
		if(RegexValidator.validateMobilePhoneNumber(phoneNumber)){
			this.phoneNumber = phoneNumber;
		}else{
			throw new InvalidStoreDataException();
		}
		/////////////////////////////
		if(RegexValidator.validateEmail(email)){
			this.email = email;
		}else{
			throw new InvalidStoreDataException();
		}
		//////////////////////////
		if(workingTime != null && !workingTime.isEmpty()){
			this.workingTime = workingTime;
		}else{
			throw new InvalidStoreDataException();
		}
		if(gps != null && !gps.isEmpty()){
			this.gps = gps;
		}else{
			throw new InvalidStoreDataException();
		}
		if(storeImageUrl != null && !storeImageUrl.isEmpty()){
			this.storeImageUrl = storeImageUrl;
		}else{
			throw new InvalidStoreDataException();
		}
		this.product = new HashMap<>();
	}
	
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
	public long getStoreId() {
		return storeId;
	}
	public String getAddress() {
		return address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public String getWorkingTime() {
		return workingTime;
	}
	public String getGps() {
		return gps;
	}
	public String getStoreImageUrl() {
		return storeImageUrl;
	}
	public void setStoreImageUrl(String storeImageUrl) {
		this.storeImageUrl = storeImageUrl;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}
	
	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	//hashCode and equals overrides for collections:
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gps == null) ? 0 : gps.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + (int) (storeId ^ (storeId >>> 32));
		result = prime * result + ((storeImageUrl == null) ? 0 : storeImageUrl.hashCode());
		result = prime * result + ((workingTime == null) ? 0 : workingTime.hashCode());
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
		Store other = (Store) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gps == null) {
			if (other.gps != null)
				return false;
		} else if (!gps.equals(other.gps))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (storeId != other.storeId)
			return false;
		if (storeImageUrl == null) {
			if (other.storeImageUrl != null)
				return false;
		} else if (!storeImageUrl.equals(other.storeImageUrl))
			return false;
		if (workingTime == null) {
			if (other.workingTime != null)
				return false;
		} else if (!workingTime.equals(other.workingTime))
			return false;
		return true;
	}
	
	
	
	
	
	
}
