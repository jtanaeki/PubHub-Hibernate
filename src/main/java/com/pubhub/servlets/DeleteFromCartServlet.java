package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class DeleteFromCartServlet
 */
@WebServlet("/DeleteFromCart")
public class DeleteFromCartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		String fullName = (String) request.getSession().getAttribute("userWelcome");
		String user = "";
		
		Shopping_CartDAO dao = HibernateUtil.getShopping_CartDAO();
		
		if(fullName != null) {
			UserDAO uDao = HibernateUtil_User.getUserDAO();
			List<User> users = uDao.getAllUsers();
			
			for(User userObj : users) {
				if(fullName.equals(userObj.getFullName())) {
					user = userObj.getUsername();
				}
			}

			isSuccess = dao.deleteBook(user, isbn13);
		}
		
		List<Shopping_Cart> cart = dao.getCart(user);
		
		if(isSuccess){
			// Keeps count of cart items for header
			request.getSession().setAttribute("cartCount", cart.size());
			
			request.getSession().setAttribute("message", "Book successfully removed from cart");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ShoppingCart");
		}else {
			request.getSession().setAttribute("message", "There was a problem removing this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("viewCart.jsp").forward(request, response);
		}
	}

}
