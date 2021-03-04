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
	
		<p id="disable-download-msg"></p>
		
		<h1>PUBHUB <small>Shopping Cart</small></h1>
		<hr class="book-primary">
		
		<c:choose>
			<c:when test="${userWelcome == undefined}">
			<c:url var="adminURL" value="Login" />
			<div class="card">
				<h3>Login Required</h3>
				<p>
					You must be logged in to view cart. Sign in <a href="${adminURL}"><u>here</u></a>.
				</p>
			</div>
			</c:when>
			
			<c:otherwise>				
				<table class="table table-striped table-hover table-responsive">
					<thead>
						<tr>
							<td>ISBN-13:</td>
							<td>Title:</td>
							<td>Author:</td>
							<td>Publish Date:</td>
							<td>Price:</td>
							<td>Tag Name:</td>
							<td><form action="ClearCart" method="post">
									<button id="clearCart" class="btn btn-danger">
										<sup>Clear Cart</sup>
									</button>
								</form></td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${books == '[]'}">
							<tr>
								<td colspan="7" style="font-size: 20px"><em>(EMPTY)</em></td>
							</tr>
						</c:if>
						
						<c:forEach var="book" items="${books}">
						
							<input class="tag-name" type="hidden" value="${book.tagName}">
							
							<tr class="book-row">
								<td><c:out value="${book.isbn13}" /></td>
								
								<td><c:out value="${book.title}" /></td>
								<td><c:out value="${book.author}" /></td>
								<td><c:out value="${book.publishDate}" /></td>
								<td class="price-td"><fmt:formatNumber value="${book.price }" type="CURRENCY"/></td>
								<td class="tag-td"><c:out value="${book.tagName}" /></td>
								<td><form action="DeleteFromCart" method="post">
										<input type="hidden" name="isbn13" value="${book.isbn13}">
										<button class="btn btn-danger">Remove</button>
									</form></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td style="float: right; font-size: 1.25em">Total:</td>
							<td id="total" style="font-size: 1.25em"></td>
							<td></td>
							<td><form action="CheckoutCart" method="get">
									<button id="checkout-btn" class="btn btn-primary">
										Proceed to Checkout
									</button>
								</form></td>
						</tr>
					</tfoot>
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
		#clearCart {
			height: 15px;
			padding-top: 22px;
			font-size: 20px;
		}
	</style>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script>
		var bookRow = document.getElementsByClassName("book-row");
		var tagName = document.getElementsByClassName("tag-name");
		var tagTD = document.getElementsByClassName("tag-td");
		var priceTD = document.getElementsByClassName("price-td");
		var total = document.getElementById("total");
		var checkoutBtn = document.getElementById("checkout-btn");
		var disableDownloadMsg = document.getElementById("disable-download-msg");
		
		var price = "";
		var num = 0.0;
		var sum = 0.0;
		
		var i, j;

		
		for (i = 0; i < bookRow.length; i++) {		
//	 		Labels books with no tag and/or no book content
			if(tagName[i].value == "") {
				tagTD[i].innerHTML = "<em>(no tag)</em>";
			}
			
// 			Calculates total price
			price = priceTD[i].innerHTML;
			num = Number(price.replace(/[^0-9.-]+/g, "")); //Removes currensy symbol and converts to number
			sum += num;
		}
		total.innerHTML = "$" + sum.toFixed(2);
		
		if(bookRow.length == 0){
// 			Disables button
			checkoutBtn.style = "color: #DFDFDF; background: #586776";
			checkoutBtn.className = "btn";
			checkoutBtn.style.cursor = "not-allowed";
			checkoutBtn.addEventListener("click", function(event) {
				event.preventDefault();
			});
			
// 			Shows message on hover
			checkoutBtn.onmouseover = function() {
				$("#disable-download-msg").fadeIn();
				disableDownloadMsg.innerHTML = "There are no items in shopping cart";
				disableDownloadMsg.style = "color: white; background: #FF5747; " + 
					"height: 50px; padding-top: 10px";
			};
			checkoutBtn.onmouseout = function() {
				$("#disable-download-msg").fadeOut();
			};
		}
	</script>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />