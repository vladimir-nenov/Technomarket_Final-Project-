package com.example.model.util;
//
//import java.util.HashSet;
//import java.util.Properties;
//
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.mail.BodyPart;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//import com.example.model.User;
//
//import javafx.scene.shape.Line;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


	public final class Postman{
	public static final String EMAIL = "technomarkettalents@gmail.com";
	private static final String PASS = "ittechnomarket1234";
	
	//email gateways to costruct email subject and text:
	
	//1 - forgotten password -> email send to specific user by the system on request by the same user:
	public static void forgottenPassEmail(String recipientЕmail, String newPassword){
		final String subject = SystemEmailTexts.SUBJECT_FORGOTTEN;
		final String text = SystemEmailTexts.forgottenPassEmail(newPassword);
		sendSimpleEmail(recipientЕmail, subject, text);
	}
	
	//2 - new promo products -> email send to all subscribers by they system on request by the admin:
	public static void promoProductEmail(String productName, long userId, int percentPromo, BigDecimal price, HashSet<String> emails){
		final String subject = SystemEmailTexts.SUBJECT_FAVOURITE_PROMO;
		final String text = SystemEmailTexts.favouriteOnPromo(productName, userId, percentPromo, price);
		for (Iterator<String> iterator = emails.iterator(); iterator.hasNext();) {
			String recipientЕmail = iterator.next();
			sendSimpleEmail(recipientЕmail, subject, text);
		}
	}
	
	//3 - technomarket news -> email send to system email by user:
//	public static void promoProductEmail(String productName, long userId, int percentPromo, BigDecimal price, HashSet<String> emails){
//		final String subject = SystemEmailTexts.SUBJECT_FAVOURITE_PROMO;
//		final String text = SystemEmailTexts.favouriteOnPromo(productName, userId, percentPromo, price);
//		for (Iterator<String> iterator = emails.iterator(); iterator.hasNext();) {
//			String recipientЕmail = iterator.next();
//			sendSimpleEmail(recipientЕmail, subject, text);
//		}
//	}
	
	
	//common method for sending e-mails:
 	public static void sendSimpleEmail(String receiverEmail, String subjectText, String msgText) {
 		Properties props = new Properties();
 		props.put("mail.smtp.auth", "true");
 		props.put("mail.smtp.starttls.enable", "true");
 		props.put("mail.smtp.host", "smtp.gmail.com");
 		props.put("mail.smtp.port", "587");
 
 		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
 			protected PasswordAuthentication getPasswordAuthentication() {
 				return new PasswordAuthentication(EMAIL, PASS);
 			}
 		});
 		
 		try {
 			Message message = new MimeMessage(session);
 			message.setFrom(new InternetAddress("technomarkettalents@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
 			message.setSubject(subjectText);
 			message.setText(msgText);
 			Transport.send(message);
 
 			System.out.println("Email sent.");
 
 		} catch (MessagingException e) {
 			throw new RuntimeException(e);
 		}
 	}
 }