<%-- 
    Document   : inventory
    Created on : 10-Jul-2021, 12:15:40 PM
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" rel="stylesheet" href="<c:url value="/users.css" />" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory</title>
    </head>
    <body>
        <p style="text-align: center; margin-top: 20px; text-transform: uppercase;"><a href="inventory?action=logout">Logout</a></p>
        <p> ${totalRows}</p>
        <div class="card itemOne">
            <h2>Inventory for</h2>
            <h2>${userFirstN} ${userLastN}</h2>
            <p></p>
            <c:forEach items="${items}" var="item">
                <form action="" method="post">
                    <div class="grid-container forItems">
                        
                        <div class="grid-item">
                            ${item.getCategory().getCategoryName()}
                        </div>
                        
                        <div class="grid-item">
                            ${item.getItemName()}
                        </div>

                        <div class="grid-item">
                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.getPrice()}"/>
                        </div>
                        
                        <input type="hidden" name="username" value="${userShow.getUsername()}">
                        <input type="hidden" name="selectedID" value="${item.getItemID()}">
                        <input type="hidden" name="selectedName" value="${item.getItemName()}">
                        <input type="hidden" name="selectedPrice" value="${item.getPrice()}">
                        
                        <div class="grid-item">
                            <input type="submit" class="btn second" name="action" value="Edit">   
                        </div>
                        
                        <div class="grid-item">
                            <input type="submit" class="btn first" name="action" value="Delete">      
                        </div>

                    </div>
                </form>
            </c:forEach>
            <p style="color: red" >${itemMessage}</p>
        </div>
        
        <c:if test="${editItemView}">
        <div class="card">
            <h2>Edit Item</h2>
            
            <form action="" method="post" class="card-form">

                <div class="input">
                        <label for="itemNameEdit" class="input-label">Name</label>
                        <input class="input-field dropdownOne" type="text" name="itemNameEdit"  value="${itemNameEdit}" required><br>
                </div>

                    <div class="input">
                        <label for="itemPriceEdit" class="input-label">Price</label>
                        <input class="input-field" type="number" name="itemPriceEdit" min="1" max="10000" step="any" value="${itemPriceEdit}" required><br>
                    </div>
                    
                    <input type="hidden" name="selectedID" value="${selectedID}">
                
                    <div class="input">
                    <label for="categoryEdit" class="input-label">Category</label>
                    <select name="categoryEdit" id="categoryAdd" class="input-field  dropdownOne" required>
                        <option value="" selected required>Choose here</option>
                        <c:forEach items="${categories}" var="category"> 
                            <option value="${category.getCategoryID()}">${category.getCategoryName()}</option>
                        </c:forEach>
                    </select>
                    </div>

                    <div style="margin-top: 20px">
                        <input type="submit" class="btn second" name="action" value="Save">
                    </div>
                    
                    <div style="margin-top: 10px">
                        <input type="submit" class="btn first" name="action" value="Cancel" formnovalidate>
                    </div>

                    <c:if test="${someNull}">
                        <p style="color: red" >Please fill in all the fields.</p>
                    </c:if>
            </form>
        </div>
        </c:if>
        
        <div class="card">
            <h2>Add Item</h2>
            
            <form action="" method="post" class="card-form">

                <div class="input">
                        <label for="itemName" class="input-label">Name</label>
                        <input class="input-field dropdownOne" type="text" name="itemName" placeholder="Lamp" value="" required><br>
                </div>

                    <div class="input">
                        <label for="itemPrice" class="input-label">Price</label>
                        <input class="input-field" type="number" name="itemPrice" min="1" max="10000" placeholder="99.99" value="" required><br>
                    </div>
                
                    <div class="input">
                    <label for="categoryAdd" class="input-label">Category</label>
                    <select name="categoryAdd" id="categoryAdd" class="input-field  dropdownOne" required>
                        <option value="" selected required>Choose here</option>
                        <c:forEach items="${categories}" var="category"> 
                            <option value="${category.getCategoryID()}">${category.getCategoryName()}</option>
                        </c:forEach>
                    </select>
                    </div>

                    <div style="margin-top: 20px">
                        <input type="submit" class="btn second" name="action" value="Add">
                    </div>

                    <c:if test="${someNull}">
                        <p style="color: red" >Please fill in all the fields.</p>
                    </c:if>
            </form>
        </div>
        
        
        
        <div class="card">
                <h2 style="color: #2ecc71">Edit Profile</h2>
                
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

                    <div style="margin-top: 20px">
                        <input type="submit" class="btn second" name="actionTwo" value="Save">
                    </div>

                </form>  
            </div>
        
    </body>
</html>
