package com.example.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Store;
import com.example.model.User;
import com.example.model.DAO.OrderDAO;
import com.example.model.DAO.ProductDAO;
import com.example.model.DAO.StoreDAO;
import com.example.model.DAO.UserDAO;
import com.example.model.exceptions.InvalidCategoryDataException;
import com.example.model.exceptions.InvalidCharacteristicsDataException;
import com.example.model.exceptions.InvalidStoreDataException;
import com.example.model.util.Postman;
import com.example.model.util.RegexValidator;
import com.example.model.util.SystemEmailTexts;

@Controller
@RequestMapping(value = "/info")
public class InfoController {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private StoreDAO storeDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private UserDAO userDAO;
	

	// product info gets:

	@RequestMapping(value = "/infoForProduct", method = RequestMethod.GET)
	public String infoForProduct(@RequestParam(value = "value") String productId,
			HttpSession session,
			Model model) {
		try {
			// gets the product itself:
			Product product = productDAO.searchProductById(productId);
			model.addAttribute("product", product);

			// finds if product is in stock in any store:
			boolean isProductInStock = storeDAO.isProductInStock(productId);
			model.addAttribute("isProductInStock", isProductInStock);
			
			//gets user, check if he/she is logged and if yes checks if products is in his/her favourites:
			User user = (User) session.getAttribute("user");
			if(user != null){
				boolean isProductInFavourite = userDAO.isPrductInFavourite(String.valueOf(user.getUserId()), productId);
				model.addAttribute("isProductInFavourite", isProductInFavourite);
			}

			//if products is in stock anywhere or user is admin method is searching for stock status per store:
			if (isProductInStock || (user != null && user.getIsAdmin())) {
				// gets the product stock status per store:
				LinkedHashMap<Store, String> statusPerStore = new LinkedHashMap<>();
				HashSet<Store> stores = storeDAO.getAllStores();
				for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
					Store store = iterator.next();
					StoreDAO.Status status = storeDAO.checkQuantity(store, product);
					String statusImgLink = null;
					if (status == StoreDAO.Status.NO_STATUS) {
						statusImgLink = null;
					} else if (status == StoreDAO.Status.RED_STATUS) {
						statusImgLink = "/img/store_statuses/red.png";
					} else if (status == StoreDAO.Status.YELLOW_STATUS) {
						statusImgLink = "/img/store_statuses/yellow.png";
					} else if (status == StoreDAO.Status.GREEN_STATUS) {
						statusImgLink = "/img/store_statuses/green.png";
					}
					statusPerStore.put(store, statusImgLink);
				}
				model.addAttribute("statusPerStore", statusPerStore);
			}
			
		} catch (SQLException | InvalidStoreDataException e) {
			e.printStackTrace();
			return "errorPage";
		}

