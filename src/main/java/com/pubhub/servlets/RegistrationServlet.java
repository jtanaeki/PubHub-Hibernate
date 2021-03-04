package com.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.Purchased_BookDAO;
import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/Registration")
public class RegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("registration.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess = false;
		String name = request.getParameter("inputFullName");
		String user = request.getParameter("inputUserame");
		String pass = request.getParameter("inputPassword");
		String confirmPass = request.getParameter("inputConfirmPassword");
		
		if(user != null && pass != null && confirmPass != null){
			if(user.length() < 8) {
				request.getSession().setAttribute("message", "Username must be at least 8 characters long and unique");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("registration.jsp").forward(request, response);
			}
			else {
				int count = 0;
				for (char c : pass.toCharArray()) {
		            if (Character.isDigit(c)) {
		            	count++;
		            }
		        }
				if(pass.length() < 8 || count < 2) {
					request.getSession().setAttribute("message", "Password must be at least 8 characters long and include 2 numbers");
					request.getSession().setAttribute("messageClass", "alert-danger");
					request.getRequestDispatcher("registration.jsp").forward(request, response);
				}
				else {
					if(!(pass.equals(confirmPass))) {
						request.getSession().setAttribute("message", "Please make sure passwords match");
						request.getSession().setAttribute("messageClass", "alert-danger");
						request.getRequestDispatcher("registration.jsp").forward(request, response);
					}
					else {
						UserDAO uDao = HibernateUtil_User.getUserDAO();
						if(!(isSuccess = uDao.addUser(user, pass, name))) {		
							request.getSession().setAttribute("message", "Please try choosing a different username");
							request.getSession().setAttribute("messageClass", "alert-danger");
							request.getRequestDispatcher("registration.jsp").forward(request, response);
						}
						
					}
				}
			}			
		}	
		else {
			isSuccess = false;
		}

		Shopping_CartDAO sDao = HibernateUtil.getShopping_CartDAO();
		Purchased_BookDAO pDao = HibernateUtil.getPurchased_BookDAO();
		if(isSuccess){
			sDao.createCart(user);
			pDao.createHistory(user);
			request.getSession().setAttribute("userWelcome", name);
			request.getSession().setAttribute("message", "\'" + name + "\' successfully registered");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("Home");
		}else {
			request.getSession().setAttribute("message", "There was a problem adding this user");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("registration.jsp").forward(request, response);
		}
	}

}
