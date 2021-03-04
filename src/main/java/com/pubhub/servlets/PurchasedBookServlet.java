package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.Purchased_BookDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.model.Purchased_Book;
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class PurchasedBookServlet
 */
@WebServlet("/OrderHistory")
public class PurchasedBookServlet extends HttpServlet {
	
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
		
			Purchased_BookDAO dao = HibernateUtil.getPurchased_BookDAO();
			List<Purchased_Book> history = dao.getHistory(user);
	
			request.getSession().setAttribute("books", history);
		}
		
		request.getSession().setAttribute("prevPage", "OrderHistory");
		
		request.getRequestDispatcher("viewOrderHistory.jsp").forward(request, response);
	}

}