		return "productInfo";
	}
	
	@RequestMapping(value = "/contactsEmail", method = RequestMethod.POST)
	public String contactsMail(@RequestParam(value = "names") String names,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "phone") String phone,
			@RequestParam(value = "message") String message,
			Model model) {
		
		boolean validateEmail = RegexValidator.validateEmail(email.trim());
		boolean validatePhone = RegexValidator.validateMobilePhoneNumber(phone.trim());
		
		if((email.trim() == null || email.isEmpty())){
			model.addAttribute("emptyEmail", true);
			return "contacts";
		}
		
		if((message.trim() == null || message.isEmpty())){
			model.addAttribute("emptyMessage", true);
			return "contacts";
		}
		
		if(!validateEmail){
			model.addAttribute("invalidEmail", true);
			return "contacts";
		}
		
		if((phone.trim() == null || phone.trim().isEmpty()) || !validatePhone){
			model.addAttribute("invalidPhone", true);
			return "contacts";
		}
		
		Postman.sendSimpleEmail(Postman.EMAIL, SystemEmailTexts.SUBJECT_USER_EMAIL + " " + names, message.trim());
		model.addAttribute("emailSend", true);
		
		return "contacts";
	}

	// site conditions info gets:

	@RequestMapping(value = "/infoContacts", method = RequestMethod.GET)
	public String infoContacts() {
		return "contacts";
	}

	@RequestMapping(value = "/infoForShoppingCon", method = RequestMethod.GET)
	public String infoShopping() {
		return "shoppingConditions";
	}

	@RequestMapping(value = "/infoForDelivery", method = RequestMethod.GET)
	public String infoDelivery() {
		return "deliveryInfo";
	}

	@RequestMapping(value = "/infoForOnlinePay", method = RequestMethod.GET)
	public String infoOnlinePay() {
		return "onlinePayInfo";
	}

	@RequestMapping(value = "/infoForTBICredit", method = RequestMethod.GET)
	public String infoTBICredit() {
		return "tbiCreditInfo";
	}

	@RequestMapping(value = "/infoForUniCredit", method = RequestMethod.GET)
	public String infoUniCredit() {
		return "uniCreditInfo";
	}

	// user info gets:

	@RequestMapping(value = "/infoUserProfile", method = RequestMethod.GET)
	public String infoUserProfile() {
		return "user_profile";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String log(HttpSession session) {
		session.invalidate();
		return "/index";
	}

	@RequestMapping(value = "/infoUserOrders", method = RequestMethod.GET)
	public String infoUserOrders(HttpSession sesion,
			Model model) {

		User user = (User) sesion.getAttribute("user");
		try {
			LinkedHashSet<Order> orders = orderDAO.getOrdersForUser(user.getUserId());
			System.out.println(orders);
			model.addAttribute("orders", orders);
		} catch (SQLException e) {
			System.out.println("SQL Exception into /info/infoUserOrders ");
			e.printStackTrace();
		} catch (InvalidCategoryDataException e) {
			System.out.println("Ivalid data into /info/infoUserOrders");
			e.printStackTrace();
			return "errorPage";
		}
		return "user_orders";
	}

	@RequestMapping(value = "/infoFoCurrentOrder", method = RequestMethod.POST)
	public String infoForCurrentOrder(@RequestParam(value = "value") String orderId, Model model) {
		try {
			Order order = orderDAO.searchOrderById(orderId);
			LinkedHashMap<Long, Integer> product = orderDAO.getProductFromOrder(orderId);
			
			LinkedHashMap<Product, Integer> readyProduct = new LinkedHashMap<>();
			for(Iterator<Entry<Long, Integer>> it = product.entrySet().iterator(); it.hasNext();){
				Entry<Long, Integer> entry = it.next();
				try {
					Product pr = productDAO.getProduct(entry.getKey());
					readyProduct.put(pr, entry.getValue());
				} catch (InvalidCategoryDataException e) {
					e.printStackTrace();
					return "errorPage";
				}
			}
			
			model.addAttribute("order", order);
			model.addAttribute("products", readyProduct);
		} catch (SQLException e) {
			System.out.println("SQL EXception is InfoControlle/inForCurrenrOrder");
			e.printStackTrace();
			return "errorPage";
		}

		return "pageForCurrentOrders";
	}

	// admin info gets:

	@RequestMapping(value = "/infoAdminPanel", method = RequestMethod.GET)
	public String infoAdminPanel() {
		return "admin_panel";
	}

	@RequestMapping(value = "/infoAdminOrders", method = RequestMethod.GET)

	public String infoAdminOrders(Model model, HttpSession session){
		try {
			LinkedHashSet<Order> orders = orderDAO.getOrderWhereIsNotConfirmedAndIsNotPaid();
			model.addAttribute("orders", orders);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception in info/infoAdminPanel");
			return "errorPage";
		}

		return "admin_orders";
	}

	@RequestMapping(value = "/infoAdminBan", method = RequestMethod.GET)
	public String infoAdminBan() {
		return "admin_ban";
	}

	@RequestMapping(value = "/infoAdminCreateAdmin", method = RequestMethod.GET)
	public String infoAdminCreateAdmin() {
		return "admin_create_admin";
	}

	@RequestMapping(value = "/infoAdminInsertProduct", method = RequestMethod.GET)
	public String infoAdminInsertProduct() {
		return "admin_insert_product";
	}

}
