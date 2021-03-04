package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.BookDAO;
import com.pubhub.model.Book;
import com.pubhub.utilities.HibernateUtil;

/*
 * This servlet will take you to the Book Publishing page
 */
@WebServlet("/BookPublishing")
public class BookPublishingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Grab the list of Books from the Database
		BookDAO dao = HibernateUtil.getBookDAO();
		List<Book> booksWithTags = dao.getAllBooksWithTags();

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", booksWithTags);
		
		request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
	}
	
}
