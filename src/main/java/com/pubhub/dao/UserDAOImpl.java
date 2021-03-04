package com.pubhub.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Implementation for the UserDAO, responsible for querying the database for User objects.
 * (Refactored to use Hibernate for all database interaction)
 */
public class UserDAOImpl implements UserDAO{

	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	
	@Override
	public List<User> getAllUsers() {
		try (Session session = HibernateUtil_User.getSessionFactory().openSession()) {
			return session.createQuery("from User", User.class).list();
		}
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean authenticateUser(String user, String pass) {
		List<User> userData = null;
		try (Session session = HibernateUtil_User.getSessionFactory().openSession()) {
			String hql = "from User WHERE username = :username";
			Query<User> query = session.createQuery(hql);
			query.setParameter("username", user);
			userData = query.getResultList();
		}
		
		if(userData.isEmpty()) {
			log.error("\'" + user + "\' is not registered");
			return false;
		}
		
		if(userData.get(0).getPassword().equals(pass)) {
			log.info("Authentication was successful.");
		}
		else {
			log.error("Authentication was NOT successful.");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean addUser(String user, String pass, String name) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil_User.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			
			// operation 1
			Object object = session.save(new User(user, pass, name));
			
			// operation 2
			session.get(User.class, (Serializable) object);
			
			// commit transaction
			transaction.commit();
			
			log.info("\'" + name + "\' has been registered.");
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("\'" + name + "\' could not be registered.");
			return false;
		}
		return true;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean changePass(String user, String pass) {
		Transaction transaction = null;
		
		try (Session session = HibernateUtil_User.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			String hql = "UPDATE User SET password = :password WHERE username = :username";
			Query<User> query = session.createQuery(hql);
			query.setParameter("password", pass);
			query.setParameter("username", user);
			query.executeUpdate();
			log.info("Password has been reset for \'" + user + "\'");

			// commit transaction
			transaction.commit();
		} 
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			log.error("Password could not be reset for \'" + user + "\'");
			return false;
		}
		return true;
	}
	
}