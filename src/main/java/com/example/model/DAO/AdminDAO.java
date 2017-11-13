package com.example.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Category;
import com.example.model.Characteristics;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Store;
import com.example.model.User;
import com.example.model.DBM.DBManager;
import com.example.model.exceptions.IlligalAdminActionException;
import com.example.model.exceptions.IlligalUserActionException;
import com.example.model.exceptions.InvalidUserDataException;
import com.example.model.exceptions.NotAnAdminException;


@Component
public class AdminDAO {
	@Autowired
	DBManager DBManager;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	StoreDAO storeDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	OrderDAO orderDAO;
	
	
	private Connection connection;
	
	public void insertNewProduct(Product p, User admin) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			productDAO.insertNewProduct(p);
		}else{
			throw new NotAnAdminException();
		}
		
	}
	
	public void removeProduct(long productId, User admin) throws NotAnAdminException, SQLException{
		if(admin.getIsAdmin()){
			productDAO.removeProduct(productId);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void changeQuantityInStore(int storeId, int productId, int quantity, User admin) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			storeDAO.insertProductInStore(storeId, productId, quantity);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void insertNewStore(User admin, Store s) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			storeDAO.insertNewStore(s);
		}else{
			throw new NotAnAdminException();
		}
	}

	//changes the promo percent of product, adds promo sticker in view
	public void setPromoPercent(User admin, int productId, int promoPercent) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			productDAO.setPromoPercent(productId, promoPercent);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	//makes user admin:
	public void changeUserIsAdminStatus(User admin, String email) throws NotAnAdminException, SQLException, InvalidUserDataException{
		if(admin.getIsAdmin()){
			userDAO.createAdmin(email.trim());
		}else{
			throw new NotAnAdminException();
		}
	}
	
	//banns user:
	public void changeUserIsBannedStatus(User admin, User u, boolean isBanned) throws NotAnAdminException, SQLException, IlligalAdminActionException{
		if(admin.getIsAdmin()){
			userDAO.changeUserIsBannedStatus(u, isBanned);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	//set order as paid:
	public void setOrderAsPaid(User admin, long orderId) throws NotAnAdminException, SQLException, IlligalAdminActionException{
		if(admin.getIsAdmin()){
			orderDAO.setOrderAsPaid(orderId);
		}else{
			throw new NotAnAdminException();
		}
	}
	public void setOrderAsNotConfirmed(User admin, long orderId) throws NotAnAdminException, SQLException, IlligalAdminActionException, IlligalUserActionException{
		if(admin.getIsAdmin()){
			orderDAO.setOrderAsNotConfirmed(orderId);
		}else{
			throw new NotAnAdminException();
		}
	}
	public void setOrderAsConfirmed(User admin, long orderId) throws NotAnAdminException, SQLException, IlligalAdminActionException, IlligalUserActionException{
		if(admin.getIsAdmin()){
			orderDAO.setOrderAsConfirmed(orderId);
		}else{
			throw new NotAnAdminException();
		}
	}
	public void deleteOrder(User admin,long orderId) throws NotAnAdminException, SQLException{
		if(admin.getIsAdmin()){
			orderDAO.deleteOrder(orderId);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	
}
