package com.example.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.model.DAO.AdminDAO;
import com.example.model.exceptions.NotAnAdminException;

@RestController
@RequestMapping("/store")
public class StoreService {
	
	@Autowired
	private AdminDAO adminDAO;

	@RequestMapping(value="/quantity", method=RequestMethod.POST)
	@ResponseBody
	public void changeQuantityInStore(HttpServletResponse resp, HttpSession session,
			@RequestParam(value="product") int productId,
			@RequestParam(value="store") int storeId,
			@RequestParam(value="amount") int amount){
			resp.setStatus(200);
		try {
			adminDAO.changeQuantityInStore(storeId, productId, amount, (User) session.getAttribute("user"));
		} catch (SQLException | NotAnAdminException e) {
			resp.setStatus(401);
			e.printStackTrace();
		}
	}
}
