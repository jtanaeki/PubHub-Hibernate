package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.TagDAO;
import com.pubhub.model.Book;
import com.pubhub.utilities.HibernateUtil;

/*
 * This servlet will take you to the page with list of books based on tag search results.
 */
@WebServlet("/BookWithTag")
public class BookWithTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tName = request.getParameter("tagName");
		
		TagDAO dao = HibernateUtil.getTagDAO();
		List<Book> bookList = dao.getBooksByTagName(tName);
		
		request.getSession().setAttribute("books", bookList);
		
		request.getRequestDispatcher("bookWithTag.jsp").forward(request, response);		
	}
	
}
