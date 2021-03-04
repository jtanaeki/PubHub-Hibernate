package com.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.BookDAO;

import com.pubhub.utilities.HibernateUtil;

/**
 * Servlet implementation class DeleteBookServlet
 */
@WebServlet("/DeleteBook")
public class DeleteBookServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");

		BookDAO dao = HibernateUtil.getBookDAO();
		isSuccess = dao.deleteBookByISBN(isbn13);
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Book successfully removed");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("BookPublishing");
		}else {
			request.getSession().setAttribute("message", "There was a problem removing this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
		}
	}

}
