package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.BookDAO;
import com.pubhub.dao.Purchased_BookDAO;
import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.model.Book;
import com.pubhub.model.Purchased_Book;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/*
 * This servlet will take you to the Marketplace page
 */
@WebServlet("/Marketplace")
public class MarketplaceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullName = (String) request.getSession().getAttribute("userWelcome");
		String user = "";
		
		if(fullName != null) {
			UserDAO uDao = HibernateUtil_User.getUserDAO();
			List<User> users = uDao.getAllUsers();
			
			for(User userObj : users) {
				if(fullName.equals(userObj.getFullName())) {
					user = userObj.getUsername();
				}
			}
			
			// Grab the list of Purchased_Book objects from the Database
			Purchased_BookDAO pDao = HibernateUtil.getPurchased_BookDAO();
			List<Purchased_Book> pbList = pDao.getHistory(user);
			
			request.getSession().setAttribute("pbList", pbList);
			
			// Grab the list of Shopping_Cart objects from the Database
			Shopping_CartDAO sDao = HibernateUtil.getShopping_CartDAO();
			List<Shopping_Cart> cartList = sDao.getCart(user);
			
			request.getSession().setAttribute("cartList", cartList);
		}
		
		// Grab the list of Book objects from the Database
		BookDAO bDao = HibernateUtil.getBookDAO();
		List<Book> booksWithTags = bDao.getAllBooksWithTags();
		
		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", booksWithTags);
		
		request.getSession().setAttribute("prevPage", "Marketplace");
		
		request.getRequestDispatcher("bookList.jsp").forward(request, response);
	}
	
}
