package com.example.model.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SystemEmailTexts {

	final static String SUBJECT_FORGOTTEN = "Техномаркет - Забравена парола";
	final static String SUBJECT_FAVOURITE_PROMO = "Техномаркет - Промоция на любим продукт!";
	public final static String SUBJECT_USER_EMAIL = "Потребителско съобщение от ";
	
	
	
	final static String PRODUCT_URL = "http://localhost:8080/MyProject/info/infoForProduct?value=";
	
	public static String forgottenPassEmail(String newPassword){
		StringBuffer message = new StringBuffer();
		message.append("Здравей, любезни потребителю,");
		message.append(System.lineSeparator());
		message.append("по твое искане ти изпращаме нова парола, която да замести старатата ти парола за сайта на Техномаркет.");
		message.append("Можеш да си я запишеш: ");
		message.append(newPassword);
		message.append(System.lineSeparator());
		message.append("Ако тази информация не те засяга, моля игнорирай съобщението ни.");
		message.append(System.lineSeparator());
		message.append("Приятен ден,");
		message.append(System.lineSeparator());
		message.append("екипът на Техномаркет!");
		return message.toString();
	}
	
	public static String favouriteOnPromo(String productName, long productId, int percentPromo, BigDecimal price){
		StringBuffer message = new StringBuffer();
		message.append("Здравей, любезни потребителю,");
		message.append(System.lineSeparator());
		message.append("с радост искаме да те уведомим, че един от твоите ЛЮБИМ продукци в нашите магазини е на промоция от днес!");
		message.append("Ако искаш да закупиш ");
		message.append(productName);
		message.append(" на специална цена от ");
		message.append(SystemEmailTexts.getPrice(percentPromo, price));
		message.append(" лв.");
		message.append(System.lineSeparator());
		message.append("Това са ЦЕЛИ ");
		message.append(percentPromo);
		message.append(" процента намаление!");
		message.append(System.lineSeparator());
		message.append("Възползвайте се докато е време като кликнете на линка по-долу!");
		message.append(System.lineSeparator());
		message.append(PRODUCT_URL);
		message.append(productId);
		message.append(System.lineSeparator());
		message.append("Приятен ден,");
		message.append(System.lineSeparator());
		message.append("екипът на Техномаркет!");
		return message.toString();
	}
	
	private static String getPrice(int percentPromo, BigDecimal price) {
		BigDecimal newPrice = price.subtract(price.multiply(new BigDecimal(percentPromo/100))).setScale(2, RoundingMode.CEILING);
		return newPrice.toString();
	}

	

	
}
