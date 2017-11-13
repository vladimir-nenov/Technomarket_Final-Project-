package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.model.DAO.ProductDAO;

@RestController
@RequestMapping("/basket")
public class BasketService {
	
	@Autowired
	private ProductDAO productDAO;

	@RequestMapping(value="/quantity", method=RequestMethod.POST)
	@ResponseBody
	public void changeQuantity(HttpServletResponse resp, HttpSession session,
			@RequestParam(value="product") String productId,
			@RequestParam(value="quantity") int quantity){
			resp.setStatus(200);
		try {
			HashMap<Product, Integer> basket = (HashMap<Product, Integer>) session.getAttribute("basket");
			Product product = productDAO.searchProductById(productId.trim());
			basket.put(product, (quantity - 1));
//			resp.sendRedirect("basket");
		} catch (SQLException e) {
			resp.setStatus(401);
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	@ResponseBody
	public void removeProduct(HttpServletResponse resp, HttpSession session,
			@RequestParam(value="product") long productId){
			resp.setStatus(200);
		HashMap<Product, Integer> product = (HashMap<Product, Integer>) session.getAttribute("basket");
		for(Iterator<Entry<Product, Integer>> it = product.entrySet().iterator(); it.hasNext();){
			Entry<Product, Integer> entry = it.next();
			if(entry.getKey().getProductId() == productId){
				it.remove();
			}
		}
	}
	
//	/buyController/removeProduct
//	basket
	
	
//	@RequestMapping(value="/decrement", method=RequestMethod.POST)
//	@ResponseBody
//	public void decrementAll(HttpServletResponse resp, HttpSession session){
//			resp.setStatus(200);
//		HashMap<Product, Integer> basket = (HashMap<Product, Integer>) session.getAttribute("basket");
//		for (Iterator<Entry<Product, Integer>> iterator = basket.entrySet().iterator(); iterator.hasNext();) {
//			Entry<Product,Integer> entry = iterator.next();
//			Product p = entry.getKey();
//			Integer i = entry.getValue() - 1;
//			basket.put(p, i);
//		}
//	}
}
