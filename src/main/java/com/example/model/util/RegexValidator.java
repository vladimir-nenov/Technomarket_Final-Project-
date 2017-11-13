package com.example.model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexValidator {
	
	private static final String EMAIL_REGEX = "^(.+)@(.+)$"; //username at domain
	private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$"; //Minimum eight characters, at least one letter and one number
	private static final String MOBILE_PHONE_REGEX = "[+]?[0-9]{1,10}"; //starts with 08, 0-9 number range, 10 numbers
	private static final String IMAGE_FILE_REGEX = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)"; //possible image formats
	
	public static boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}

	public static boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_REGEX);
		Matcher matcher = pattern.matcher(password);
		
		return matcher.matches();
	}
	
	public static boolean validateMobilePhoneNumber(String phoneNumber) {
		Pattern pattern = Pattern.compile(MOBILE_PHONE_REGEX);
		Matcher matcher = pattern.matcher(phoneNumber);
		
		return matcher.matches();
	}
	
	public static boolean validateImageFile(String fileName) {
		Pattern pattern = Pattern.compile(IMAGE_FILE_REGEX);
		Matcher matcher = pattern.matcher(fileName);
		
		return matcher.matches();
	}

}

