package com.pubhub.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pubhub.model.Book;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.utilities.HibernateUtil;

import javassist.bytecode.ByteArray;

/**
 * Implementation for the Shopping_CartDAO, responsible for querying the database for Shopping_Cart objects.
 */
@SuppressWarnings("deprecation")
public class Shopping_CartDAOImpl implements Shopping_CartDAO{

	private static Logger log = Logger.getLogger(Shopping_CartDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Shopping_Cart> getCart(String user) {
		List<Shopping_Cart> cart = new ArrayList<>();
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String sql = "SELECT * from shopping_cart_" + userSubstring;
			SQLQuery<Object[]> query = session.createSQLQuery(sql);
			List<Object[]> rows = query.list();
			for(Object[] row : rows) {
				Shopping_Cart item = new Shopping_Cart();
				item.setId(Integer.parseInt(row[0].toString()));		// set to NOT NULL in database
				item.setIsbn13(row[1].toString());		// set to NOT NULL in database
				if(row[2] != null) {
					item.setTitle(row[2].toString());
				}
				if(row[3] != null) {
					item.setAuthor(row[3].toString());
				}
				if(row[4] != null) {
					item.setPublishDate(LocalDate.parse(row[4].toString()));
				}
				if(row[5] != null) {
					item.setPrice(Double.parseDouble(row[5].toString()));
				}
				if(row[6] != null) {
					item.setContent(row[6].toString().getBytes());
				}
				if(row[7] != null) {
					item.setTagName(row[7].toString());
				}
				cart.add(item);
			}
		}
		return cart;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean createCart(String user) {
		Transaction transaction = null;
		String sql = "";
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			
			sql = "DROP TABLE IF EXISTS shopping_cart_" + userSubstring;
			session.createSQLQuery(sql).executeUpdate();
			
			sql = "CREATE TABLE `shopping_cart_" + userSubstring + "`" + 
					" (`cart_id` int NOT NULL AUTO_INCREMENT," +
					" `isbn_13` varchar(13) NOT NULL DEFAULT '0000000000000'," + 
					" `title` varchar(100) DEFAULT NULL," + 
					" `author` varchar(80) DEFAULT NULL," + 
					" `publish_date` date DEFAULT NULL," + 
					" `price` decimal(6,2) DEFAULT NULL," + 
					" `content` longblob," + 
					" `tag_name` varchar(45) DEFAULT NULL," + 
					" PRIMARY KEY (`cart_id`), CONSTRAINT `fk_isbn_13_cart_" + userSubstring + "`" + 
					" FOREIGN KEY (`isbn_13`) REFERENCES `book` (`isbn_13`) ON DELETE CASCADE" + 
					" ON UPDATE CASCADE)";
			session.createSQLQuery(sql).executeUpdate();
			log.info("Shopping_Cart table has been created for \'" + user + "\'");

			// commit transaction
			transaction.commit();
		}
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Shopping_Cart table could not be created for \'" + user + "\'");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean addBook(String user, Book book) {
		Transaction transaction = null;
		String sql = "";
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			sql = "INSERT INTO shopping_cart_" + userSubstring + " (isbn_13, title, author, " + 
					"publish_date, price, content) VALUES ('" + book.getIsbn13() + "', '" + 
					book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getPublishDate() + "', " + 
					book.getPrice() + ", (SELECT content FROM book WHERE isbn_13 = " + book.getIsbn13() + 
					"))";
			session.createSQLQuery(sql).executeUpdate();
			
			sql = "UPDATE shopping_cart_" + userSubstring + " SET tag_name = (SELECT tag_name FROM tag " + 
					"WHERE isbn_13 = '" + book.getIsbn13() + "') WHERE isbn_13 = '" + book.getIsbn13() + 
					"'";
			session.createSQLQuery(sql).executeUpdate();
			log.info("\'" + book.getTitle() + "\' was added to cart");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("\'" + book.getTitle() + "\' could not be added");
			return false;
		}
		return true;	
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean deleteBook(String user, String isbn) {
		Transaction transaction = null;
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String sql = "DELETE FROM shopping_cart_" + userSubstring + " WHERE isbn_13 = " + isbn;
			session.createSQLQuery(sql).executeUpdate();
			log.info("Book \'" + isbn + "\' was removed from cart");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Book \'" + isbn + "\' could not be removed");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean clearCart(String user) {
		Transaction transaction = null;
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String sql = "TRUNCATE shopping_cart_" + userSubstring;
			session.createSQLQuery(sql).executeUpdate();
			log.info("Table \'shopping_cart_" + userSubstring + "' has been cleared");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.info("Table \'shopping_cart_" + userSubstring + "' could not be cleared");
			return false;
		}
		return true;
	}
	
}