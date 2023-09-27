<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body>
<div align="center">
<h1> Welcome to the portal </h1> 
<form action=LoginServlet method=post>

<label for="name"> Name  </label>
<input type="text" id="name" name="uname">
<br>
<label for="pwd"> Password </label>
<input type="password" id="pwd" name="pword"> 
<br>
<button > submit</button> 


</form>


</div>
</body>
</html>