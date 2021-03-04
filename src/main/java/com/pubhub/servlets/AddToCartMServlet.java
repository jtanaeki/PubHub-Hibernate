package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.BookDAO;
import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.model.Book;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class AddToCartMServlet
 */
@WebServlet("/AddToCart-M")
public class AddToCartMServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("bookList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		String fullName = (String) request.getSession().getAttribute("userWelcome");
		String user = "";
		
		UserDAO uDao = HibernateUtil_User.getUserDAO();
		List<User> users = uDao.getAllUsers();
		
		for(User userObj : users) {
			if(fullName.equals(userObj.getFullName())) {
				user = userObj.getUsername();
			}
		}

		BookDAO bDao = HibernateUtil.getBookDAO();
		Book book = bDao.getBookByISBN(isbn13);
		
		Shopping_CartDAO sDao = HibernateUtil.getShopping_CartDAO();
		isSuccess = sDao.addBook(user, book);

		List<Shopping_Cart> cart = sDao.getCart(user);
	
		if(isSuccess){
			// Keeps count of cart items for header
			request.getSession().setAttribute("cartCount", cart.size());
			
			request.getSession().setAttribute("message", "Book successfully added to cart");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("Marketplace");
		}
		else {
			request.getSession().setAttribute("message", "There was a problem adding this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookList.jsp").forward(request, response);
		}
	}

}
