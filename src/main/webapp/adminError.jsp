<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Admin Login Error | PubHub</title>
		
		<link rel="shortcut icon" href="resources/imgs/favicon.png" type="image/x-icon">
		
		<style>
			body  {
			  background: #2c3e50;
			  font-family: Calibri;
			  font-size: large;
			}
			
			.card {
				background: #93B1C6;
				margin: 150px 500px;
			  	height: 300px;
			  	border: 0;
  				border-radius: 1rem;
			}
			
			h3 {
				color: #e83333;
			}
		</style>
	</head>
	<body>
		<!-- JSTL includes -->
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		
		<c:url var="adminURL" value="PublishBook"/>
		
		<div class="card" align="center">
			<br /><br /><br />
			<h3>Access Denied</h3>
			<p>
				You do not have permission to view this page. Please contact an administrator or
			 	<a href="${adminURL}">try again</a>
			 	with different credentials.
			 </p>
		</div>
	</body>
</html>
