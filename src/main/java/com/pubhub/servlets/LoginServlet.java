package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.Purchased_BookDAO;
import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.model.Purchased_Book;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("userWelcome", null);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess = false;
		String user = request.getParameter("inputEmail");
		String pass = request.getParameter("inputPassword");
		String name = "";
		String prevPage = (String) request.getSession().getAttribute("prevPage");
		
		UserDAO uDao = HibernateUtil_User.getUserDAO();
		isSuccess = uDao.authenticateUser(user, pass);
		
		List<User> userList = uDao.getAllUsers();
		for(User userObj : userList) {
			if(userObj.getUsername().equals(user)) {
				name = userObj.getFullName();
			}
		}
		
		Shopping_CartDAO sDao = HibernateUtil.getShopping_CartDAO();
		List<Shopping_Cart> cart = sDao.getCart(user);
		
		if(prevPage == null) {
			prevPage = "Home";
		}
		
		if(isSuccess) {
			request.getSession().setAttribute("userWelcome", name);
			request.getSession().setAttribute("cartCount", cart.size());
			
			request.getSession().setAttribute("message", "\'" + name + "\' successfully logged in");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect(prevPage);
		}
		else {
			request.getSession().setAttribute("message", "Invalid username and/or password. Please try again.");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
}
