package com.example.controller;

import java.sql.SQLException;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Product;
import com.example.model.User;
import com.example.model.DAO.UserDAO;

@Controller
@RequestMapping(value = "/favourite")
public class UserFavoriteController {
	@Autowired
	private UserDAO userDAO;
	
	
	@RequestMapping(value = "/infoUserFavourites", method = RequestMethod.GET)
	public String infoUserFavourites(HttpSession session,
			Model model){
		User user = (User) session.getAttribute("user");
		try {
			LinkedHashSet<Product> product = userDAO.viewFavourite(user);
			model.addAttribute("product", product);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception in /info/infoUserFavourites");
			return "errorPage";
		}
		
		
		return "user_favourites";
	}
	@RequestMapping(value = "/addInFavorite", method = RequestMethod.GET)
	public String addInFavorite(@RequestParam("value") long productId,
			Model model,
			HttpSession session){
		if(session.getAttribute("user") == null){
			return "login";
		}
		User user = (User) session.getAttribute("user");
		try {
			userDAO.addInFavorite(user.getUserId(), productId);
		} catch (SQLException e) {
			System.out.println("SQL Exception in /info/addInFavorite");
			e.printStackTrace();
			return "errorPage";
		}
		return "forward:/favourite/infoUserFavourites";
	}
	@RequestMapping(value = "/removeFromFavorite", method = RequestMethod.POST)
	public String removeFromFavorite(@RequestParam(value = "value") long productId,
			Model model,
			HttpSession session){
		User user = (User) session.getAttribute("user");
		try {
     		userDAO.removeFavouriteProduct(user.getUserId(), productId);
			LinkedHashSet<Product> product = userDAO.viewFavourite(user);
			model.addAttribute("product", product);
		} catch (SQLException e) {
			System.out.println("SQL Exception in info/removeFromFavorite");
			e.printStackTrace();
			return "errorPage";
		}
		
		return "user_favourites";
	}

}
