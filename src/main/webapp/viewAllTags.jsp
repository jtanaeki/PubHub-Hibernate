	<!-- Header -->
	<jsp:include page="header.jsp" />
	
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<!-- 	Just some stuff you need -->
	<header>
	  <div class="container">
	  
	<c:choose>
	<c:when test="${not empty message }">
	  <p class="alert ${messageClass}">${message }</p>
	<%
	  session.setAttribute("message", null);
	  session.setAttribute("messageClass", null);
	%>
	</c:when>
	</c:choose>
	
		<%
		  session.setAttribute("prevPage", null);
		%>
	
		<h1>PUBHUB <small>All Tags</small></h1>
		<hr class="book-primary">

		<table class="table table-striped table-hover table-responsive pubhub-datatable">
			<thead>
				<tr>
					<td>ISBN-13:</td>
					<td>Tag Name:</td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tag" items="${tags}">
					<tr>
						<td><c:out value="${tag.isbn13}" /></td>
						
						<td><c:out value="${tag.tagName}" /></td>
						<td><form action="ViewTagDetails?isbn=${tag.isbn13}" method="get">
								<input type="hidden" name="isbn13" value="${tag.isbn13}">
								<button class="btn btn-secondary">Update Tag</button>
							</form></td>
						<td><form action="DeleteTag" method="post">
								<input type="hidden" name="isbn13" value="${tag.isbn13}">
								<button class="btn btn-danger">Remove</button>
							</form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	  </div>
	</header>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />