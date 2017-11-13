package com.example.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Category;
import com.example.model.Product;
import com.example.model.DBM.DBManager;
import com.example.model.exceptions.InvalidCategoryDataException;
import com.mysql.jdbc.StandardSocketFactory;
;
@Component
public class CategoryDAO {
    @Autowired
    private DBManager DBManager;
	private Connection connection;

	

	

	public void insertProductIntoProductHasCategory(Product p) throws SQLException {
		this.connection = DBManager.getConnections();
		this.connection.setAutoCommit(false);
		try {
			long categoryId = getCategoryId(p.getCategory().getName());
			PreparedStatement ps = this.connection.prepareStatement(
					"INSERT INTO technomarket.product_has_category (category_id, product_id) VALUES (?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, categoryId);
			ps.setLong(2, p.getProductId());
			ps.executeUpdate();
			this.connection.commit();
			ps.close();
		} catch (SQLException e) {
			this.connection.rollback();
			throw new SQLException();
		} finally {
			this.connection.setAutoCommit(true);
		}
	}

	private long getCategoryId(String category) throws SQLException {
		Connection con = DBManager.getConnections();
		PreparedStatement ps = con
				.prepareStatement("SELECT category_id FROM technomarket.categories WHERE category_name LIKE ?;");

		ps.setString(1, category);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getLong("category_id");
	}

	public Category getProductsCategory(long productId) throws SQLException, InvalidCategoryDataException {
		Connection con = DBManager.getConnections();
		PreparedStatement ps = con.prepareStatement(
				"SELECT category_name FROM technomarket.categories AS c JOIN technomarket.product_has_category AS h ON(c.category_id = h.category_id) JOIN technomarket.product AS p ON(h.product_id = p.product_id) WHERE h.product_id = ?;");
		ps.setLong(1, productId);
		ResultSet rs = ps.executeQuery();
		String name = "";
		while(rs.next()){
			name += rs.getString("category_name");
		}
		Category category = new Category(name);
		System.out.println(category.getName());
		return category;
	}
	
	
	public boolean categoryExist(String category) throws SQLException{
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("SELECT category_name FROM technomarket.categories WHERE category_name = ?;");
		ps.setString(1, category);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			rs.close();
			return true;
		}else{
			rs.close();
			return false;
		}
	}
	
	public void insertCategory(String category) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("INSERT INTO technomarket.categories (category_name) VALUES (?)");
		ps.setString(1, category);
		ps.executeUpdate();
	}
	
	public TreeSet<String> getAllInnerCategories() throws SQLException{
		TreeSet<String> innerCategories = new TreeSet<>();
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("SELECT category_name FROM technomarket.categories WHERE parent_category_id IS NOT NULL;");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String categoryName = rs.getString("category_name");
			innerCategories.add(categoryName);
		}
		ps.close();
		rs.close();
		return innerCategories;
	}	
	
	public TreeMap<String, TreeSet<String>> getCategoriesWithParentKeys() throws SQLException{
		TreeMap<String, TreeSet<String>> result = new TreeMap<String, TreeSet<String>>();
		this.connection = DBManager.getConnections();
		PreparedStatement ps1 = this.connection.prepareStatement("SELECT category_name FROM technomarket.categories WHERE parent_category_id IS NULL;");
		ResultSet rs1 = ps1.executeQuery();
		while (rs1.next()) {
			result.put(rs1.getString("category_name"), new TreeSet<String>());
		}
		ps1.close();
		rs1.close();
		PreparedStatement ps2 = this.connection.prepareStatement("SELECT p.category_name, c.category_name FROM technomarket.categories AS p JOIN technomarket.categories AS c ON (c.parent_category_id = p.category_id);");
		ResultSet rs2 = ps2.executeQuery();
		while (rs2.next()) {
			if(rs2.getString("p.category_name").equals("ТЕЛЕВИЗОРИ И АУДИО")){
				result.get("ТЕЛЕВИЗОРИ И АУДИО").add(rs2.getString("c.category_name"));
			}else if(rs2.getString("p.category_name").equals("КОМПЮТРИ И ПЕРИФЕРИЯ")){
				result.get("КОМПЮТРИ И ПЕРИФЕРИЯ").add(rs2.getString("c.category_name"));
			}else if(rs2.getString("p.category_name").equals("ТЕЛЕФОНИ И ТАБЛЕТИ")){
				result.get("ТЕЛЕФОНИ И ТАБЛЕТИ").add(rs2.getString("c.category_name"));
			}else if(rs2.getString("p.category_name").equals("ЕЛЕКТРОУРЕДИ")){
				result.get("ЕЛЕКТРОУРЕДИ").add(rs2.getString("c.category_name"));
			}else if(rs2.getString("p.category_name").equals("МАЛКИ ЕЛЕКТРОУРЕДИ")){
				result.get("МАЛКИ ЕЛЕКТРОУРЕДИ").add(rs2.getString("c.category_name"));
			}else if(rs2.getString("p.category_name").equals("ФОТО И ВИДЕО")){
				result.get("ФОТО И ВИДЕО").add(rs2.getString("c.category_name"));
			}else if(rs2.getString("p.category_name").equals("HOME")){
				result.get("HOME").add(rs2.getString("c.category_name"));
			}else if(rs2.getString("p.category_name").equals("ЗАБАВЛЕНИЯ")){
				result.get("ЗАБАВЛЕНИЯ").add(rs2.getString("c.category_name"));
			}
			
			//result.put(rs2.getString("category_name"), new TreeSet<String>());
		}
		ps2.close();
		rs2.close();
		return result;
	}
	

}
