package com.example.model.DBM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DBManager {
	
    private Connection connection;
	
	private static final String DB_IP = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "technomarket";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD ="1234";
	private static final String URL = "jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_NAME+"?useUnicode=yes&characterEncoding=UTF-8";
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnections(){
		if(this.connection == null){
			try {
				this.connection = DriverManager.getConnection(URL,DB_USERNAME,DB_PASSWORD);
			} catch (SQLException e) {
				//Zavedi ni na nqkude 
				e.printStackTrace();
			}
		}
		return this.connection;
	}

}
