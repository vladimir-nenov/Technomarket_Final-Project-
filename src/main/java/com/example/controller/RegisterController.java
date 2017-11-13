package com.example.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.User;
import com.example.model.DAO.UserDAO;
import com.example.model.exceptions.EmailAlreadyInUseException;
import com.example.model.exceptions.InvalidUserDataException;
import com.example.model.util.RegexValidator;

@Controller
public class RegisterController {

	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String prepareRegistarion(HttpSession s) {
		s.invalidate();
		// model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerNewUser(Model model, HttpSession session,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("password1") String password1,
			@RequestParam("gender") String gender,
			@RequestParam("bday") String bday,
			@RequestParam(value = "abonat", defaultValue = "null") String abonat,
			@RequestParam(value = "submit", defaultValue = "null") String submit) {
		
		try {
			//validation for equal password samples:
			if (!password.equals(password1)) {
				model.addAttribute("passError", "Passwords are not the same");
				return "register";
			}
			
			
			if (submit.equals("null")) {
				model.addAttribute("submitError", "Passwords are not the same");
				return "register";
			}
			if (!RegexValidator.validatePassword(password)) {
				model.addAttribute("invlidPassError", "Invalid passwod format");
				return "register";
			}
			if (!RegexValidator.validateEmail(email)) {
				model.addAttribute("invlidMail", "Invalid mail format");
				return "register";
			}
			
			User user = new User(firstName.trim(), lastName.trim(),
					email.trim(), password.trim(), gender.trim(),
					LocalDate.parse(bday.trim()),
					abonat == null ? false : true, false, false);
			try {
				userDAO.insertUser(user);
				session.setAttribute("user", user);
				session.setAttribute("log", true);
				return "index";
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("SQL Exception");
				return "errorPage";

			} catch (EmailAlreadyInUseException e) {
				model.addAttribute("emailError", "Passwords are not the same");
				return "register";
			}
		} catch (InvalidUserDataException e) {
			model.addAttribute("dataError", "Passwords are not the same");
			return "register";
		}

	}
}
