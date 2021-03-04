package com.pubhub.test;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;

import com.pubhub.dao.BookDAO;
import com.pubhub.dao.Purchased_BookDAO;
import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.TagDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.model.Book;
import com.pubhub.model.Tag;
import com.pubhub.model.User;
import com.pubhub.model.Purchased_Book;
import com.pubhub.model.Shopping_Cart;

import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

public class TestDAO {
	
	private static Logger log = Logger.getLogger(TestDAO.class);

	public static void main(String[] args) {
		BookDAO bDao = HibernateUtil.getBookDAO();
		TagDAO tDao = HibernateUtil.getTagDAO();
		UserDAO uDao = HibernateUtil_User.getUserDAO();
		Shopping_CartDAO sDao = HibernateUtil.getShopping_CartDAO();
		Purchased_BookDAO pDao = HibernateUtil.getPurchased_BookDAO();

//	    log.info(bDao.getAllBooks());
//	    log.info(bDao.getAllBooksWithTags());
//	    log.info(bDao.getBooksByTitle("v"));
//	    log.info(bDao.getBooksByAuthor("bar"));
//	    log.info(bDao.getBooksLessThanPrice(200.50));
//	    log.info(bDao.getBookByISBN("1111"));
//		log.info(bDao.addBook(new Book("1111", "PT", "PA", 413.05, null)));
//		log.info(bDao.updateBook(new Book("1111", "updateT", "updateA", 41.30, null)));
//		log.info(bDao.deleteBookByISBN("1111"));

//		log.info(tDao.getAllTags());
//		log.info(tDao.getTagsByTagName("fic"));
//		log.info(tDao.addTag("Adventure", new Book("1111", "PT", "PA", 413.05, null)));
//		log.info(tDao.getTagsByBook(new Book("1111", "PT", "PA", 413.05, null)));
//		log.info(tDao.getBooksByTagName("adv"));
//		log.info(tDao.getTagByISBN("1111111111111"));
//		log.info(tDao.updateTag(new Tag("1111", "Action")));
//		log.info(tDao.deleteTagByISBN("1111"));
//		log.info(tDao.deleteTagByName("Action", new Book("1111", "PT", "PA", 413.05, null)));
		
//		log.info(uDao.getAllUsers());
//		log.info(uDao.authenticateUser("firstlast@mail.com", "pass"));
//		log.info(uDao.addUser("firstlast@mail.com", "pass"));
//		log.info(uDao.changePass("firstlast@mail.com", "pass1"));
	    
//	    log.info(sDao.getCart("firstlast@mail.com"));
//	    log.info(sDao.createCart("firstlast@mail.com"));
//	    log.info(sDao.addBook("firstlast@mail.com", new Book("1111", "PT", "PA", 413.05, null)));
//	    log.info(sDao.deleteBook("firstlast@mail.com", "1111"));
//		log.info(sDao.clearCart("firstlast@mail.com"));
	    
//	    log.info(pDao.getHistory("firstlast@mail.com"));
//	    log.info(pDao.createHistory("firstlast@mail.com"));
//	    log.info(pDao.addBook("firstlast@mail.com", new Shopping_Cart("1111", "PT", "PA", 413.05, null)));
//	    log.info(pDao.deleteBook("firstlast@mail.com", "1111"));
//		log.info(pDao.clearHistory("firstlast@mail.com"));
	}

}
