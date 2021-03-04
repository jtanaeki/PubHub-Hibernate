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
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class ClearHistoryServlet
 */
@WebServlet("/ClearHistory")
public class ClearHistoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
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
			isSuccess = dao.clearHistory(user);
		}
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Order History successfully cleared");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("OrderHistory");
		}else {
			request.getSession().setAttribute("message", "There was a problem clearing the order history");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("viewOrderHistory.jsp").forward(request, response);
		}
	}

}
