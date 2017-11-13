package com.example.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.DBM.DBManager;
import com.example.model.exceptions.EmailAlreadyInUseException;
import com.example.model.exceptions.IlligalAdminActionException;
import com.example.model.exceptions.IlligalUserActionException;
import com.example.model.exceptions.InvalidCategoryDataException;
import com.example.model.exceptions.InvalidCharacteristicsDataException;
import com.example.model.exceptions.InvalidUserDataException;
import com.example.model.util.Encrypter;



@Component
public class UserDAO {
	@Autowired
	DBManager DBManager;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	OrderDAO orderDAO;
	
	private Connection connection;
	
	public void insertUser(User user) throws SQLException, EmailAlreadyInUseException {
		if (checkIfUserWithSameEmailExist(user.getEmail())) {
			throw new EmailAlreadyInUseException();
		} else {
			String query = "INSERT INTO technomarket.users ( first_name, last_name, email, password, gender,date_of_birth, isAdmin,isAbonat,isBAnned)VALUES(?,?,?,?,?,?,?,?,?);";
			this.connection = DBManager.getConnections();
			java.sql.PreparedStatement statement = this.connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, Encrypter.encrypt(user.getPassword()));
			statement.setString(5, user.getGender());
			statement.setString(6, user.getBirthDate().toString());
			statement.setString(7, "" + (user.getIsAdmin() ? 1:0));
			statement.setString(8, "" + (user.getIsAbonat() ? 1:0));
			statement.setString(9, "" + (user.getIsBanned() ? 1:0));
			statement.executeUpdate();
			ResultSet resutSet = statement.getGeneratedKeys();
			// Взимаме от базата идто и го слагаме на обекта;
			// За да можем после да ми направим сесия;
			while (resutSet.next()) {
				user.setId(resutSet.getInt(1));
			}
			statement.close();
		}
	}
  
	// log in of user:
	public boolean existingUser(String userName, String password) throws SQLException {
		String checkQuery = "SELECT * FROM technomarket.users WHERE email = ? and password = ?";
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(checkQuery);
		statement.setString(1, userName);
		statement.setString(2, Encrypter.encrypt(password));
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			statement.close();
			return true;
		}
		statement.close();
		return false;
	}
	/*
	 * public User getUser(String username) throws SQLException{ Connection con
	 * = DBManager.getInstance().getConnection(); PreparedStatement ps = con.
	 * prepareStatement("SELECT id, username, password, email FROM users WHERE username = ?"
	 * ); ps.setString(1, username); ResultSet rs = ps.executeQuery();
	 * rs.next(); HashSet<Order> orders =
	 * OrderDao.getInstance().getOrdersForUser(rs.getLong("id")); return new
	 * User( rs.getLong("id"), username, rs.getString("password"),
	 * rs.getString("email"), orders); }
	 * 
	 */

	// Метода exitsUser проверява и ако каже че има се връща потребителя от този
	// метод!
	public User getUser(String userName)
			throws SQLException, InvalidCategoryDataException {
		this.connection = DBManager.getConnections();
		User user = new User();
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM technomarket.users WHERE email = ?");
		statement.setString(1, userName);
		ResultSet result = statement.executeQuery();
		result.next();
		user.setId(result.getLong("user_id"));
		user.setFirstName(result.getString("first_name"));
		user.setLastName(result.getString("last_name"));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setGender(result.getString("gender"));
		user.setBirthDate(LocalDate.parse(result.getString("date_of_birth")));
		user.setAdmin(result.getBoolean("isAdmin"));
		user.setAbonat(result.getBoolean("isAbonat"));
		user.setBanned(result.getBoolean("isBanned"));
		LinkedHashSet<Order> orders = orderDAO.getOrdersForUser(user.getUserId());
		user.setOrders(orders);
		result.close();
		statement.close();
		return user;
	}

	// Checks if the email exist in DB in connection with user. Used when password is forgotten or to check is such user is already registrated:
	public boolean checkIfUserWithSameEmailExist(String email) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement("SELECT users.email FROM technomarket.users WHERE users.email = ?");
		statement.setString(1, email);
		ResultSet result = statement.executeQuery();
		boolean exist = result.next();
		statement.close();
		return exist;
		
	}
	
	public boolean isertNewlyGeneratedPassword(String email, String newPassword) throws SQLException{
		long userId = getUserIdByEmail(email);
			this.connection = DBManager.getConnections();
			PreparedStatement ps = this.connection.prepareStatement("UPDATE technomarket.users SET password = ? WHERE user_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, Encrypter.encrypt(newPassword));
			ps.setLong(2, userId);
			int resultSet = ps.executeUpdate();
			ps.close();
			if(resultSet > 0){
				return true;
			}else{
				return false;
			}
	}
	
	
private long getUserIdByEmail(String email) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement("SELECT user_id FROM technomarket.users WHERE users.email = ?");
		statement.setString(1, email);
		ResultSet result = statement.executeQuery();
		long userId = 0;
		while(result.next()){
			userId = result.getLong("user_id");
		}
		
		statement.close();
		return userId;
	}

