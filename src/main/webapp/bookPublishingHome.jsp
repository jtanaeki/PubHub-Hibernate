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
		
		<h1>PUBHUB <small>Book Publishing</small></h1>
		<hr class="book-primary">
		
		<table class="table table-striped table-hover table-responsive pubhub-datatable">
			<thead>
				<tr>
					<td>ISBN-13:</td>
					<td>Title:</td>
					<td>Author:</td>
					<td>Publish Date:</td>
					<td>Price:</td>
					<td>Tag Name:</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="book" items="${books}">
					<input class="tag-name" type="hidden" value="${book.tagName}">
					<tr class="book-row">
						<td><c:out value="${book.isbn13}" /></td>
						
						<td><c:out value="${book.title}" /></td>
						<td><c:out value="${book.author}" /></td>
						<td><c:out value="${book.publishDate}" /></td>
						<td><fmt:formatNumber value="${book.price }" type="CURRENCY"/></td>
						<td class="tag-td"><c:out value="${book.tagName}" /></td>						
						<td><form action="DownloadBook" method="get">
								<input type="hidden" name="isbn13" value="${book.isbn13}">
								<button class="btn btn-success">Download</button>
							</form></td>					
						<td><form action="ViewBookDetails?isbn=${book.isbn13}" method="get">
								<input type="hidden" name="isbn13" value="${book.isbn13}">
								<button class="btn btn-primary">Update Book</button>
							</form></td>
						<td><form action="ViewTagDetails?isbn=${book.isbn13}" method="get">
								<input type="hidden" name="isbn13" value="${book.isbn13}">
								<button class="btn btn-secondary">Add/Update Tag</button>
							</form></td>
						<td><form action="DeleteBook" method="post">
								<input type="hidden" name="isbn13" value="${book.isbn13}">
								<button class="btn btn-danger">Remove</button>
							</form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	  </div>
	</header>
	<script>
		var bookRow = document.getElementsByClassName("book-row");
		var tagName = document.getElementsByClassName("tag-name");
		var tagTD = document.getElementsByClassName("tag-td");
		
		var i;
		
		for (i = 0; i < bookRow.length; i++) {
			if(tagName[i].value == "") {
				tagTD[i].innerHTML = "<em>(no tag)</em>";
			}
		}
	</script>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />