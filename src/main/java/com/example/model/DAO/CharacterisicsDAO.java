package com.example.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Characteristics;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.DBM.DBManager;
import com.example.model.exceptions.InvalidCharacteristicsDataException;


@Component
public class CharacterisicsDAO {
    @Autowired
    DBManager DBManager;

	private Connection connection;

	 void addProductInCharacteristicsTable(Product p) throws SQLException {
		for (Iterator<Characteristics> iterator = p.getCharacteristics().iterator(); iterator.hasNext();) {
			Characteristics characteristic = iterator.next();
			Connection con = DBManager.getConnections();
			con.setAutoCommit(false);
			try {
				long typeId = getCharacteristicsTypeId(characteristic);
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO technomarket.characteristics (product_id, characteristics_name, characteristics_type_id) VALUES (?, ?, ?);",
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, p.getProductId());
				ps.setString(2, characteristic.getName());
				ps.setLong(3, typeId);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				characteristic.setCharacteristicsId(rs.getLong(1));
				con.commit();
				ps.close();
			} catch (SQLException e) {
				con.rollback();
				throw new SQLException();
			} finally {
				con.setAutoCommit(true);
				
			}
		}
	}

	private long getCharacteristicsTypeId(Characteristics characteristic) throws SQLException {
		Connection con = DBManager.getConnections();
		PreparedStatement ps = con.prepareStatement(
				"SELECT characteristics_type_id FROM technomarket.characteristics_type WHERE characteristics_type_name LIKE '?';");
		ps.setString(1, characteristic.getTypeCharacteristics());
		ResultSet rs = ps.executeQuery();
		rs.next();
		long characteristics_type_id = rs.getLong("characteristics_type_id");
		ps.close();
		return characteristics_type_id;
	}

	public ArrayList<Characteristics> getProducsCharacteristics(long long1)
			throws SQLException, InvalidCharacteristicsDataException {
		ArrayList<Characteristics> characteristics = new ArrayList<>();
		Connection con = DBManager.getConnections();
		PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM technomarket.characteristics AS c JOIN technomarket.characteristics_type AS t ON(c.characteristics_type_id = t.characteristics_type_id) WHERE c.product_id = ?;");
		ps.setLong(1, long1);
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			Characteristics characteristic = new Characteristics(result.getString("characteristics_name"),
					result.getString("characteristics_type_name"));
			characteristics.add(characteristic);
		}
		ps.close();
		return characteristics;
	}

}
