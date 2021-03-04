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
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet("/ShoppingCart")
public class ShoppingCartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
			
			Shopping_CartDAO dao = HibernateUtil.getShopping_CartDAO();
			List<Shopping_Cart> cart = dao.getCart(user);

			request.getSession().setAttribute("books", cart);
		}
		
		request.getSession().setAttribute("prevPage", "ShoppingCart");
		
		request.getRequestDispatcher("viewCart.jsp").forward(request, response);
	}

}
