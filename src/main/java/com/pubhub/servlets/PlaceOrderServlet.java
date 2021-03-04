package com.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.Purchased_BookDAO;
import com.pubhub.dao.Shopping_CartDAO;
import com.pubhub.dao.UserDAO;
import com.pubhub.model.Shopping_Cart;
import com.pubhub.model.User;
import com.pubhub.utilities.HibernateUtil;
import com.pubhub.utilities.HibernateUtil_User;

/**
 * Servlet implementation class PlaceOrderServlet
 */
@WebServlet("/PlaceOrder")
public class PlaceOrderServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("checkout.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String fullName = (String) request.getSession().getAttribute("userWelcome");
		String user = "";
		String statusShip = (String) request.getSession().getAttribute("shippingStatus");
		String statusPay = (String) request.getSession().getAttribute("paymentStatus");

		Shopping_CartDAO sDao = HibernateUtil.getShopping_CartDAO();
		Purchased_BookDAO pDao = HibernateUtil.getPurchased_BookDAO();
		
		List<Shopping_Cart> cart = new ArrayList<>();
		
		if(fullName != null && statusShip != null && statusPay != null) {
			UserDAO uDao = HibernateUtil_User.getUserDAO();
			List<User> users = uDao.getAllUsers();
			
			for(User userObj : users) {
				if(fullName.equals(userObj.getFullName())) {
					user = userObj.getUsername();
				}
			}
			
			cart = sDao.getCart(user);
			
			for(Shopping_Cart item : cart) {
				isSuccess = pDao.addBook(user, item);
			}
		}
		
		// Resets shipping fields
		request.getSession().setAttribute("shippingStatus", null);
		request.getSession().setAttribute("shippingStatusScroll", null);
		request.getSession().setAttribute("shipNameVal", null);
		request.getSession().setAttribute("shipStreetVal", null);
		request.getSession().setAttribute("shipCityVal", null);
		request.getSession().setAttribute("shipStateVal", null);
		request.getSession().setAttribute("shipZipCodeVal", null);
		
		// Resets payment fields
		request.getSession().setAttribute("paymentStatus", null);
		request.getSession().setAttribute("paymentStatusScroll", null);
		request.getSession().setAttribute("payNameVal", null);
		request.getSession().setAttribute("payCardVal", null);
		request.getSession().setAttribute("payExpirationVal", null);
		request.getSession().setAttribute("payCVVVal", null);
		
		if(isSuccess){
			sDao.clearCart(user);
			request.getSession().setAttribute("cartCount", 0);
			
			request.getSession().setAttribute("message", "Book(s) successfully purchased");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("OrderHistory");
		}else {
			request.getSession().setAttribute("message", "Make sure to fill out both Shipping//Billing and Payment sections");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("checkout.jsp").forward(request, response);
		}
	}

}
