<%-- 
    Document   : users
    Created on : 30-Jun-2021, 11:23:51 AM
    Author     : srvad
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<link type="text/css" rel="stylesheet" href="<c:url value="/users.css" />" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users Page</title>
    </head>
    <body>
   
        <p style="text-align: center; margin-top: 20px; text-transform: uppercase;"><a href="admin?action=logout">Logout</a></p>
        <div class="card userOne">
            <h2>Manage Users</h2>
            <p></p>
            <c:forEach items="${users}" var="user">
                <form action="" method="post">
                    <div class="grid-container resUser">
                        
                        <div class="grid-item">
                            ${user.username}
                        </div>
                        
                        <div class="grid-item">
                            ${user.firstName}
                        </div>

                        <div class="grid-item">
                            ${user.lastName}
                        </div>
                        
                        <input type="hidden" name="username" value="${user.username}">
                        <input type="hidden" name="email" value="${user.email}">
                        <input type="hidden" name="lastName" value="${user.lastName}">
                        
                        <input type="hidden" name="prevUserName" value="${prevUserName}">
                        <input type="hidden" name="prevLastName" value="${prevLastName}">

                        <div class="grid-item">
                            <input type="submit" class="btn second" name="action" value="Edit">   
                        </div>

                        <div class="grid-item">
                            <input type="submit" class="btn first" name="action" value="Delete">      
                        </div>

                    </div>
                </form>
            </c:forEach>
            <p style="color: red" >${adminCheck}</p>
        </div>
            
  
        <c:if test="${addView}">
            <div class="card">
                        <h2>Add User</h2>
                        <form action="" method="post" class="card-form">

                    <div class="input">
                        <label for="emailAdd" class="input-label" >Email</label>
                        <input class="input-field dropdownOne" type="text" name="emailAdd" size="30" placeholder="johndoe@server.com" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" value="" required><br>
                    </div>
                    
                    <div class="input">
                        <label for="usernameAdd" class="input-label">Username</label>
                        <input class="input-field" type="text" name="userNameAdd" size="30" placeholder="john20" value="" required><br>
                    </div>
                    
                    <div class="input">
                        <label for="passwordAdd" class="input-label">Password</label>
                        <input class="input-field" type="text" name="passwordAdd" size="30" placeholder="p@$$w0rd" value="" required><br>
                    </div>

                    <div class="input">
                    <label for="fname" class="input-label">First Name</label>
                    <input class="input-field" type="text" name="firstNameAdd" size="30" placeholder="John" value="" required><br>
                    </div>

                    <div class="input">
                    <label for="lname" class="input-label">Last Lame</label>
                    <input class="input-field" type="text" name="lastNameAdd" size="30" placeholder="Doe" value="" required><br>
                    </div>

                    <div style="margin-top: 20px">
                        <input type="submit" class="btn second" name="action" value="Add">
                    </div>

                    <c:if test="${someNull}">
                        <p style="color: red" >Please fill in all the fields.</p>
                    </c:if>
                </form>
            </div>
        </c:if>
            

        <c:if test="${editView}">
            
            <div class="card">
                <h2 style="color: #2ecc71">Edit User</h2>
                
                <form action="" method="post" class="card-form">
                    
                    <div class="input">
                        <label for="emailEdit" class="input-label">Email</label>
                        <input class="input-field dropdownOne" type="text" name="emailEdit" size="30" value="${emailEdit}" required><br>
                    </div>
                    
                    <div class="input">
                        <label for="usernameEdit" class="input-label">Username</label>
                        <input class="input-field" type="text" name="userNameEdit" size="30" value="${userNameEdit}" disabled><br>
                    </div>
                    
                    <div class="input">
                    <label for="passwordEdit" class="input-label">Password</label>
                    <input type="text" name="passwordEdit" size="30" value="${passwordEdit}" class="input-field" required><br>
                    </div>

                    <div class="input">
                        <label for="fname" class="input-label">First Name</label>
                        <input class="input-field" type="text" name="firstNameEdit" size="30" value="${firstNameEdit}" required><br>
                    </div>
                    
                    <div class="input">
                        <label for="lname" class="input-label">Last Name</label>
                        <input type="text" name="lastNameEdit" size="30" value="${lastNameEdit}" class="input-field" required><br>
                    </div>

                    
                    
                    <div class="input">
                    <label for="statusEdit" class="input-label">Status</label>
                    <select name="statusEdit" id="statusEdit" class="input-field  dropdownOne" required>
                        <option value="" selected required>Choose here</option>
                        <option value="false">Inactive</option>
                        <option value="true" >Active</option>
                    </select>
                    </div>
                    
                    <div class="input">
                    <label for="roleEdit" class="input-label">Role</label>
                    <select name="roleEdit" id="roleEdit" class="input-field  dropdownOne" required>
                        <option value="" selected required>Choose here</option>
                        <option value="false">Regular User</option>
                        <option value="true" >Administrator</option>
                    </select>
                    </div>

                    <div style="margin-top: 20px">
                        <input type="submit" class="btn second" name="actionTwo" value="Save">
                    </div>
                    
                    <div style="margin-top: 10px">
                        <input type="submit" class="btn first" name="actionTwo" value="Cancel" formnovalidate>
                    </div>
                </form>  
            </div>
        </c:if>
        
        <div class="card itemThree">
            <h2>Category List</h2>

            <form action="" method="post" class="card-form">
                    
                <div class="input">
                    <label for="categoryAdd" class="input-label">Category: </label>
                    <input class="input-field dropdownOne" type="text" name="categoryAdd" size="30" value="" required><br>
                </div>
                
                <div style="margin-top: 20px">
                    <button type="submit" class="btn second" name="actionThree" value="AddCat"> Add</button> 
                </div>
                
            </form>
            
            <p></p>
            
            <c:forEach items="${categories}" var="category">
                <form action="" method="post">
                    <div class="grid-container category2">
                        
                        <div class="grid-item">
                            ${category.categoryID}
                        </div>
                        
                        <div class="grid-item">
                            ${category.categoryName}
                        </div>
                        
                        <input type="hidden" name="catID" value="${category.categoryID}">
                        <input type="hidden" name="catName" value="${category.categoryName}">
<!--                        
                        <input type="hidden" name="email" value="${user.email}">
                        <input type="hidden" name="lastName" value="${user.lastName}">
                        
                        <input type="hidden" name="prevUserName" value="${prevUserName}">
                        <input type="hidden" name="prevLastName" value="${prevLastName}">-->

                        <div class="grid-item">
                            <button type="submit" class="btn second" name="actionThree" value="EditCat"> Edit</button>
                        </div>

                        <div class="grid-item">
                            <button type="submit" class="btn first" name="actionThree" value="DeleteCat"> Delete</button>     
                        </div>

                    </div>
                </form>
            </c:forEach>
                
            <c:if test="${editCatView}">
                <form action="" method="post" class="card-form">
                    
                <div class="input">
                    <label for="categoryEdit" class="input-label">Category: </label>
                    <input class="input-field dropdownOne" type="text" name="categoryEdit" size="30" value="${categoryEdit}" required><br>
                </div>
                
                <input type="hidden" name="catID" value="${catID}">
                
                <div style="margin-top: 20px">
                    <button type="submit" class="btn second" name="actionFour" value="SaveCat"> Save</button> 
                </div>
                
                </form>
            </c:if>
            <p style="color: red" >${editCatCheck}</p>
        </div>
   
    </body>
</html>
