package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.WebInitializer;
import com.example.model.Product;
import com.example.model.Store;
import com.example.model.DAO.StoreDAO;

@Controller
@RequestMapping ("/store")
public class StoreController {
	@Autowired
	StoreDAO storeDAO;
	
	@RequestMapping(value = "/showInTheMap", method = RequestMethod.GET)
	public String showInTheMap(@RequestParam("value") long orderId,
			Model model){
		try {
			Store store = storeDAO.seachStoreById(orderId);
			String [] gps = store.getGps().split(",");
			model.addAttribute("longitude", gps[0]);
			model.addAttribute("latitude", gps[1]);
			model.addAttribute("address", "ТЕХНОМАРКЕТ " + store.getAddress());
		} catch (SQLException e) {
			System.out.println("SQL Exception into /store/showInMap");
			e.printStackTrace();
			return "errorPage";
		}
		return "storeMaping";
	}
	
	@RequestMapping(value="/store_pic", method = RequestMethod.GET)
	public void productPic(HttpServletResponse resp, @RequestParam(value = "value") long storeId){
		Store s;
		try {
			s = storeDAO.seachStoreById(storeId);
		String url = s.getStoreImageUrl();
		File pic = new File(WebInitializer.STORE_LOCATION + url);
		System.out.println(WebInitializer.STORE_LOCATION + url + "===================");
		Files.copy(pic.toPath(), resp.getOutputStream());
		resp.getOutputStream().flush();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
