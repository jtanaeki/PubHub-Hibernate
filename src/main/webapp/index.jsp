<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<title>PubHub</title>
		
		<link rel="shortcut icon" href="resources_login/imgs/favicon.png" type="image/x-icon">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
		<link rel="stylesheet" href="resources_login/styles/bootstrap.min.css">
		<link rel="stylesheet" href="resources_login/styles/index.css">
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
					<img src="resources_login/imgs/login-background.jpg" alt="PubHub Picture" />
					<br/><br/><br/>
					<div class="card card-signin flex-row my-5">
						<div class="card-body">
							<h1 class="card-title text-center"><b>PUBHUB&trade;</b></h1>
							<div class="form-signin">
								<a href="Login">
									<button class="btn btn-lg btn-primary btn-block text-uppercase">
										Sign In</button>
								</a>
								<hr class="my-4">
								<a href="Registration">
									<button class="btn btn-lg btn-primary btn-block text-uppercase">
										Create an account</button>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>    
		<script src="resources_login/scripts/jquery.slim.js"></script>
		<script src="resources_login/scripts/bootstrap.bundle.min.js"></script>
		<script src="resources/scripts/custom.js"></script>
	</body>
</html>
