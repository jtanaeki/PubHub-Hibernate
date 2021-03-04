package com.pubhub.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.pubhub.dao.BookDAO;
import com.pubhub.dao.BookDAOImpl;
import com.pubhub.dao.TagDAO;
import com.pubhub.dao.TagDAOImpl;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages a single instance of the database connection.
 */
public class DAOUtilities {

	private static Connection connection;
	private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/pubhub";
	private static final String CONNECTION_USERNAME = "root";
	private static final String CONNECTION_PASSWORD = "password";
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName(DRIVER_CLASS);
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		return connection;
	}
	
	public static BookDAO getBookDAO() {
		return new BookDAOImpl();
	}
	
	public static TagDAO getTagDAO() {
		return new TagDAOImpl();
	}

}
