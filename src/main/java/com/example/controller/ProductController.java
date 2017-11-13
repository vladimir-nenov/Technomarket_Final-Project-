package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.WebInitializer;
import com.example.model.Category;
import com.example.model.Credit;
import com.example.model.Product;
import com.example.model.Store;
import com.example.model.User;
import com.example.model.DAO.AdminDAO;
import com.example.model.DAO.CategoryDAO;
import com.example.model.DAO.ProductDAO;
import com.example.model.DAO.StoreDAO;
import com.example.model.DAO.TradeMarkDAO;
import com.example.model.DAO.UserDAO;
import com.example.model.DBM.DBManager;
import com.example.model.exceptions.InvalidCategoryDataException;
import com.example.model.exceptions.InvalidCharacteristicsDataException;
import com.example.model.exceptions.InvalidProductDataException;
import com.example.model.exceptions.InvalidStoreDataException;
import com.example.model.exceptions.NotAnAdminException;
import com.example.model.util.Postman;

@Component
@RequestMapping(value="/product")
public class ProductController {

	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TradeMarkDAO tradeMarkDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private StoreDAO storeDAO;
	
	@RequestMapping(value = "/insert_product", method = RequestMethod.GET)
	public String prepareRegistarion() {
		return "admin_insert_product";
	}
	@RequestMapping(value = "/productByPrice", method = RequestMethod.GET)
	public String searchProductByPrice(@RequestParam("price1") double price1  ,@RequestParam("price2") double price2,@RequestParam("categoryName") String categoryName, Model model){
		
		try {
			if(price1 > price2){
				model.addAttribute("errorPrice", "invalidData");
			}else{
				Set<Product> products = null;
				LinkedHashSet<Product> pro = new LinkedHashSet<>();
				try {
					products = productDAO.searchProductByCategoryName(categoryName.trim());
					for(Product prod : products){
						if(prod.getPrice().doubleValue() >= price1 && prod.getPrice().doubleValue() <= price2){
							pro.add(prod);
						}
					}
				} catch (InvalidCategoryDataException e) {
					e.printStackTrace();
					return "errorPage";
				}
				
			 
			  model.addAttribute("filtredProducts", pro);
			  model.addAttribute("categoryName", categoryName);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception in /product/productByPrice");
			e.printStackTrace();
			return "errorPage";
		}
		return "filtred_products";
		
	}
	
	@RequestMapping(value = "/insert_product", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String insertNewProduct(Model model, HttpSession session,
			@RequestParam("productName") String productName,
			@RequestParam("tradeMark") String tradeMark,
			@RequestParam("categoryName") String categoryName,
			@RequestParam("price") BigDecimal price,
			@RequestParam("warranty") int warranty,
			@RequestParam("promoPercent") int promoPercent,
			@RequestParam("description") String description,
			@RequestParam("image") MultipartFile image) {
		String imageName = null;
		try {
			if(!tradeMarkDAO.tradeMarkExist(tradeMark.trim())){
				tradeMarkDAO.insertTradeMark(tradeMark.trim());
			}
			if(!categoryDAO.categoryExist(categoryName.trim())){
				categoryDAO.insertCategory(categoryName.trim());
			}
			if(description == null && description.isEmpty() && description.length() < 30){
				model.addAttribute("invalidDescription", true);
				return "admin_insert_product";
			}
		MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
		MimeType type = allTypes.forName(image.getContentType());
		String extention = type.getExtension(); // .extention (dot included)
		String productNumber = productDAO.generateProductNumber();
		imageName = "product" + "-" + productNumber.trim() + extention;
		File imageFile = new File(imageName);
		image.transferTo(imageFile);
		Category category = new Category(categoryName.trim());
		Product newProduct = new Product(productName.trim(), tradeMark.trim(), price, null, category, warranty,
				promoPercent, LocalDate.now(), imageName);
		newProduct.setDescription(description);
		adminDAO.insertNewProduct(newProduct, (User) session.getAttribute("user"));
		model.addAttribute("added", "New product added");
		model.addAttribute("productId", newProduct.getProductId());
		} catch (IllegalStateException | IOException | MimeTypeException | InvalidCategoryDataException | NotAnAdminException | InvalidProductDataException e) {
			e.printStackTrace();
			model.addAttribute("invalidProductData", true);
			return "admin_insert_product";
		} catch (SQLException e) {
			e.printStackTrace();
			return "errorPage";
		} 
		return "admin_insert_product";
	}

	@RequestMapping(value="/product_pic", method = RequestMethod.GET)
	public void productPic(HttpServletResponse resp, @RequestParam(value = "value") String productId){
		Product p;
		try {
			p = productDAO.searchProductById(productId.trim());
		String url = p.getImageUrl();
		File pic = new File(WebInitializer.LOCATION + url);
		Files.copy(pic.toPath(), resp.getOutputStream());
		resp.getOutputStream().flush();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/remove", method = RequestMethod.GET)
	public String removeProduct(HttpSession session,
			@RequestParam(value = "value") long productId){
		try {
			adminDAO.removeProduct(productId, (User) session.getAttribute("user"));
		} catch (NotAnAdminException | SQLException e) {
			e.printStackTrace();
			return "errorPage";
		}
		
		return "product_removed";
	}
	
	@RequestMapping(value="/setPromo", method = RequestMethod.POST)
	public String setPromo(Model model,
			HttpSession session,
			@RequestParam(value = "productId") int productId,
			@RequestParam(value = "promoPercent") int percentPromo){
		
		if(percentPromo < 0 || percentPromo > 99){
			model.addAttribute("invalidPercent", true);
			return "redirect:/info/infoForProduct?value=" + Integer.toString(productId);
		}
		
		Product promoProduct = null;
		HashSet<String> emails = null;
		try {
			
			User user = (User) session.getAttribute("user");
			
			//sets the product on promo after checking is user is really admin:
			adminDAO.setPromoPercent(user, productId, percentPromo);
			
			//gets the product for email purposes:
			promoProduct = productDAO.getProduct((int) productId);
			
			//gets all the needed emails: 
			emails = productDAO.getEmailsPerFavourites(user.getUserId());
			
			//sends mail data to Postman class to construct and send email to recipients:

			if(percentPromo > 0){
				Postman.promoProductEmail(promoProduct.getName(), productId, percentPromo, promoProduct.getPrice(), emails);
			}
		} catch (NotAnAdminException | SQLException | InvalidCategoryDataException e) {
			if(promoProduct.getName() == null){
			}
			Postman.promoProductEmail(promoProduct.getName(), productId, percentPromo, promoProduct.getPrice(), emails);
			e.printStackTrace();
			return "errorPage";
		}
		
		model.addAttribute("promoSet", true);
		return "redirect:/info/infoForProduct?value=" + Integer.toString(productId);
	}

	@RequestMapping(value = "/productsByCategory" , method = RequestMethod.GET)
	public String searchProduct(@RequestParam("categoryName") String categoryName, Model model){
		try {
			Set<Product> products = productDAO.searchProductByCategoryName(categoryName.trim());
			model.addAttribute("filtredProducts", products);
			model.addAttribute("categoryName", categoryName.trim());
		} catch (SQLException | InvalidCategoryDataException e) {
			e.printStackTrace();
			System.out.println("Error for SQL");
			return "errorPage";
		}
		return "filtred_products";
	}
	
	@RequestMapping(value = "/compareProduct" , method = RequestMethod.GET)
	public String compare(@RequestParam("compare") String comp,
			@RequestParam("categoryName") String categoryName,
			Model model){
		
		try {
			String compare = new String(comp.trim());
			Set<Product> products = productDAO.searchProductByCategoryName(categoryName.trim());
			TreeSet<Product> sortProduct = null;
			if(compare.equals("price")){
			  sortProduct = new TreeSet<Product>((o1 , o2) -> {
				  if(o1.getPrice().compareTo(o2.getPrice())== 0){
					  return 1;
				  }
				  return o1.getPrice().compareTo(o2.getPrice());
			  });
			}
			if(compare.equals("mark")){
			  sortProduct = new TreeSet<Product>((o1, o2) ->{
				  if(o1.getTradeMark().compareTo(o2.getTradeMark()) == 0){
					  return 1;
				  }
				  return o1.getTradeMark().compareTo(o2.getTradeMark());
			  });
			}
			if(compare.equals("type")){
				sortProduct = new TreeSet<Product>((o1, o2) ->{
					if(o1.getPercentPromo() == o2.getPercentPromo()){
						return 1;
					}else{
						if(o1.getPercentPromo() > o2.getPercentPromo()){
							return -1;
						}
					}
					return 1;		
				});
			}
			sortProduct.addAll(products);
			model.addAttribute("filtredProducts", sortProduct);
			model.addAttribute("categoryName", categoryName.trim());
		} catch (SQLException | InvalidCategoryDataException e) {
			e.printStackTrace();
			System.out.println("Error for SQL");
			return "errorPage";
		}
		return "filtred_products";
	}

	
	
	
	
	



	
	
	
	
	
	
	
	
	
	
}