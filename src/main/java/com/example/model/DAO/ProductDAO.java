package com.example.model.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.WebInitializer;
import com.example.model.Category;
import com.example.model.Characteristics;
import com.example.model.Product;
import com.example.model.DBM.DBManager;
import com.example.model.Product.campareEnum;
import com.example.model.exceptions.InvalidCategoryDataException;
import com.example.model.exceptions.InvalidCharacteristicsDataException;
import com.example.model.exceptions.InvalidProductDataException;
import com.sun.org.apache.regexp.internal.REUtil;


@Component
public class ProductDAO {
	@Autowired
	private DBManager DBManager;
	@Autowired
	private CharacterisicsDAO characterisicsDAO;
	@Autowired
	CategoryDAO categoryDAO;
	private Connection connection;
    
	
	
//getting product and its fields:
	
	public Product getProduct(long productID)
			throws SQLException, InvalidCategoryDataException {
		String query = "SELECT * FROM technomarket.product WHERE product_id = ?;";
		this.connection = DBManager.getConnections();
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setLong(1, productID);
			ResultSet result = statement.executeQuery();
			Product pro = new Product();
			result.next();
			pro.setName(result.getString("product_name"));
			pro.setPrice(result.getString("price"));
			pro.setWorranty(result.getInt("warranty"));
			pro.setPercentPromo(result.getInt("percent_promo"));
			pro.setDateAdded(LocalDate.parse(result.getString("date_added")));
			pro.setProductNumber(result.getString("product_number"));
			pro.setProductId(result.getLong("product_id"));
			pro.setTradeMark(getTradeMark(pro.getProductId()));
			pro.setImageUrl(WebInitializer.LOCATION + result.getString("image_url"));
			pro.setCategory(categoryDAO.getProductsCategory(pro.getProductId()));
			result.close();
			statement.close();
			return pro;
	}

	public String getTradeMark(long id) throws SQLException {
		String query = "SELECT trade_mark_name FROM technomarket.trade_marks AS t JOIN technomarket.product AS p ON(t.trade_mark_id = p.trade_mark_id) WHERE product_id = ?";
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setLong(1, id);
		ResultSet result = statement.executeQuery();
		String tradeMark = null;
		while(result.next()){
			tradeMark = result.getString(1);
		}
		
		result.close();
		statement.close();
		
		return tradeMark;
	}
	public LinkedHashSet<Product> searchProductByPrice(double price1 , double price2) throws SQLException{
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement("SELECT product.product_id,trade_marks.trade_mark_name, product.product_name,product.price,product.warranty,product.percent_promo,product.date_added,product.product_number,product.image_url FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)  WHERE product.price between ? and ?"); 
		statement.setDouble(1, price1);
		statement.setDouble(2, price2);
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()){
			Product product = new Product();
			product.setProductId(resultSet.getLong("product_id"));
			product.setTradeMark(resultSet.getString("trade_mark_name"));
			product.setName(resultSet.getString("product_name"));
			product.setPrice(resultSet.getString("price"));
			product.setWorranty(resultSet.getInt("warranty"));
			product.setPercentPromo(resultSet.getInt("percent_promo"));
			product.setDateAdded(LocalDate.parse(resultSet.getString("date_added")));
			product.setProductNumber(resultSet.getString("product_number"));
			product.setImageUrl(resultSet.getString("image_url"));
			products.add(product);
		}
		
		return products;
	}
	
	public Product searchProductById(String productId) throws SQLException{
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement("SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price, product.warranty, product.percent_promo, product.date_added, product.product_number, product.image_url, product.description FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id) WHERE product.product_id = ?");
		statement.setString(1, productId);
		ResultSet result = statement.executeQuery();
		Product product = new Product();
		while(result.next()){
			product.setProductId(result.getLong(1));
			product.setTradeMark(result.getString(2));
			product.setName(result.getString(3));
			product.setPrice(result.getString(4));
			product.setWorranty(result.getInt(5));
			product.setPercentPromo(result.getInt(6));
			product.setDateAdded(LocalDate.parse(result.getString(7)));
			product.setProductNumber(result.getString(8));
			product.setImageUrl(result.getString(9));
			product.setDescription(result.getString(10));
		}
		statement.close();
		return product;
		
	}
	
