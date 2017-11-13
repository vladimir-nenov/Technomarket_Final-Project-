package com.example.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Store;
import com.example.model.DBM.DBManager;

@Component
public class TradeMarkDAO {

	@Autowired
	DBManager DBManager;
	
	private Connection connection;
	
	public boolean tradeMarkExist(String tradeMark) throws SQLException{
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("SELECT trade_mark_name FROM technomarket.trade_marks WHERE trade_mark_name = ?;");
		ps.setString(1, tradeMark);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ps.close();
			return true;
		}else{
			ps.close();
			return false;
		}
		
	}
	
	public void insertTradeMark(String tradeMark) throws SQLException {
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("INSERT INTO technomarket.trade_marks (trade_mark_name) VALUES (?)");
		ps.setString(1, tradeMark);
		ps.executeUpdate();
		ps.close();
	}
	
	public TreeSet<String> getAllTradeMarks() throws SQLException{
		TreeSet<String> tradeMarks = new TreeSet<>();
		this.connection = DBManager.getConnections();
		PreparedStatement ps = this.connection.prepareStatement("SELECT trade_mark_name FROM technomarket.trade_marks;");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String tradeMark = rs.getString("trade_mark_name");
			tradeMarks.add(tradeMark);
		}
		ps.close();
		rs.close();
		return tradeMarks;
	}


	
}
