package com.pubhub.utilities;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.pubhub.dao.BookDAO;
import com.pubhub.dao.BookDAOImpl;
import com.pubhub.dao.Purchased_BookDAO;
import com.pubhub.dao.Purchased_BookDAOImpl;
import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.Shopping_CartDAOImpl;
import com.pubhub.dao.TagDAO;
import com.pubhub.dao.TagDAOImpl;
import com.pubhub.model.Book;
import com.pubhub.model.Purchased_Book;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.model.Tag;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/pubhub");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "password");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.FORMAT_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				configuration.setProperties(settings);
				
				configuration.addAnnotatedClass(Book.class);
				configuration.addAnnotatedClass(Tag.class);
				configuration.addAnnotatedClass(Shopping_Cart.class);
				configuration.addAnnotatedClass(Purchased_Book.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
	
	public static BookDAO getBookDAO() {
		return new BookDAOImpl();
	}
	
	public static TagDAO getTagDAO() {
		return new TagDAOImpl();
	}
	
	public static Shopping_CartDAO getShopping_CartDAO() {
		return new Shopping_CartDAOImpl();
	}
	
	public static Purchased_BookDAO getPurchased_BookDAO() {
		return new Purchased_BookDAOImpl();
	}
	
}
