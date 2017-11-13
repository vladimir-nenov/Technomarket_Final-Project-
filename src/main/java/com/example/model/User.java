package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.format.datetime.joda.LocalDateParser;

import com.example.model.exceptions.InvalidUserDataException;
import com.example.model.util.RegexValidator;



public class User {
	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private LocalDate birthDate;
	private boolean isAdmin;
	private boolean isAbonat;
	private boolean isBanned;
	private LinkedHashSet<Product> favourites;
	private LinkedHashMap<Product, Integer> basket;
	private LinkedHashSet<Order> orders;

	public User() {
		this.basket = new LinkedHashMap<>();
	}

	public User(String firstName, String lastName, String email, String password, String gender, LocalDate birthDate, boolean isAbonat, boolean isAdmin, boolean isBanned) throws InvalidUserDataException {
		if (!correctDataForNameOfUser(firstName, lastName)) {
			throw new InvalidUserDataException();
		} else {
			this.firstName = firstName;
			this.lastName = lastName;
		}
		if (!RegexValidator.validateEmail(email)) {
			throw new InvalidUserDataException();
		
		} else {
			this.email = email;
		}
		if (!RegexValidator.validatePassword(password)) {
			throw new InvalidUserDataException();
		} else {
			this.password = password;
		}
		// This field is not validate !
		if (gender != null && !gender.isEmpty()) {
			this.gender = gender;
		} else {
			throw new InvalidUserDataException();
		}

		if(validateBirthDay(birthDate)){
			this.birthDate = birthDate;
		}else{
			throw new InvalidUserDataException();
		}
		
		
	
		this.isAdmin = isAdmin;
		this.isAbonat = isAbonat;
		this.isBanned = isBanned;
		this.favourites = new LinkedHashSet<>();
		this.basket = new LinkedHashMap<>();
		this.orders = new LinkedHashSet<>();
	}
	

	private boolean validateBirthDay(LocalDate birthDate) {

		if(birthDate.isAfter(LocalDate.now())){
			return false;
		}else{
			return true;
		}
	}

	private boolean correctDataForNameOfUser(String firstName, String lastName) {
		if (firstName == null || firstName.isEmpty() || firstName.length() < 2) {
			return false;
		}
		if (lastName == null || lastName.isEmpty() || lastName.length() < 2) {
			return false;
		}
		return true;
	}
	
	public BigDecimal getBasketPrice(){
		BigDecimal sum = new BigDecimal(0);
		if(basket.isEmpty()){
			return sum;
		}
		for (Iterator<Entry<Product, Integer>> iterator = basket.entrySet().iterator(); iterator.hasNext();) {
			Entry<Product, Integer> product = iterator.next();
			sum.add(product.getKey().getPrice().multiply(new BigDecimal(product.getValue())));
		}
		
		return sum;
	}
	public Map<Product, Integer> getBasket() {
		return Collections.unmodifiableMap(this.basket);
	}

	public void setId(long id) {
		this.userId = id;

	}
	public Set<Product> getFavourites() {
		return Collections.unmodifiableSet(this.favourites);
	}
	public void setFavourites(LinkedHashSet<Product> favourites) {
		this.favourites = favourites;
	}
	
	public void setOrders(LinkedHashSet<Order> orders) {
		this.orders = orders;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getGender() {
		return gender;
	}

	public boolean getIsAbonat() {
		return this.isAbonat;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setAbonat(boolean isAbonat) {
		this.isAbonat = isAbonat;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public long getUserId() {
		return userId;
	}
	public Set<Order> getOrders() {
		return Collections.unmodifiableSet(this.orders);
	}

}
