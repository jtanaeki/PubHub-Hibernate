package com.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pubhub.dao.TagDAO;
import com.pubhub.model.Tag;
import com.pubhub.utilities.HibernateUtil;

/**
 * Servlet implementation class UpdateBookServlet
 */
@WebServlet("/UpdateTag")
public class UpdateTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;   

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		
		TagDAO dao = HibernateUtil.getTagDAO();
		Tag tag = dao.getTagByISBN(isbn13);
		
		if(tag != null){
			// The only fields we want to be updatable is the tag name. A new ISBN has to be applied for
			// And a new tag needs to be re-published.
			tag.setTagName(request.getParameter("tagName"));
			request.setAttribute("tag", tag);
			isSuccess = dao.updateTag(tag);
		}else {
			//ASSERT: couldn't find tag with isbn. Update failed.
			isSuccess = false;
		}
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Tag successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("BookPublishing");
		}else {
			request.getSession().setAttribute("message", "There was a problem updating this tag");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("tagDetails.jsp").forward(request, response);
		}
	}

}
