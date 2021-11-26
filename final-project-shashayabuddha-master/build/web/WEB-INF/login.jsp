<%-- 
    Document   : login
    Created on : 9-Jul-2021, 3:37:25 PM
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="<c:url value="/login.css" />" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory</title>
    </head>
    <body>
        
        <div class="card">
            <div class="welcomeHeader">Welcome!</div>
            <div style="margin-top: 5px; text-align: center;">Sign in to your account</div>
            
            <form action="" method="post" class="card-form">
                
                <div class="input">
                    <label for="userNameL" class="input-label">Username</label>
                    <input type="text" name="userNameL" placeholder="john20" value="${userNameL}" class="input-field" required><br>
                </div>
                

                <div class="input">
                    <label for="passWordL" class="input-label">Password</label>
                    <input type="password" name="passWordL" placeholder="pa$$word" value="" class="input-field" required><br>
                </div>
                

                <div style="margin-top: 30px; text-align: center;">
                    <input type="submit" value="LOGIN" class="btn second">
                </div>

                <c:if test="${invalidLogin}">
                    <p style="color: red">Invalid credentials. Please try again.</p>
                </c:if>
                
                <c:if test="${validLogOut}">
                    <p style="color: red">You have successfully logged out.</p>
                </c:if>
                    
                <c:if test="${nonActive}">
                    <p style="color: red">Non-active users can not log in.</p>
                </c:if>

            </form>
            
            <p style="margin-top: 20px; margin-left: 10px;"><a href="forgot">Forgot Password?</a></p>
                
            <p style="text-align: center; margin-top: 20px;">New User? <a href="register">Register Here</a></p>
        </div>
        
        <div class="login-user-pass">
            <br>Login Info<br>
            <br>Admin Level - Username: <span style="color:blue">admin</span> , Password: <span style="color:blue">password</span><br>
            User Level - Username:  <span style="color:blue">anne</span>, Password: <span style="color:blue">password</span><br>
        </div>
            
    </body>
</html>
