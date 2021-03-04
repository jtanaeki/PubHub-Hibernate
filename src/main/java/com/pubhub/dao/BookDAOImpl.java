package com.pubhub.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.pubhub.model.Book;
import com.pubhub.model.Tag;
import com.pubhub.utilities.HibernateUtil;

/**
 * Implementation for the BookDAO, responsible for querying the database for Book objects.
 * (Refactored to use Hibernate for all database interaction)
 */
public class BookDAOImpl implements BookDAO {
	
	private static Logger log = Logger.getLogger(BookDAOImpl.class);
	
	@Override
	public List<Book> getAllBooks() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Book", Book.class).list();
		}
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public List<Book> getAllBooksWithTags() {
		List<Book> books = new ArrayList<>();
		List<Tag> tags = new ArrayList<>();
				
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			books = session.createQuery("from Book", Book.class).list();
			tags = session.createQuery("from Tag", Tag.class).list();
		}
		
		for(Tag tag: tags) {
			for(Book book: books) {
				if(tag.getIsbn13().equals(book.getIsbn13())) {
					// adds tag name to Book object for Book Publishing page
					book.setTagName(tag.getTagName());
				}
			}
		}
		return books;
	}
	
	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooksByTitle(String title) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Book WHERE title LIKE :title";
			Query<Book> query = session.createQuery(hql);
			query.setParameter("title", "%" + title + "%");
			return query.getResultList();
		}
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooksByAuthor(String author) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Book WHERE author LIKE :author";
			Query<Book> query = session.createQuery(hql);
			query.setParameter("author", "%" + author + "%");
			return query.getResultList();
		}
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooksLessThanPrice(double price) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Book WHERE price < :price";
			Query<Book> query = session.createQuery(hql);
			query.setParameter("price", price);
			return query.getResultList();
		}
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@SuppressWarnings("unchecked")
	@Override
	public Book getBookByISBN(String isbn) {
		List<Book> books = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Book WHERE isbn_13 = :isbn";
			Query<Book> query = session.createQuery(hql);
			query.setParameter("isbn", isbn);
			books = query.getResultList();
			
			if(books.isEmpty()) {
				return null;
			}
		}
		return books.get(0);
	}


	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public boolean addBook(Book book) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			
			// operation 1
			Object object = session.save(book);
			
			// operation 2
			session.get(Book.class, (Serializable) object);
			
			// commit transaction
			transaction.commit();
			
			log.info("\'" + book.getTitle() + "\' was added");
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

	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateBook(Book book) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String hql = "UPDATE Book SET title = :title, author = :author, price = :price " + 
					"WHERE isbn_13 = :isbn_13";
			Query<Book> query = session.createQuery(hql);
			query.setParameter("title", book.getTitle());
			query.setParameter("author", book.getAuthor());
			query.setParameter("price", book.getPrice());
			query.setParameter("isbn_13", book.getIsbn13());
			query.executeUpdate();
			log.info("Book \'" + book.getIsbn13() + "\' was updated");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Book \'" + book.getIsbn13() + "\' could not be updated");
			return false;
		}
		return true;
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteBookByISBN(String isbn) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String hql = "DELETE FROM Book WHERE isbn_13 = :isbn_13";
			Query<Book> query = session.createQuery(hql);
			query.setParameter("isbn_13", isbn);
			query.executeUpdate();
			log.info("Book \'" + isbn + "\' was removed");

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
	
}
