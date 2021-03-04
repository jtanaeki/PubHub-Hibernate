<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Registration | PubHub</title>
		
		<link rel="shortcut icon" href="resources_login/imgs/favicon.png" type="image/x-icon">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
		<link rel="stylesheet" href="resources_login/styles/bootstrap.min.css">
		<link rel="stylesheet" href="resources_login/styles/registration.css">
		
		<style>
			.card-img-left {
				background: scroll center url(resources_login/imgs/login-background.jpg);
			}
		</style>
	</head>
	<body>
		<!-- JSTL includes -->
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	
			</div>
		</header>
		<div class="container">
			<div class="row">
				<div class="col-lg-10 col-xl-9 mx-auto">
					<div class="card card-signin flex-row my-5">
						<div class="card-img-left d-none d-md-flex">
							<!-- Background image for card set in CSS! -->
						</div>
						<div class="card-body">
							<h5 class="card-title text-center">Sign up for <b>PUBHUB&trade;</b></h5>
							<form class="form-signin" action="Registration" method="post">
								<div class="form-label-group">
									<input type="text" id="inputFullName" name="inputFullName" 
										class="form-control" placeholder="Name" required autofocus> 
									<label for="inputUserame">Name</label>
									<div class="text-center small">
										Enter first and last name.
									</div>
								</div>
								
								<hr>
								
								<div class="form-label-group">
									<input type="email" id="inputUserame" name="inputUserame" 
										class="form-control" placeholder="Username" required> 
									<label for="inputUserame">Username</label>
									<div class="text-center small">
										Username must be at least 8 characters long and unique.
									</div>
								</div>

								<hr>

								<div class="form-label-group">
									<input type="password" id="inputPassword" name="inputPassword" 
										class="form-control" placeholder="Password" required> 
									<label for="inputPassword">Password</label>
									<div class="text-center small">
										Password must be at least 8 characters long and include 2 numbers.
									</div>
								</div>

								<div class="form-label-group">
									<input type="password" id="inputConfirmPassword" 
									name="inputConfirmPassword" class="form-control" placeholder="Password" 
										required> 
									<label for="inputConfirmPassword">Confirm password</label>
								</div>

								<button class="btn btn-lg btn-primary btn-block text-uppercase"
									type="submit">Register</button>
								<div class="text-center small">
									Already have an account?&ensp;<a href="Login">Sign In</a>
								</div>
								<hr class="my-4">
								<a href="https://www.google.com/">
									<button class="btn btn-lg btn-google btn-block text-uppercase" type="button">
										<i class="fab fa-google mr-2"></i> Sign up with Google
									</button>
								</a><br/>
								<a href="https://www.facebook.com/">
									<button class="btn btn-lg btn-facebook btn-block text-uppercase" type="button">
										<i class="fab fa-facebook-f"></i>&ensp;Sign up with Facebook
									</button>
								</a>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="resources_login/scripts/jquery.slim.js"></script>
		<script src="resources_login/scripts/bootstrap.bundle.min.js"></script>
		<script src="resources/scripts/custom.js"></script>
    	<script src="resources/libraries/js/jquery.js"></script>
    </body>
</html>
