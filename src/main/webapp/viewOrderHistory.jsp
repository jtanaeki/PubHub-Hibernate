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
		
		<h1>PUBHUB <small>Order History</small></h1>
		<hr class="book-primary">
		
		<c:choose>
			<c:when test="${userWelcome == undefined}">
			<c:url var="adminURL" value="Login" />
			<div class="card">
				<h3>Login Required</h3>
				<p>
					You must be logged in to view order history. Sign in <a href="${adminURL}"><u>here</u></a>.
				</p>
			</div>
			</c:when>
			
			<c:otherwise>
				<table class="table table-striped table-hover table-responsive pubhub-datatable">
					<thead>
						<tr>
							<td>Order ID:</td>
							<td>Purchase Date:</td>
							<td>ISBN-13:</td>
							<td>Title:</td>
							<td>Author:</td>
							<td>Publish Date:</td>
							<td>Price:</td>
							<td>Tag Name:</td>
							<td></td>
							<td><form action="ClearHistory" method="post">
									<button id="clearHistory" class="btn btn-danger">
										<sup>Clear History</sup>
									</button>
								</form></td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${books == '[]'}">
							<tr>
								<td colspan="10" style="font-size: 20px"><em>(EMPTY)</em></td>
							</tr>
						</c:if>
						
						<c:forEach var="book" items="${books}">
							<input class="tag-name" type="hidden" value="${book.tagName}">
							<tr class="book-row">
								<td><c:out value="${book.id}" /></td>
								<td><c:out value="${book.purchaseDate}" /></td>
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
								<td><form action="DeleteFromHistory" method="post">
										<input type="hidden" name="isbn13" value="${book.isbn13}">
										<button class="btn btn-danger">Remove</button>
									</form></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>

	  </div>
	</header>
	<style>
		h3 {
			color: #e83333;
		}
		.card {
			background: #2c3e50;
			color: white;
			margin: 10px 10px;
			padding-top: 15px;
		  	height: 150px;
		  	border: 0;
			border-radius: 1rem;
		}
		#clearHistory {
			height: 15px;
			padding-top: 22px;
			font-size: 20px;
		}
	</style>
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