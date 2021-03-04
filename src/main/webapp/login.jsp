<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Login | PubHub</title>
		
		<link rel="shortcut icon" href="resources_login/imgs/favicon.png" type="image/x-icon">
		<link rel="stylesheet" href="resources_login/styles/bootstrap.min.css">
		<link rel="stylesheet" href="resources_login/styles/login.css">
		
		<style>
			.bg-image {
				background-image: url(resources_login/imgs/login-background.jpg);
			}
		
			body {
				background-color: #93B1C6;
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
		<div class="container-fluid">
			<div class="row no-gutter">
				<div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>
				<div class="col-md-8 col-lg-6">
					<div class="login d-flex align-items-center py-5">
						<div class="container">
							<div class="row">
								<div class="col-md-9 col-lg-8 mx-auto">
									<h1><b>PUBHUB<sup>&trade;</sup></b></h1>
									<h3 class="login-heading mb-4">Welcome Back!</h3>
									<form class="form-signin" action="Login" method="post">
										<div class="form-label-group">
											<input type="email" id="inputEmail" name="inputEmail" class="form-control" 
												placeholder="Email address" required autofocus>
											<label for="inputEmail">Email address</label>
										</div>

										<div class="form-label-group">
											<input type="password" id="inputPassword" name="inputPassword" class="form-control" 
												placeholder="Password" required>
											<label for="inputPassword">Password</label>
										</div>

										<div class="custom-control custom-checkbox mb-3">
											<input type="checkbox" class="custom-control-input" 
												id="customCheck1">
											<label class="custom-control-label" for="customCheck1">
												Remember password</label>
										</div>
										<button class="btn btn-lg btn-primary btn-block btn-login 
											text-uppercase font-weight-bold mb-2" type="submit">
											Log In</button>
										<div class="text-center small">
											<a href="Registration">Create account</a>&emsp;&emsp;
											<a href="Home">Forgot password?</a>
										</div>
									</form>
								</div>
							</div>
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
