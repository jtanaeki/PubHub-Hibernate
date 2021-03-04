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
		
		<h1>PUBHUB <small>Marketplace</small></h1>
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
				</tr>
			</thead>
			<tbody>
			
				<c:forEach var="pb" items="${pbList}">
		   			<input class="pb-list" type="hidden" value="${pb.isbn13}" />
				</c:forEach>
				
				<c:forEach var="cart" items="${cartList}">
		   			<input class="cart-list" type="hidden" value="${cart.isbn13}" />
				</c:forEach>
				
				<c:forEach var="book" items="${books}">
				
					<input class="tag-name" type="hidden" value="${book.tagName}">
					
					<tr>
						<td class="book-isbn"><c:out value="${book.isbn13}" /></td>
						
						<td><c:out value="${book.title}" /></td>
						<td><c:out value="${book.author}" /></td>
						<td><c:out value="${book.publishDate}" /></td>
						<td><fmt:formatNumber value="${book.price }" type="CURRENCY"/></td>
						<td class="tag-td"><c:out value="${book.tagName}" /></td>
						<td><form action="DownloadBook" method="get">
								<input class="download-isbn" type="hidden" name="isbn13" value="${book.isbn13}">
								<button class="btn btn-success download-btn">Download</button>
							</form></td>
						<td class="cart-td"><form action="AddToCart-M" method="post">
								<input class="cart-isbn" type="hidden" name="isbn13" value="${book.isbn13}">
								<button type="submit" class="btn btn-warning cart-btn">Add to Cart</button>
							</form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	  </div>
	</header>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script>
		var disableDownloadMsg = document.getElementById("disable-download-msg");
		var bookIsbn = document.getElementsByClassName("book-isbn");
		var tagName = document.getElementsByClassName("tag-name");
		var tagTD = document.getElementsByClassName("tag-td");
		var userWelcome = document.getElementById("user");
		var downloadIsbn = document.getElementsByClassName("download-isbn");
		var downloadBtn = document.getElementsByClassName("download-btn");
		var cartIsbn = document.getElementsByClassName("cart-isbn");
		var cartBtn = document.getElementsByClassName("cart-btn");
		var cartList = document.getElementsByClassName("cart-list");
		var cartTD = document.getElementsByClassName("cart-td");
		var pbList = document.getElementsByClassName("pb-list");

		var disableMsg;
		var cartBtnArr = [];
		var downloadBtnArr = [];
		var purchased = [];
		
		var i, j;
		

// 		Disables buttons
		function disableBtn(btnArr, index, btnColor, btnClass) {
			btnArr[index].style = "color: white; background: " + btnColor;
			btnArr[index].className = "btn " + btnClass + "-btn";
			btnArr[index].style.cursor = "not-allowed";
			btnArr[index].addEventListener("click", function(event) {
				event.preventDefault();
			});
		}
		
// 		Shows message for disabled buttons
		function showMsgOnHover(btnArr, index, msg) {
			btnArr[index].onmouseover = function() {
				$("#disable-download-msg").fadeIn();
				disableDownloadMsg.innerHTML = msg;
				disableDownloadMsg.style = "color: white; background: #FF5747; " + 
					"height: 50px; padding-top: 10px";
			};
			btnArr[index].onmouseout = function() {
				$("#disable-download-msg").fadeOut();
			};
		}
		
		
// 		Labels books with no tag
		for (i = 0; i < bookIsbn.length; i++) {
			if(tagName[i].value == "") {
				tagTD[i].innerHTML = "<em>(no tag)</em>";
			}
		}
		
		
// 		Disables all buttons if user is not signed in
		if(userWelcome.innerHTML == "") {
// 			Disables download buttons
			for (i = 0; i < downloadIsbn.length; i++) {
				disableBtn(downloadBtn, i, "#80CCBC", "download");

				disableMsg = "Login is required to download";
				showMsgOnHover(downloadBtn, i, disableMsg);
			}
			
// 			Disables add to cart buttons
			for (i = 0; i < cartIsbn.length; i++) {				
				disableBtn(cartBtn, i, "#F5CA6A", "cart");
				
				disableMsg = "Login is required to add to cart";
				showMsgOnHover(cartBtn, i, disableMsg);
			}
		}
		else {
// 			Creates array for books added to cart
			for (i = 0; i < cartList.length; i++) {
				for (j = 0; j < cartIsbn.length; j++) {
					if(cartList[i].value == cartIsbn[j].value) {
						cartBtnArr.push(j);
					}
				}
			}
			
// 			Labels books that have been added to cart
			for (i = 0; i < bookIsbn.length; i++) {
				if(cartBtnArr.includes(i)) {
					cartTD[i].innerHTML = "<em>ADDED TO CART</em>";
				}
			}
			
			
// 			Creates array of download buttons for purchased books
			for (i = 0; i < pbList.length; i++) {
				for (j = 0; j < downloadIsbn.length; j++) {
					if(pbList[i].value == downloadIsbn[j].value) {
						downloadBtnArr.push(j);
					}
				}
			}
			
			
// 			Creates array for all purchased books
			for (i = 0; i < pbList.length; i++) {
				for (j = 0; j < cartIsbn.length; j++) {
					if(pbList[i].value == cartIsbn[j].value) {
						purchased.push(j);
					}
				}
			}
			
			
// 			Disables add to cart and download buttons
			for (i = 0; i < bookIsbn.length; i++) {
//				Disables add to cart buttons with books that have been purchased
				if(purchased.includes(i)) {
					disableBtn(cartBtn, i, "#F5CA6A", "cart");
					
					disableMsg = "Book has already been purchased";
					showMsgOnHover(cartBtn, i, disableMsg);
				}
				
// 				Disables download buttons with books that have not been purchased
				if(downloadBtnArr.includes(i)) {
					continue;
				}
				else {
					disableBtn(downloadBtn, i, "#80CCBC", "download");

					disableMsg = "Book must be purchased to download";
					showMsgOnHover(downloadBtn, i, disableMsg);
				}
			}
		}
	</script>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />