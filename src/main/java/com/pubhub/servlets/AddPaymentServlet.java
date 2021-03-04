package com.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddPaymentServlet
 */
@WebServlet("/AddPayment")
public class AddPaymentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String payName = request.getParameter("name");
		String payCard = request.getParameter("number");
		String payExpiration = request.getParameter("expiration-date");
		String payCVV = request.getParameter("cvv");
		String status = (String) request.getSession().getAttribute("paymentStatus");
		
		if(status == "done") {
			request.getSession().setAttribute("paymentStatus", null);
			request.getSession().setAttribute("paymentStatusScroll", "edit");
			
			String editName = (String) request.getSession().getAttribute("payNameVal");
			String editCard = (String) request.getSession().getAttribute("payCardVal");
			String editExpiration = (String) request.getSession().getAttribute("payExpirationVal");
			String editCVV = (String) request.getSession().getAttribute("payCVVVal");
			
			request.getSession().setAttribute("payNameVal", editName);
			request.getSession().setAttribute("payCardVal", editCard);
			request.getSession().setAttribute("payExpirationVal", editExpiration);
			request.getSession().setAttribute("payCVVVal", editCVV);
			
			request.getSession().setAttribute("messagePay", "Payment info can now be edited");
			request.getSession().setAttribute("messageClassPay", "alert-success");
		}
		else {
			request.getSession().setAttribute("paymentStatus", "done");
			request.getSession().setAttribute("paymentStatusScroll", "add");
			
			request.getSession().setAttribute("payNameVal", payName);
			request.getSession().setAttribute("payCardVal", payCard);
			request.getSession().setAttribute("payExpirationVal", payExpiration);
			request.getSession().setAttribute("payCVVVal", payCVV);
			
			request.getSession().setAttribute("messagePay", "Payment info has been added");
			request.getSession().setAttribute("messageClassPay", "alert-success");
		}
	
		response.sendRedirect("CheckoutCart");
	}

}
