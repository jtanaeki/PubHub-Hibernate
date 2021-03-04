<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Admin Login | PubHub</title>
		
		<link rel="shortcut icon" href="resources/imgs/favicon.png" type="image/x-icon">
		
		<style>
			body  {
			  background: #2c3e50;
			  font-family: Calibri;
			  font-size: large;
			}
			
			.card {
				background: #93B1C6;
				margin: 100px 500px;
			  	height: 500px;
			  	border: 0;
  				border-radius: 1rem;
			}
			
			h1 {
				color: #B3422B;
			}
			
			input {
				margin-bottom: 10px;
				font-size: 70%;
			    border-radius: 2rem;
			    letter-spacing: .1rem;
			    padding: 1rem;
			    transition: all 0.2s;
			    font-weight: bold;
			}
			
			.input-text {
				height: 5px;
				font-size: 80%;
				border-radius: 5rem;
				color: black;
			}
			
			.input-btn {
				margin-top: 10px;
			    background: #2c3e50;
				color: #B3422B;
			}
			
			.input-btn:hover {
				cursor: pointer;
			}
		</style>
	</head>
	<body>
		<div class="card" align="center">
			<br /><br />
			<h1><b>PUBHUB<sup>&trade;</sup></b></h1>
			<h3>Administrator Login</h3>
			<form action="j_security_check" method="post">
				<div>
					<input class="input-text" type="email" id="inputEmail" name="j_username"
						placeholder="Email address" required autofocus>
				</div>
				<div>
					<input class="input-text" type="password" id="inputPassword" name="j_password"
						placeholder="Password" required>
				</div>
				<input class="input-btn" type="submit" value="Authorize">
				<input class="input-btn" type="reset" value="Reset">
			</form>
		</div>
	</body>
</html>
