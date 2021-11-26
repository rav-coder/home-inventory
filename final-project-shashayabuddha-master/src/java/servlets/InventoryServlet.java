/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

References:
1) “How to avoid ‘ConcurrentModificationException’ while removing elements from `ArrayList` while iterating it?,” Stackoverflow.com. [Online]. 
    Available: https://stackoverflow.com/questions/18448671/how-to-avoid-concurrentmodificationexception-while-removing-elements-from-arr. [Accessed: 10-Jul-2021].
 */
package servlets;

import dataaccess.CategoriesDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Categories;
import models.Items;
import models.Users;
import services.CategoryService;
import services.InventoryService;
import services.UserService;

/**
 *
 * @author srvad
 */
public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserService us = new UserService();
        InventoryService is = new InventoryService();
        CategoriesDB cdb = new CategoriesDB();
        String userNameL = (String) session.getAttribute("userNameL");
        
        String action = (String) request.getParameter("action");
        
        if(action!=null && action.equals("logout") ){
            response.sendRedirect("login");
            return;
        }
        
        Users userShow = new Users();
        try {
            userShow = us.get(userNameL);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("User not found for that username.");
        }

        
        String userFirstN = userShow.getFirstName();
        request.setAttribute("userFirstN", userFirstN);

        
        String userLastN = userShow.getLastName();
        request.setAttribute("userLastN", userLastN);
        
        session.setAttribute("username", userNameL);
        
        List<Items> items = new ArrayList();
        try {
            items = is.getAll(userShow.getUsername());
            System.out.println("Items not found for that user.");
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Items> toRemove = new ArrayList<Items>();
        
        for(Items e: items){
            if(!e.getOwner().equals(userShow))
                toRemove.add(e);
        }
        
        items.removeAll(toRemove);
        
        request.setAttribute("items", items);
        
        if(items.size() == 0){
            request.setAttribute("itemMessage", "No items. Please add items.");
        }
        
        List<Categories> categories = new ArrayList();
        try {
            categories = cdb.getAll();
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Categories not found in the database.");
        }
        
        request.setAttribute("categories", categories);
        
        String firstNameEdit = "";
        String userNameEdit = "";
        String lastNameEdit = "";
        String passwordEdit = "";
        String emailEdit = "";
        String username = (String) session.getAttribute("username");
        Users userEdit = new Users();
        
        
        try {
                userEdit = us.get(username);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("User not found for that username.");
            }
            
//            session.setAttribute("prevUserName", username);
//            prevUserName = (String) session.getAttribute("prevUserName");
//            
//            session.setAttribute("prevLastName", lastName);
//            prevLastName = (String) session.getAttribute("prevLastName");
 
            userNameEdit = userEdit.getUsername();
            request.setAttribute("userNameEdit", userNameEdit);
            
            emailEdit = userEdit.getEmail();
            request.setAttribute("emailEdit", emailEdit);
            
            firstNameEdit = userEdit.getFirstName();
            request.setAttribute("firstNameEdit", firstNameEdit);
            
            lastNameEdit = userEdit.getLastName();
            request.setAttribute("lastNameEdit", lastNameEdit);
            
            passwordEdit = userEdit.getPassword();
            request.setAttribute("passwordEdit", passwordEdit);
            
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserService us = new UserService();
        InventoryService is = new InventoryService();
        CategoriesDB cdb = new CategoriesDB();
        CategoryService cs = new CategoryService();
        
        String action = (String) request.getParameter("action");
        String itemName = (String) request.getParameter("itemName");

        
        Double itemPrice = 0.0;
        if(request.getParameter("itemPrice") != null)
            itemPrice = Double.parseDouble(request.getParameter("itemPrice"));
        
        Integer categoryAdd = 0;
        if(request.getParameter("categoryAdd") != null)
            categoryAdd = Integer.parseInt(request.getParameter("categoryAdd"));
        
        String username = (String) session.getAttribute("username");
        
        Integer selectedID = 0;
        if(request.getParameter("selectedID") != null)
            selectedID = Integer.parseInt(request.getParameter("selectedID"));
        
        //session.setAttribute("selectedID", selectedID);

        if(action != null && action.equals("Delete")){
            try {
                Items item = new Items();
                
                item = new Items(selectedID);

                is.delete(selectedID);
                session.setAttribute("itemMessage", "Item deleted.");
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not delete items with taht id");
            }
            response.sendRedirect("inventory");
            return;
            
        }else if(action != null && action.equals("Add")){
            
            Items item = new Items();
            try {
                Integer hereRows = is.totalRows() + 1;
                item = new Items(0, itemName, itemPrice);
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("The new item could not be added.");
            }
            
            Categories category = new Categories(categoryAdd);
            item.setCategory(category);
            
            Users user = new Users(username);
            item.setOwner(user);
            
            try {
                is.insert(item);
                session.setAttribute("itemMessage", "Item added.");
                
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("The new item could not be added.");
            }
            response.sendRedirect("inventory");
            return;
            
        } else if(action != null && action.equals("Edit")){
            
            request.setAttribute("editItemView", true);
            Items item = new Items();
            
            try {
                item = is.get(selectedID);
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not get that item");
            }
            
            String itemNameEdit = item.getItemName();
            request.setAttribute("itemNameEdit", itemNameEdit);
            
            Double itemPriceEdit = item.getPrice();
            request.setAttribute("itemPriceEdit", itemPriceEdit);
            
            if(request.getParameter("selectedID") != null)
            selectedID = Integer.parseInt(request.getParameter("selectedID"));
            
            session.setAttribute("selectedID", selectedID);
            
            //response.sendRedirect("inventory");
            //getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
            //return;
            
        }
        
        if(action != null && action.equals("Save")){
            double priceL = 0;
            String nameL = request.getParameter("itemNameEdit");
            priceL = Double.parseDouble(request.getParameter("itemPriceEdit"));
            
            Integer categoryL = 0;
            if(request.getParameter("categoryEdit") != null)
                categoryL = Integer.parseInt(request.getParameter("categoryEdit"));
            
            Categories category = new Categories();
            try {
                category = cs.get(categoryL);
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Items item = new Items();
            try {
                item = is.get((int) session.getAttribute("selectedID"));
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not get that item.");
            }
            
            item.setCategory(category);
            item.setItemName(nameL);
            item.setPrice(priceL);
            
            try {
                is.update(item);
                session.setAttribute("itemMessage", "Item updated.");
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not update the item.");
            }
  
        }
        else if(action != null && action.equals("Cancel")){
            request.setAttribute("editItemView", false);
        }
        
        String actionTwo = request.getParameter("actionTwo");
        
            if(actionTwo != null && actionTwo.equals("Save")){
                
                Users userEdit = new Users(username, request.getParameter("passwordEdit"),
                        request.getParameter("emailEdit"), 
                        request.getParameter("firstNameEdit"), request.getParameter("lastNameEdit"), 
                        Boolean.parseBoolean(request.getParameter("statusEdit")), false);
                
            try {

                us.update(userEdit, (String) session.getAttribute("prevUserName"), (String) session.getAttribute("prevLastName"));
                //getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                //return;
                response.sendRedirect("inventory");
                return;
                //request.setAttribute("adminCheck", "User Updated.");
                //request.setAttribute("addView", true);
                    
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not update the item.");
            }

            }
            
        String userNameL = (String) session.getAttribute("userNameL");

        
        if(action!=null && action.equals("logout") ){
            response.sendRedirect("login");
            return;
        }
        
        Users userShow = new Users();
        try {
            userShow = us.get(userNameL);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not find a user for that username.");
        }

        
        String userFirstN = userShow.getFirstName();
        request.setAttribute("userFirstN", userFirstN);

        
        String userLastN = userShow.getLastName();
        request.setAttribute("userLastN", userLastN);
        
        session.setAttribute("username", userNameL);
        
        List<Items> items = new ArrayList();
        try {
            items = is.getAll(userShow.getUsername());
            System.out.println("Could not find items for that username.");
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Items> toRemove = new ArrayList<Items>();
        
        for(Items e: items){
            if(!e.getOwner().equals(userShow))
                toRemove.add(e);
        }
        
        items.removeAll(toRemove);
        
        request.setAttribute("items", items);
        
        if(items.size() == 0){
            request.setAttribute("itemMessage", "No items. Please add items.");
        }
        
        List<Categories> categories = new ArrayList();
        try {
            categories = cdb.getAll();
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("categories", categories);
        
        String firstNameEdit = "";
        String userNameEdit = "";
        String lastNameEdit = "";
        String passwordEdit = "";
        String emailEdit = "";
        Users userEdit = new Users();
        
        
        try {
                userEdit = us.get(username);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            session.setAttribute("prevUserName", username);
//            prevUserName = (String) session.getAttribute("prevUserName");
//            
//            session.setAttribute("prevLastName", lastName);
//            prevLastName = (String) session.getAttribute("prevLastName");
 
            userNameEdit = userEdit.getUsername();
            request.setAttribute("userNameEdit", userNameEdit);
            
            emailEdit = userEdit.getEmail();
            request.setAttribute("emailEdit", emailEdit);
            
            firstNameEdit = userEdit.getFirstName();
            request.setAttribute("firstNameEdit", firstNameEdit);
            
            lastNameEdit = userEdit.getLastName();
            request.setAttribute("lastNameEdit", lastNameEdit);
            
            passwordEdit = userEdit.getPassword();
            request.setAttribute("passwordEdit", passwordEdit);
            
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);   
    }

}
