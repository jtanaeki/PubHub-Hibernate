	<!-- Header -->
	<jsp:include page="header.jsp" />
	
	<!-- JSTL includes -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
	 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/css/all.min.css">
	
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
		
		<h1>PUBHUB <small>Checkout Information</small></h1>
		<hr class="book-primary">
		
		<table class="table table-striped table-hover table-responsive">
			<thead>
				<tr>
					<td>ISBN-13:</td>
					<td>Title:</td>
					<td>Author:</td>
					<td>Publish Date:</td>
					<td>Price:</td>
					<td>Tag Name:</td>
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
						<td class="price-td"><fmt:formatNumber value="${book.price }" type="CURRENCY"/></td>
						<td class="tag-td"><c:out value="${book.tagName}" /></td>
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
				</tr>
			</tfoot>
		</table>

	  </div>
	</header>
	<span id="scroll-ship"></span>
	<section>
		<div class="container">
			<h1>
				<small>Shipping/Billing</small>
				<c:choose>
					<c:when test="${shippingStatus == 'done'}">
						<i class="fas fa-check-circle"></i>
					</c:when>
	
					<c:otherwise>
						<i class="fas fa-times-circle"></i>
					</c:otherwise>
				</c:choose>
			</h1>
			
			<c:choose>
				<c:when test="${not empty messageShip }">
					<p class="alert ${messageClassShip}">${messageShip }</p>
					<%
						session.setAttribute("messageShip", null);
						session.setAttribute("messageClassShip", null);
					%>
				</c:when>
			</c:choose>
	
			<hr class="book-primary light">
			<form action="AddShipping" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="name" class="col-sm-4 control-label">Name</label>
					<div class="col-sm-5">
						<input type="text" class="form-control shipping" id="name" name="name" 
							placeholder="Name" required="required" value="${shipNameVal}" />
					</div>
				</div>
				<div class="form-group">
					<label for="street" class="col-sm-4 control-label">Street</label>
					<div class="col-sm-5">
						<input type="text" class="form-control shipping" id="street" name="street" 
							placeholder="Street" required="required" value="${shipStreetVal}" />
					</div>
				</div>
				<div class="form-group">
					<label for="city" class="col-sm-4 control-label">City</label>
					<div class="col-sm-5">
						<input type="text" class="form-control shipping" id="city" name="city" 
							placeholder="City" required="required" value="${shipCityVal}" />
					</div>
				</div>
				<div class="form-group">
					<label for="state" class="col-sm-4 control-label">State</label>
					<div class="col-sm-5">
						<select id="state" class="form-control shipping" name="state">
							<jsp:include page="stateDropdown.jsp" />
						</select>
						<input id="state-value" type="hidden" value="${shipStateVal}">
					</div>
				</div>
				<div class="form-group">
					<label for="zip-code" class="col-sm-4 control-label">Zip Code</label>
					<div class="col-sm-5">
						<input type="number" class="form-control shipping" id="zip-code" name="zip-code" 
							placeholder="Zip Code" min="10001" max="99999" required="required" 
							value="${shipZipCodeVal}" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-1">
						<input id="shipping-status" type="hidden" value="${shippingStatus}">
						<input id="shipping-status-scroll" type="hidden" value="${shippingStatusScroll}">
						<c:choose>
							<c:when test="${shippingStatus == 'done'}">
								<button type="submit" class="btn edit">Edit Shipping</button>
								<%
									session.setAttribute("shippingStatusScroll", null);
								%>
							</c:when>
	
							<c:otherwise>
								<button type="submit" class="btn btn-primary">Add Shipping</button>
								<%
									session.setAttribute("shippingStatusScroll", null);
								%>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form>
		</div>
	</section>
	<span id="scroll-pay"></span>
	<section class="success">
		<div class="container">
			<h1>
				<small>Payment</small>
				<c:choose>
					<c:when test="${paymentStatus == 'done'}">
						<i class="fas fa-check-circle"></i>
					</c:when>
	
					<c:otherwise>
						<i class="fas fa-times-circle"></i>
					</c:otherwise>
				</c:choose>
			</h1>
	
			<c:choose>
				<c:when test="${not empty messagePay }">
					<p class="alert ${messageClassPay}">${messagePay }</p>
					<%
						session.setAttribute("messagePay", null);
						session.setAttribute("messageClassPay", null);
					%>
				</c:when>
			</c:choose>
	
			<hr class="book-primary">
			<form action="AddPayment" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="name" class="col-sm-4 control-label">Name</label>
					<div class="col-sm-5">
						<input type="text" class="form-control payment" id="name" name="name" 
							placeholder="Name" required="required" value="${payNameVal}" />
					</div>
				</div>
				<div class="form-group">
					<label for="number" class="col-sm-4 control-label">Card Number</label>
					<div class="col-sm-5">
						<input type="number" class="form-control payment" id="number" name="number" 
							placeholder="Card Number" min="1000000000000001" max="9999999999999999" 
							required="required" value="${payCardVal}" />
					</div>
				</div>
				<div class="form-group">
					<label for="expiration-date" class="col-sm-4 control-label">Expiration Date</label>
					<div class="col-sm-5">
						<input type="text" class="form-control payment" id="expiration-date" 
							name="expiration-date" placeholder="Expiration Date" required="required" 
							value="${payExpirationVal}" />
					</div>
				</div>
				<div class="form-group">
					<label for="cvv" class="col-sm-4 control-label">CVV</label>
					<div class="col-sm-5">
						<input type="number" class="form-control payment" id="cvv" name="cvv" 
							placeholder="CVV" min="101" max="999" required="required" 
							value="${payCVVVal}" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-1">
						<input id="payment-status" type="hidden" value="${paymentStatus}">
						<input id="payment-status-scroll" type="hidden" value="${paymentStatusScroll}">
						<c:choose>
							<c:when test="${paymentStatus == 'done'}">
								<button type="submit" class="btn edit">Edit Payment</button>
								<%
									session.setAttribute("paymentStatusScroll", null);
								%>
							</c:when>
	
							<c:otherwise>
								<button type="submit" class="btn btn-primary">Add Payment</button>
								<%
									session.setAttribute("paymentStatusScroll", null);
								%>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form>
		</div>
	</section>
	<div style="height: 50px; padding-top: 20px">
		<div class="container" align="center">
			<form action="PlaceOrder" method="post">
				<button type="submit" class="btn btn-success place-order">Place Order</button>
			</form>
		</div>
	</div>
	<style>
		.edit {
			background: gray;
			color: white;
		}
		.edit:hover {
			background: darkgray;
		}
		.place-order {
			font-size: 1.5em;
		}
	</style>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script>
		var bookRow = document.getElementsByClassName("book-row");
		var tagName = document.getElementsByClassName("tag-name");
		var tagTD = document.getElementsByClassName("tag-td");
		var priceTD = document.getElementsByClassName("price-td");
		var total = document.getElementById("total");
		var state = document.getElementById("state");
		var stateValue = document.getElementById("state-value");
		var shippingStatusScroll = document.getElementById("shipping-status-scroll");
		var scrollShip = document.getElementById("scroll-ship");
		var shippingStatus = document.getElementById("shipping-status");
		var shipping = document.getElementsByClassName("shipping");
		var paymentStatusScroll = document.getElementById("payment-status-scroll");
		var scrollPay = document.getElementById("scroll-pay");
		var paymentStatus = document.getElementById("payment-status");
		var payment = document.getElementsByClassName("payment");
		
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
		
		
// 		Handles 'state' field
		state.value = stateValue.value;

		
// 		Scrolls to shipping section
		if(shippingStatusScroll.value == "add" || shippingStatusScroll.value == "edit") {
			$("html, body").animate({
			    scrollTop: ($("#scroll-ship").offset().top)
			},500);
		}
		
// 		Disables shipping fields
		for (i = 0; i < shipping.length; i++) {
			if(shippingStatus.value == "done") {
				shipping[i].disabled = true;
			}
		}
		
		
// 		Scrolls to payment section
		if(paymentStatusScroll.value == "add" || paymentStatusScroll.value == "edit") {
			$("html, body").animate({
			    scrollTop: ($("#scroll-pay").offset().top)
			},500);
		}
		
		
// 		Disables payment fields
		for (i = 0; i < payment.length; i++) {
			if(paymentStatus.value == "done") {
				payment[i].disabled = true;
			}
		}
	</script>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />