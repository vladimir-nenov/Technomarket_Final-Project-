package com.example.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.User;
import com.example.model.DAO.UserDAO;
import com.example.model.exceptions.InvalidCategoryDataException;
import com.example.model.exceptions.InvalidCharacteristicsDataException;
import com.example.model.util.PasswordGenerator;
import com.example.model.util.Postman;
import com.example.model.util.RegexValidator;

@Controller
public class LoginController {
	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(HttpSession ses, Model model) {
		ses.setAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpSession session, Model model) {
		
			if(username == null || username.isEmpty() || !RegexValidator.validateEmail(username) || username.length() > 35){
				model.addAttribute("invalidUser", "Invalid username or password");
				return "login";
			}
			if(password == null || password.isEmpty() || !RegexValidator.validatePassword(password)){
				model.addAttribute("invalidUser", "Invalid username or password");
				return "login";
			}
		
		try {
			boolean exiting = userDAO.existingUser(username.trim(), password.trim());
			if (exiting) {
				
				User user = null;
				try {
					user = userDAO.getUser(username.trim());

				} catch (InvalidCategoryDataException e) {
					System.out.println("Invalid data exceptions");
					return "login";
				}
				System.out.println(user+"!!!!!!!!!!!!!!!!!!!!!!!$$$$$$$$$$$$$$$$$$$$$$$$$$!!!!!!!!!!!!!!!!!");
				user.setAdmin(true);
				session.setAttribute("user", user);
				session.setAttribute("log", true);

				// if user was in basket before log in, method sends him back in
				// his basket:
				if (session.getAttribute("inBasket") != null) {
					boolean inBasket = (boolean) session.getAttribute("inBasket");
					if (inBasket == true) {
						session.removeAttribute("inBasket");
						return "basket";
					} else {

						// if he wasn't, in index:
						return "index";
					}
				}else{
					return "index";
				}
			}else{
				model.addAttribute("invalidUser", "Invalid username or password");
				return "login";
			}
		} catch (SQLException e) {
			  System.out.println("SQL Exceptions");
			  e.printStackTrace();
			   
	    	 }  
		
		return "login";
	}

	// forgotten password module:

	@RequestMapping(value = "/forgotten", method = RequestMethod.GET)
	public String forgotten() {
		return "forgotten";
	}

	@RequestMapping(value = "/forgotten", method = RequestMethod.POST)
	public String forgottenPassword(Model model,
			@RequestParam("email") String email) {
		try {
			
			if(!RegexValidator.validateEmail(email)){
				model.addAttribute("emailError", "Email not valid");
				return "forgotten";
			}
			
			// checks for same email
			boolean exist = userDAO.checkIfUserWithSameEmailExist(email.trim());
			if (exist) {
				// Builds new password:
				PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true)
						.useLower(true).useUpper(true).build();
				String newPassword = passwordGenerator.generate(8);

				Postman.forgottenPassEmail(email.trim(), newPassword);
				// inserts new password in DB after hashing in the method of
				// UserDAO:
				if (userDAO.isertNewlyGeneratedPassword(email.trim(), newPassword)) {
					return "email_sent";
				} else {
					model.addAttribute("systemProblem", "System cant insert the new password");
					return "email_sent";
				}
			} else {
				model.addAttribute("emailError", "Email not valid");
				return "forgotten";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Ops SQL Exceptions");
			return "errorPage";
		}
	}

	// logout module:

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
}
