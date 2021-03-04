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
 * Implementation for the TagDAO, responsible for querying the database for Tag objects.
 * (Refactored to use Hibernate for all database interaction)
 */
public class TagDAOImpl implements TagDAO {
	
	private static Logger log = Logger.getLogger(TagDAOImpl.class);
	
	@Override
	public List<Tag> getAllTags() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Tag", Tag.class).list();
		}
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getTagsByTagName(String tagName) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Tag WHERE tag_name LIKE :tagName";
			Query<Tag> query = session.createQuery(hql);
			query.setParameter("tagName", "%" + tagName + "%");
			return query.getResultList();
		}
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getTagsByBook(Book book) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Tag WHERE isbn_13 = :isbn";
			Query<Tag> query = session.createQuery(hql);
			query.setParameter("isbn", book.getIsbn13());
			return query.getResultList();
		}
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooksByTagName(String tagName) {
		List<Tag> tags = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		List<Book> booksWithTag = new ArrayList<>();
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Tag WHERE tag_name LIKE :tagName";
			Query<Tag> query = session.createQuery(hql);
			query.setParameter("tagName", "%" + tagName + "%");
			tags = query.getResultList();
		}
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			books = session.createQuery("from Book", Book.class).list();
		}
		
		for(Tag tag : tags) {
			for(Book book : books) {
				if(tag.getIsbn13().equals(book.getIsbn13())) {
					booksWithTag.add(book);
				}
			}
		}
		return booksWithTag;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public Tag getTagByISBN(String isbn) {
		List<Tag> tags = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "from Tag WHERE isbn_13 = :isbn";
			Query<Tag> query = session.createQuery(hql);
			query.setParameter("isbn", isbn);
			tags = query.getResultList();
			
			if(tags.isEmpty()) {
				return null;
			}
		}
		return tags.get(0);
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean addTag(String tagName, Book book) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			
			// operation 1
			Object object = session.save(new Tag(book.getIsbn13(), tagName));
			
			// operation 2
			session.get(Tag.class, (Serializable) object);
			
			// commit transaction
			transaction.commit();
			
			log.info("\'" + tagName + "\' was added");
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("\'" + tagName + "\' could not added");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateTag(Tag tag) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String hql = "UPDATE Tag SET tag_name = :tagName WHERE isbn_13 = :isbn_13";
			Query<Tag> query = session.createQuery(hql);
			query.setParameter("tagName", tag.getTagName());
			query.setParameter("isbn_13", tag.getIsbn13());
			query.executeUpdate();
			log.info("Updated to \'" + tag.getTagName() + "\'");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Could not update to \'" + tag.getTagName() + "\'");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteTagByISBN(String isbn) {
		List<Tag> tags = new ArrayList<>();
		boolean tagFound = false;
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tags =  session.createQuery("from Tag", Tag.class).list();
		}
		
		for(Tag tag : tags) {
			if(tag.getIsbn13().equals(isbn)) {
				tagFound = true;
			}
		}
		if(!tagFound) {
			log.error("Tag \'" + isbn + "\' was not found");
			return false;
		}
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String hql = "DELETE FROM Tag WHERE isbn_13 = :isbn_13";
			Query<Tag> query = session.createQuery(hql);
			query.setParameter("isbn_13", isbn);
			query.executeUpdate();
			log.info("Tag \'" + isbn + "\' was removed");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Tag \'" + isbn + "\' could not be removed");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteTagByName(String tagName, Book book) {
		List<Tag> tags = new ArrayList<>();
		boolean tagFound = false, bookFound = false;
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tags =  session.createQuery("from Tag", Tag.class).list();
		}
		
		for(Tag t : tags) {
			if(t.getTagName().equals(tagName)) {
				tagFound = true;
				if(t.getIsbn13().equals(book.getIsbn13())) {
					bookFound = true;
				}
			}
		}
		if(!tagFound) {
			log.error("Tag \'" + tagName + "\' was not found");
			return false;
		}
		if(!bookFound) {
			log.error("Tag \'" + tagName + "\' with Book \'" + book.getIsbn13() + "\' was not found");
			return false;
		}
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String hql = "DELETE FROM Tag WHERE tag_name = :tagName AND isbn_13 = :isbn";
			Query<Tag> query = session.createQuery(hql);
			query.setParameter("tagName", tagName);
			query.setParameter("isbn", book.getIsbn13());
			query.executeUpdate();
			log.info("Tag \'" + tagName + "\' was removed from \'" + book.getTitle() + "\'");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Tag \'" + tagName + "\' could not be removed from \'" + book.getTitle() + "\'");
			return false;
		}
		return true;
	}

}
