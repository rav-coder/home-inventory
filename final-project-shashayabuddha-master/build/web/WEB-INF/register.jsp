<%-- 
    Document   : register
    Created on : 8-Aug-2021, 12:56:17 PM
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="<c:url value="/users.css" />" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
    <div class="card">
        <h2>Register An Account</h2>
        <form action="" method="post" class="card-form">

        <div class="input">
            <label for="emailAdd" class="input-label" >Email</label>
            <input class="input-field dropdownOne" type="text" name="emailAdd" size="30" placeholder="johndoe@server.com" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" value="${emailAdd}" required><br>
        </div>
                    
        <div class="input">
            <label for="usernameAdd" class="input-label">Username</label>
            <input class="input-field" type="text" name="userNameAdd" size="30" placeholder="john20" value="" required><br>
        </div>
        
        <input type="hidden" name="uuid" value="${uuid}">
                    
<!--        <div class="input">
            <label for="passwordAdd" class="input-label">Password</label>
            <input class="input-field" type="text" name="passwordAdd" size="30" placeholder="p@$$w0rd" value="${passwordAdd}" required><br>
        </div>-->

        <div class="input">
            <label for="fname" class="input-label">First Name</label>
            <input class="input-field" type="text" name="firstNameAdd" size="30" placeholder="John" value="${firstNameAdd}" required><br>
        </div>

        <div class="input">
            <label for="lname" class="input-label">Last Lame</label>
            <input class="input-field" type="text" name="lastNameAdd" size="30" placeholder="Doe" value="${lastNameAdd}" required><br>
        </div>

        <div style="margin-top: 20px">
            <input type="submit" class="btn second" name="action" value="Register">
        </div>
            
        <div style="margin-top: 20px">
            <input type="submit" class="btn" name="action" value="Cancel" formnovalidate>
        </div>

        <c:if test="${someNull}">
            <p style="color: red" >Please fill in all the fields.</p>
        </c:if>
        </form>
        
        <p style="color: red" >${messageOut}</p>
        
      </div>
    </body>
</html>
