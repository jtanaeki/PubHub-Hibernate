package com.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddShippingServlet
 */
@WebServlet("/AddShipping")
public class AddShippingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String shipName = request.getParameter("name");
		String shipStreet = request.getParameter("street");
		String shipCity = request.getParameter("city");
		String shipState = request.getParameter("state");
		String shipZipCode = request.getParameter("zip-code");
		String status = (String) request.getSession().getAttribute("shippingStatus");
		
		if(status == "done") {
			request.getSession().setAttribute("shippingStatus", null);
			request.getSession().setAttribute("shippingStatusScroll", "edit");
			
			String editName = (String) request.getSession().getAttribute("shipNameVal");
			String editStreet = (String) request.getSession().getAttribute("shipStreetVal");
			String editCity = (String) request.getSession().getAttribute("shipCityVal");
			String editState = (String) request.getSession().getAttribute("shipStateVal");
			String editZipCode = (String) request.getSession().getAttribute("shipZipCodeVal");
			
			request.getSession().setAttribute("shipNameVal", editName);
			request.getSession().setAttribute("shipStreetVal", editStreet);
			request.getSession().setAttribute("shipCityVal", editCity);
			request.getSession().setAttribute("shipStateVal", editState);
			request.getSession().setAttribute("shipZipCodeVal", editZipCode);
			
			request.getSession().setAttribute("messageShip", "Shipping/Billing info can now be edited");
			request.getSession().setAttribute("messageClassShip", "alert-success");
		}
		else {
			request.getSession().setAttribute("shippingStatus", "done");
			request.getSession().setAttribute("shippingStatusScroll", "add");
			
			request.getSession().setAttribute("shipNameVal", shipName);
			request.getSession().setAttribute("shipStreetVal", shipStreet);
			request.getSession().setAttribute("shipCityVal", shipCity);
			request.getSession().setAttribute("shipStateVal", shipState);
			request.getSession().setAttribute("shipZipCodeVal", shipZipCode);
			
			request.getSession().setAttribute("messageShip", "Shipping/Billing info has been added");
			request.getSession().setAttribute("messageClassShip", "alert-success");
		}
	
		response.sendRedirect("CheckoutCart");
	}

}
