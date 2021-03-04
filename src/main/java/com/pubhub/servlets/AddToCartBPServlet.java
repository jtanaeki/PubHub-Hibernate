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
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class AddToCartBPServlet
 */
@WebServlet("/AddToCart-BP")
public class AddToCartBPServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		String fullName = (String) request.getSession().getAttribute("userWelcome");
		String user = "";
		
		if(fullName == null) {
			request.getSession().setAttribute("message", "Login is required before adding books to cart");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
		else {
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
		
		
			if(isSuccess){
				request.getSession().setAttribute("message", "Book successfully added to cart");
				request.getSession().setAttribute("messageClass", "alert-success");
				response.sendRedirect("BookPublishing");
			}
			else {
				request.getSession().setAttribute("message", "There was a problem adding this book");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
			}
		}
	}

}
