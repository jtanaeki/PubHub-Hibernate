package com.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.TagDAO;
import com.pubhub.model.Tag;
import com.pubhub.utilities.HibernateUtil;

/*
 * This servlet will take you to the page that views all tags.
 */
@WebServlet("/AllTags")
public class AllTagsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Grab the list of Tags from the Database
		TagDAO dao = HibernateUtil.getTagDAO();
		List<Tag> tagList = dao.getAllTags();

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("tags", tagList);
		
		request.getRequestDispatcher("viewAllTags.jsp").forward(request, response);
	}
	
}