//admin panel in products:
	
	// admin is able to set specific product on promo by adding promo percent:
	
	public void setPromoPercent(int productId, int promoPersent) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement(
				"UPDATE technomarket.product SET percent_promo = ? WHERE product_id = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, promoPersent);
		ps.setLong(2, productId);
		ps.executeUpdate();
		ps.close();
	}

	// insert product module:

	public void insertNewProduct(Product p) throws SQLException {
		// inserts Product into product table:
		this.connection = DBManager.getConnections();
			int tradeMarkId = getTradeMarkId(p.getTradeMark());
			PreparedStatement ps = this.connection.prepareStatement(
					"INSERT INTO technomarket.product (trade_mark_id, credit_id, product_name, price, warranty, percent_promo, date_added, product_number, image_url, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, tradeMarkId);
			ps.setString(2, null);
			ps.setString(3, p.getName());
			ps.setDouble(4, Double.parseDouble(p.getPrice().toString()));
			ps.setInt(5, p.getWorranty());
			ps.setInt(6, p.getPercentPromo());
			ps.setString(7, LocalDate.now().toString());
			ps.setString(8, generateProductNumber());
			ps.setString(9, p.getImageUrl());
			ps.setString(10, p.getDescription());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			p.setProductId(rs.getLong(1));

			// insert Product and its Category into product_has_category table:
			insertProductIntoProductHasCategory(p);

			// insert new row in characteristics table with the product id and
			// its
			// characteristics name and type:
			if(p.getCharacteristics() != null){
				insertProductIntoCharacteristics(p);
			}
			ps.close();

	}

	public String generateProductNumber() throws SQLException {
		String query = "SELECT product_id FROM technomarket.product ORDER BY product_id DESC LIMIT 1;";
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		long number = 1;
		if(result.next()){
			number = result.getLong("product_id") + 1;
		}else{
			return String.valueOf(number);
		}
		result.close();
		statement.close();
		return String.valueOf(number);
	}

	private void insertProductIntoCharacteristics(Product p) throws SQLException {
		characterisicsDAO.addProductInCharacteristicsTable(p);
	}

	private void insertProductIntoProductHasCategory(Product p) throws SQLException {
		categoryDAO.insertProductIntoProductHasCategory(p);
	}

	private int getTradeMarkId(String tradeMark) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection
				.prepareStatement("SELECT trade_mark_id FROM technomarket.trade_marks WHERE trade_mark_name LIKE ?;");
		ps.setString(1, tradeMark);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int result = rs.getInt("trade_mark_id");
		rs.close();
		ps.close();
		
		return result;
	}

	// remove product module:

	public void removeProduct(long productId) throws SQLException {
		this.connection = DBManager.getConnections();
		String imageUrl = getProductUrl(productId);
		System.out.println("================================1");
		this.connection.setAutoCommit(false);
		try {
			PreparedStatement ps3 = this.connection.prepareStatement(
					"DELETE FROM technomarket.product_has_category WHERE product_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps3.setLong(1, productId);
			ps3.executeUpdate();
			System.out.println("================================2");
			PreparedStatement ps4 = this.connection.prepareStatement(
					"DELETE FROM technomarket.store_has_product WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
			ps4.setLong(1, productId);
			ps4.executeUpdate();
			System.out.println("================================3");
			PreparedStatement ps1 = this.connection.prepareStatement("DELETE FROM technomarket.product WHERE product_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps1.setLong(1, productId);
			ps1.executeUpdate();
			System.out.println("================================4");
			PreparedStatement ps2 = this.connection.prepareStatement(
					"DELETE FROM technomarket.order_has_product WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
			ps2.setLong(1, productId);
			ps2.executeUpdate();
			System.out.println("================================5");
			PreparedStatement ps6 = this.connection.prepareStatement(
					"DELETE FROM technomarket.characteristics WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
			ps6.setLong(1, productId);
			ps6.executeUpdate();
			System.out.println("================================6");
			PreparedStatement ps5 = this.connection.prepareStatement(
					"DELETE FROM technomarket.user_has_favourite WHERE product_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps5.setLong(1, productId);
			ps5.executeUpdate();
			this.connection.commit();
			System.out.println("================================7");
			ps1.close();
			ps2.close();
			ps3.close();
			ps4.close();
			ps5.close();
			ps6.close();
			
			//removing the products image after deletion from the DB table:
			
			File image = new File(WebInitializer.LOCATION + imageUrl);
			if(image.exists()){
				image.delete();
			}
		} catch (SQLException e) {
			this.connection.rollback();
			throw new SQLException();
		} finally {
			this.connection.setAutoCommit(true);
			
		}
	}

	private String getProductUrl(long productId) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.image_url FROM technomarket.product WHERE product_id = ?");
		statement.setLong(1, productId);
		ResultSet result = statement.executeQuery();
		String imageUrl = "";
		while(result.next()){
			imageUrl = result.getString(1);
		}
		result.close();
		statement.close();
		return imageUrl;
	}

	// Search product by name;
	public LinkedHashSet searchProductByName(String productName) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price, product.warranty, product.percent_promo, product.date_added, product.product_number, product.image_url FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id) WHERE product.product_name LIKE ?");
		statement.setString(1, "%"+productName+"%");
		ResultSet result = statement.executeQuery();
		LinkedHashSet<Product> searchResult = new LinkedHashSet<>();
		while(result.next()){
			Product product = new Product();
			product.setProductId(result.getLong(1));
			product.setTradeMark(result.getString(2));
			product.setName(result.getString(3));
			product.setPrice(result.getString(4));
			product.setWorranty(result.getInt(5));
			product.setPercentPromo(result.getInt(6));
			product.setDateAdded(LocalDate.parse(result.getString(7)));
			product.setProductNumber(result.getString(8));
			product.setImageUrl(result.getString(9));
			searchResult.add(product);
		}
		result.close();
		statement.close();
		return searchResult;

	}

	//Search product by category name:
	public Set<Product> searchProductByCategoryName(String category) throws SQLException, InvalidCategoryDataException {
		Set<Product> products = new HashSet<>();
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM technomarket.product AS pr JOIN technomarket.product_has_category AS h ON(pr.product_id = h.product_id) JOIN technomarket.categories AS c ON(h.category_id = c.category_id) JOIN technomarket.categories AS p ON(c.parent_category_id = p.category_id) WHERE c.category_name LIKE ?;");
		statement.setString(1, "%"+ category +"%");
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			Product pro = new Product();
			pro.setProductId(result.getLong("product_id"));
			pro.setTradeMark(getTradeMark(pro.getProductId()));
			pro.setName(result.getString("product_name"));
			pro.setPrice(result.getString("price"));
			pro.setWorranty(result.getInt("warranty"));
			pro.setPercentPromo(result.getInt("percent_promo"));
			pro.setDateAdded(LocalDate.parse(result.getString("date_added")));
			pro.setProductNumber(result.getString("product_number"));
			pro.setImageUrl(result.getString("image_url"));
			pro.setCategory(new Category(result.getString("c.category_name")));
			products.add(pro);
		}
		result.close();
		statement.close();
		return products;

	}
	
//	// Search product by category
//	public List<Product> searchProductByCategory(Category category, campareEnum campare) throws SQLException {
//		String orderBy = "";
//		if (campare == campareEnum.defaultt) {
//			// default date_added
//			orderBy += "date_added";
//		} else {
//			orderBy += campare;
//		}
//		LinkedList<Product> products = new LinkedList<>();
//		this.connection = DBManager.getConnections();
//		PreparedStatement statement = this.connection.prepareStatement(
//				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price, product.warranty"
//						+ ", product.percent_promo, product.date_added, product.product_number FROM technomarket.product"
//						+ "JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)"
//						+ "JOIN technomarket.product_has_category ON(product_has_category.product_id = product_has_category.category_id)"
//						+ "JOIN technomarket.categories on(categories.category_id = product_has_category.category_id) WHERE technomarket.categories.categoy_name = ? GROUP BY ? ORDER BY ?");
//		statement.setString(1, category.getName());
//		statement.setString(2, orderBy);
//		statement.setString(3, orderBy);
//		ResultSet result = statement.executeQuery();
//		while (result.next()) {
//			Product pro = new Product();
//			pro.setProductId(result.getLong(1));
//			pro.setTradeMark(result.getString(2));
//			pro.setName(result.getString(3));
//			pro.setPrice(result.getString(4));
//			pro.setWorranty(result.getInt(5));
//			pro.setPercentPromo(result.getInt(6));
//			pro.setDateAdded(LocalDate.parse(result.getString(7)));
//			pro.setProductNumber(result.getString(8));
//			products.add(pro);
//		}
//		result.close();
//		statement.close();
//	
//		return products;
//
//	}

	// Search product with promo price
	public Set<Product> getPromoProduct() throws SQLException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price,product.warranty, product.percent_promo, product.date_added, product.product_number, product.image_url FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)  WHERE product.percent_promo > 0");
		ResultSet result = statement.executeQuery();
		Product product = null;
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setTradeMark(result.getString(2));
			product.setName(result.getString(3));
			product.setPrice(result.getString(4));
			product.setWorranty(result.getInt(5));
			product.setPercentPromo(result.getInt(6));
			product.setDateAdded(LocalDate.parse(result.getString(7)));
			product.setProductNumber(result.getString(8));
			product.setImageUrl(result.getString(9));
			products.add(product);
		}
		result.close();
		statement.close();
		
		return products;
	}

	// Search produc where trade marke is apple

	public Set<Product> getAppleProduct() throws SQLException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price,product.warranty, product.percent_promo, product.date_added, product.product_number, product.image_url FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)  WHERE trade_mark_name LIKE 'apple%' or 'ipad'");
		ResultSet result = statement.executeQuery();
		Product product = null;
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setTradeMark(result.getString(2));
			product.setName(result.getString(3));
			product.setPrice(result.getString(4));
			product.setWorranty(result.getInt(5));
			product.setPercentPromo(result.getInt(6));
			product.setDateAdded(LocalDate.parse(result.getString(7)));
			product.setProductNumber(result.getString(8));
			product.setImageUrl(result.getString(9));
			products.add(product);
		}
		result.close();
		statement.close();
		
		return products;
	}

	// Search product by price
	public Set<Product> searchProductByPrice(String fromPrice, String toPrice)
			throws InvalidProductDataException, SQLException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		if (fromPrice.compareTo(toPrice) > 0) {
			throw new InvalidProductDataException();
		}
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, product.product_name, product.price, product.product_number"
						+ ",trade_marks.trade_mark_name , product.warranty, product.percent_promo"
						+ "FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)"
						+ "WHERE product.price between ? and ?");
		statement.setString(1, fromPrice);
		statement.setString(2, toPrice);
		ResultSet result = statement.executeQuery();
		Product product = null;
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setName(result.getString(2));
			product.setPrice(result.getString(3));
			product.setProductNumber(result.getString(4));
			product.setTradeMark(result.getString(5));
			product.setWorranty(result.getInt(6));
			product.setPercentPromo(result.getInt(7));
			products.add(product);
		}
		result.close();
		statement.close();
		
		return products;
	}

	// Search product where category is home
	public Set<Product> searchProductWithCategoryHome() throws SQLException, InvalidCategoryDataException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		this.connection = DBManager.getConnections();
		String query = "SELECT * FROM technomarket.product AS pr JOIN technomarket.product_has_category AS h ON(pr.product_id = h.product_id) JOIN technomarket.categories AS c ON(h.category_id = c.category_id) JOIN technomarket.categories AS p ON(c.parent_category_id = p.category_id) WHERE p.category_name LIKE ?";
		PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1,"%Home%");
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			Product product = new Product();
			product.setProductId(result.getLong("product_id"));
			product.setName(result.getString("product_name"));
			product.setPrice(result.getString("price"));
			product.setProductNumber(result.getString("product_number"));
			product.setTradeMark(getTradeMark(product.getProductId()));
			product.setWorranty(result.getInt("warranty"));
			product.setPercentPromo(result.getInt("percent_promo"));
			product.setImageUrl(result.getString("image_url"));
			product.setCategory(new Category(result.getString("c.category_name")));
			product.setDateAdded(LocalDate.parse(result.getString("date_added")));
			products.add(product);
		}
		result.close();
		statement.close();

		return products;
	}
	
	//Finds emails of all users who have specific product in favourites and isAbonat:
	
	public HashSet<String> getEmailsPerFavourites(long userId) throws SQLException{
		String query = "SELECT email, isAbonat FROM technomarket.users AS u JOIN technomarket.user_has_favourite AS f USING(user_id) WHERE u.isAbonat = ? AND user_id = ?;";
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setBoolean(1, true);
		statement.setLong(2, userId);
		ResultSet result = statement.executeQuery();
		HashSet<String> emails = new HashSet<>();
		while(result.next()){
			System.out.println(result.getString("email"));
			emails.add(result.getString("email")); 
		}
		result.close();
		statement.close();
		return emails;
	}

}
