package com.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.TagDAO;
import com.pubhub.utilities.HibernateUtil;

/**
 * Servlet implementation class DeleteTagServlet
 */
@WebServlet("/DeleteTag")
public class DeleteTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");

		TagDAO dao = HibernateUtil.getTagDAO();
		isSuccess = dao.deleteTagByISBN(isbn13);
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Tag successfully removed");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("AllTags");
		}else {
			request.getSession().setAttribute("message", "There was a problem removing this tag");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("viewAllTags.jsp").forward(request, response);
		}
	}

}
