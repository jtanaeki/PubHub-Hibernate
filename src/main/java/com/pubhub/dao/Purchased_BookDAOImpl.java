package com.pubhub.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pubhub.model.Book;
import com.pubhub.model.Purchased_Book;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.utilities.HibernateUtil;

/**
 * Implementation for the Purchased_BookDAO, responsible for querying the database for Purchased_Book objects.
 */
@SuppressWarnings("deprecation")
public class Purchased_BookDAOImpl implements Purchased_BookDAO{

	private static Logger log = Logger.getLogger(Purchased_BookDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Purchased_Book> getHistory(String user) {
		List<Purchased_Book> history = new ArrayList<>();
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String sql = "SELECT * from purchased_book_" + userSubstring;
			SQLQuery<Object[]> query = session.createSQLQuery(sql);
			List<Object[]> rows = query.list();
			for(Object[] row : rows) {
				Purchased_Book item = new Purchased_Book();
				item.setId(Integer.parseInt(row[0].toString()));		// set to NOT NULL in database
				if(row[1] != null) {
					item.setPurchaseDate(LocalDate.parse(row[1].toString()));
				}
				item.setIsbn13(row[2].toString());		// set to NOT NULL in database
				if(row[3] != null) {
					item.setTitle(row[3].toString());
				}
				if(row[4] != null) {
					item.setAuthor(row[4].toString());
				}
				if(row[5] != null) {
					item.setPublishDate(LocalDate.parse(row[5].toString()));
				}
				if(row[6] != null) {
					item.setPrice(Double.parseDouble(row[6].toString()));
				}
				if(row[7] != null) {
					item.setContent(row[7].toString().getBytes());
				}
				if(row[8] != null) {
					item.setTagName(row[8].toString());
				}
				history.add(item);
			}
		}
		return history;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean createHistory(String user) {
		Transaction transaction = null;
		String sql = "";
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			
			sql = "DROP TABLE IF EXISTS purchased_book_" + userSubstring;
			session.createSQLQuery(sql).executeUpdate();
			
			sql = "CREATE TABLE `purchased_book_" + userSubstring + "`" + 
					" (`pb_id` int NOT NULL AUTO_INCREMENT," + 
					" `purchase_date` date DEFAULT NULL," + 
					" `isbn_13` varchar(13) NOT NULL DEFAULT '0000000000000'," + 
					" `title` varchar(100) DEFAULT NULL," + 
					" `author` varchar(80) DEFAULT NULL," + 
					" `publish_date` date DEFAULT NULL," + 
					" `price` decimal(6,2) DEFAULT NULL," + 
					" `content` longblob," + 
					" `tag_name` varchar(45) DEFAULT NULL," + 
					" PRIMARY KEY (`pb_id`), CONSTRAINT `fk_isbn_13_pb_" + userSubstring + "`" + 
					" FOREIGN KEY (`isbn_13`) REFERENCES `book` (`isbn_13`) ON DELETE CASCADE" + 
					" ON UPDATE CASCADE)";
			session.createSQLQuery(sql).executeUpdate();
			log.info("Purchased_Book table has been created for \'" + user + "\'");

			// commit transaction
			transaction.commit();
		}
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Purchased_Book table could not be created for \'" + user + "\'");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean addBook(String user, Shopping_Cart item) {
		Transaction transaction = null;
		String sql = "";
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			sql = "INSERT INTO purchased_book_" + userSubstring + " (isbn_13, " + 
					"title, author, publish_date, price, content, tag_name) VALUES ('" + item.getIsbn13() + 
					"', '" + item.getTitle() + "', '" + item.getAuthor() + "', '" + item.getPublishDate() + 
					"', " + item.getPrice() + ", (SELECT content FROM book WHERE isbn_13 = " + 
					item.getIsbn13() + "), '" + item.getTagName() + "')";
			session.createSQLQuery(sql).executeUpdate();
			
			sql = "UPDATE purchased_book_" + userSubstring + " SET tag_name = (SELECT tag_name FROM tag " + 
					"WHERE isbn_13 = '" + item.getIsbn13() + "') WHERE isbn_13 = '" + item.getIsbn13() + 
					"'";
			session.createSQLQuery(sql).executeUpdate();
			log.info("\'" + item.getTitle() + "\' was added to order history");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.info("\'" + item.getTitle() + "\' could not be added");
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

			String sql = "DELETE FROM purchased_book_" + userSubstring + " WHERE isbn_13 = " + isbn;
			session.createSQLQuery(sql).executeUpdate();
			log.info("Book \'" + isbn + "\' was removed from order history");

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
	public boolean clearHistory(String user) {
		Transaction transaction = null;
		String userSubstring = user.substring( 0, user.indexOf("@"));
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String sql = "TRUNCATE purchased_book_" + userSubstring;
			session.createSQLQuery(sql).executeUpdate();
			log.info("Table \'purchased_book_" + userSubstring + "' has been cleared");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.info("Table \'purchased_book_" + userSubstring + "' could not be cleared");
			return false;
		}
		return true;
	}
	
}