//	public String getUserPassWhenForgotten(String email) throws SQLException{
//		this.connection = DBManager.getConnections();
//		PreparedStatement statement = this.connection.prepareStatement("SELECT password FROM technomarket.users WHERE users.email = ?");
//		statement.setString(1, email);
//		ResultSet result = statement.executeQuery();
//		String password = null;
//		while(result.next()){
//			password = result.getString("password");
//		}
//		statement.close();
//		return password;
//	}

	// user confirming his order:

//	public void confirmOrder(Order o, boolean isConfirmed) throws SQLException, IlligalUserActionException {
//		orderDAO.setOrderAsConfirmed(o, isConfirmed);
//	}

	// favourite products:

	//Is product in favourite for specific user:
	
	public boolean isPrductInFavourite(String userId, String productId) throws SQLException{
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM technomarket.user_has_favourite WHERE user_id = ? AND product_id = ?;");
		ps.setString(1, userId);
		ps.setString(2, productId);
		ResultSet rs = ps.executeQuery();
		boolean isThere = rs.next();
		ps.close();
		rs.close();
		if(isThere){
			return true;
		}else{
			return false;
		}
	}
	
	// Adding favourite product to user account:
	public void addInFavorite(long userId, long productId) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement("INSERT INTO technomarket.user_has_favourite (user_id, product_id) VALUES (?,?)");
		statement.setLong(1, userId);
		statement.setLong(2, productId);
		statement.executeUpdate();
		statement.close();
		
	}
//TUKA TUKA TUKA
	// Remove favourite product:
	public void removeFavouriteProduct(long userId, long  productId) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement statment = this.connection.prepareStatement("DELETE FROM technomarket.user_has_favourite WHERE user_id = ? AND product_id = ?");
		statment.setLong(1, userId);
		statment.setLong(2, productId);
		statment.executeUpdate();
		statment.close();
	
	}

	// Listing all favourite products:
	public 	LinkedHashSet<Product> viewFavourite(User user) throws SQLException {
		String query = "SELECT * FROM technomarket.product AS p JOIN technomarket.user_has_favourite AS f ON(p.product_id = f.product_id)WHERE f.user_id ="
				+ user.getUserId();
		this.connection = DBManager.getConnections();
		PreparedStatement statement = this.connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		Product product = null;
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setName(result.getString("product_name"));
			product.setPrice(result.getString("price"));
			product.setWorranty(result.getInt("warranty"));
			product.setPercentPromo(result.getInt("percent_promo"));
			product.setDateAdded(LocalDate.parse(result.getString("date_added")));
			product.setProductNumber(result.getString("product_number"));
			product.setImageUrl(result.getString("image_url"));
			product.setTradeMark(productDAO.getTradeMark(product.getProductId()));
			products.add(product);
		}
		
		statement.close();
		return products;
	}

	// user statuses, used by admins:

	public void changeUserIsAdminStatus(User u, boolean isAdmin) throws SQLException, IlligalAdminActionException {
		if (u.getIsAdmin() && isAdmin) {
			throw new IlligalAdminActionException();
		} else if (!u.getIsAdmin() && !isAdmin) {
			throw new IlligalAdminActionException();
		} else {
			this.connection = DBManager.getConnections();
			PreparedStatement ps = this.connection.prepareStatement("UPDATE technomarket.users SET isAdmin = ? WHERE user_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, isAdmin);
			ps.setLong(2, u.getUserId());
			ps.executeUpdate();
			ps.close();
		}
	}

	public void changeUserIsBannedStatus(User u, boolean isBanned) throws IlligalAdminActionException, SQLException {
		if (u.getIsBanned() && isBanned) {
			throw new IlligalAdminActionException();
		} else if (!u.getIsBanned() && !isBanned) {
			throw new IlligalAdminActionException();
		} else {
			this.connection = DBManager.getConnections();
			PreparedStatement ps = this.connection.prepareStatement("UPDATE technomarket.users SET isBanned = ? WHERE user_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, isBanned);
			ps.setLong(2, u.getUserId());
			ps.executeUpdate();
			ps.close();
		}
	}
	public void createAdmin(String email) throws SQLException, InvalidUserDataException{
		this.connection = DBManager.getConnections();
		PreparedStatement st = this.connection.prepareStatement("SELECT users.user_id FROM technomarket.users WHERE email = ? AND isAdmin = false;");
		st.setString(1, email);
		ResultSet result = st.executeQuery();
		String userId = "";
		while(result.next()){
			userId+=result.getString(1);
		}
		st.close();
		if(userId.isEmpty()){
			throw new InvalidUserDataException();
		}
		PreparedStatement statement = this.connection.prepareStatement("UPDATE  technomarket.users SET users.isAdmin = true WHERE user_id = ?;");
		statement.setString(1, userId);
		statement.executeUpdate();
		statement.close();
		
	}

	//user subscribes for news:
	
	public void createNewAbonat(long userId) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("UPDATE technomarket.users SET isAbonat = ? WHERE user_id = ?;",
				Statement.RETURN_GENERATED_KEYS);
		ps.setBoolean(1, true);
		ps.setLong(2, userId);
		ps.executeUpdate();
		ps.close();
		
		
		
		// TODO Auto-generated method stub
		
	}

}
