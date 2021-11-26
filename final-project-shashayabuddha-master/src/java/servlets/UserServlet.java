
package servlets;

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
import models.Users;
import services.CategoryService;
import services.UserService;

/**
 *
 * @author srvad
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        CategoryService cs = new CategoryService();
        HttpSession session = request.getSession();
        String action = (String) request.getParameter("action");
        String userNameL = (String) session.getAttribute("userNameL");
        
        if(action!=null && action.equals("logout") ){
            
            session.invalidate();
            response.sendRedirect("login");
            return;
        }
        
        request.setAttribute("addView", true);
        try {

            List<Users> users = us.getAll();
            request.setAttribute("users", users); 
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not find the user list. Check databse conneciton.");
            request.setAttribute("message", "error");
        }
        
        Users adminTest = new Users();
        try {
            adminTest = us.get(userNameL);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not validate admin status.");
        }
        
        if(adminTest.getIsAdmin()){
            try {
                List<Categories> categories = cs.getAll();
                request.setAttribute("categories", categories); 
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not find categories form the databse.");
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        }   
        else{
            response.sendRedirect("inventory");
            //getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        }
   
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserService us = new UserService();
        CategoryService cs = new CategoryService();
        
        String email = (String) request.getParameter("email");
        String username = (String) request.getParameter("username");
        String lastName = (String) request.getParameter("lastName");
        String action = (String) request.getParameter("action");
        String actionThree = (String) request.getParameter("actionThree");

        
        String actionTwo = "";
        String emailEdit = "";
        String firstNameEdit = "";
        String userNameEdit = "";
        String lastNameEdit = "";
        String passwordEdit = "";
        actionTwo = (String) request.getParameter("actionTwo");
        String prevLastName = "";
        String prevUserName = "";
        Integer catID = 0;
        
        Users userEdit = new Users();

        
        if(action != null && action.equals("Delete")){
            try {
                if(!us.get(username).getIsAdmin()){
                    
                    us.deleteItemsUser(username);

                    try {
                        
                    us.delete(username, lastName);
                    request.setAttribute("adminCheck", "User deleted.");  
                        
                    } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                else{
                    request.setAttribute("adminCheck", "Administrator can not be deleted.");

                }
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not delete that item.");
            }
            request.setAttribute("addView", true);

        }
        else if(action != null && action.equals("Edit")){
            request.setAttribute("editView", true);
            request.setAttribute("addView", false);
            
//            try {
//                List<Categories> categories = cs.getAll();
//                request.setAttribute("categories", categories); 
//            } catch (Exception ex) {
//                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            try {
            session = request.getSession();
            List<Users> users = new ArrayList();
            users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
            
            try {
                userEdit = us.get(username);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            session.setAttribute("prevUserName", username);
            prevUserName = (String) session.getAttribute("prevUserName");
            
            session.setAttribute("prevLastName", lastName);
            prevLastName = (String) session.getAttribute("prevLastName");
 
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

            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            return;

        }
        else if(action != null && action.equals("Add")){
            request.setAttribute("addView", true);
            Users userOne = null;
            
            String emailAdd = (String) request.getParameter("emailAdd");
            request.setAttribute("emailAdd", emailAdd);
            
            String userNameAdd = (String) request.getParameter("userNameAdd");
            request.setAttribute("userNameAdd", userNameAdd);
            
            String firstNameAdd = (String) request.getParameter("firstNameAdd");
            request.setAttribute("firstNameAdd", firstNameAdd);
            
            String lastNameAdd = (String) request.getParameter("lastNameAdd");
            request.setAttribute("lastNameAdd", lastNameAdd);
            
            String passwordAdd = (String) request.getParameter("passwordAdd");
            request.setAttribute("passwordAdd", passwordAdd);


            if(emailAdd == null || emailAdd.equals("") || userNameAdd == null || userNameAdd.equals("") ||
                    firstNameAdd == null || firstNameAdd.equals("") ||
                    lastNameAdd == null || lastNameAdd.equals("") || passwordAdd == null || passwordAdd.equals("")){
                request.setAttribute("someNull", true);

            }
            
            try {
                userOne = new Users(userNameAdd, passwordAdd, emailAdd, firstNameAdd, lastNameAdd, true, false);
                us.insert(userOne);
                request.setAttribute("adminCheck", "User added.");

                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
        
        if(actionThree != null && actionThree.equals("AddCat")){
            String categoryAdd = (String) request.getParameter("categoryAdd");
            request.setAttribute("categoryAdd", categoryAdd);
            
            if(categoryAdd != null){
                Categories category = new Categories(0, categoryAdd);
                try {
                    cs.insert(category);
                    response.sendRedirect("admin");
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } else if(actionThree != null && actionThree.equals("DeleteCat")){
            catID = Integer.parseInt(request.getParameter("catID")) ;
            try {
                Categories category = cs.get(catID);
                
                if(category.getItemsList().size() <=0){
                    cs.delete(category);
                    response.sendRedirect("admin");
                    return;
                }
                else if(category.getItemsList() == null || category.getItemsList().size() > 0){
                    request.setAttribute("editCatCheck", "Can not delete. Users have items in this category.");
                }
                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(actionThree != null && actionThree.equals("EditCat")){
            request.setAttribute("editCatView", true);
            catID = Integer.parseInt(request.getParameter("catID")) ;
            session.setAttribute("catID", catID);
            try {
                Categories category = cs.get(catID);
                String categoryEdit = category.getCategoryName();
                request.setAttribute("categoryEdit", categoryEdit);
 
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
        }
                    
        String actionFour = (String) request.getParameter("actionFour");
        
        if(actionFour != null && actionFour.equals("SaveCat")){
                String categoryEdit = (String) request.getParameter("categoryEdit");
                Categories category1 = new Categories();
                catID = (int) session.getAttribute("catID") ;
            try {
                category1 = new Categories(catID, categoryEdit);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                try {
                    cs.get(catID).setCategoryName((String) request.getParameter("categoryEdit"));
                    //cs.update(cs.get(catID));
                    cs.update(category1);
                    request.setAttribute("editCatView", false);
                    response.sendRedirect("admin");
                    return;
                    
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
   
        if(actionTwo != null && actionTwo.equals("Save")){
                
                userEdit = new Users((String) session.getAttribute("prevUserName"), request.getParameter("passwordEdit"),
                        request.getParameter("emailEdit"), 
                        request.getParameter("firstNameEdit"), request.getParameter("lastNameEdit"), 
                        Boolean.parseBoolean(request.getParameter("statusEdit")), Boolean.parseBoolean(request.getParameter("roleEdit")));
                
            try {

                us.update(userEdit, (String) session.getAttribute("prevUserName"), (String) session.getAttribute("prevLastName"));
                request.setAttribute("adminCheck", "User Updated.");
                request.setAttribute("addView", true);
                    
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
            else if(actionTwo != null && actionTwo.equals("Cancel")){
                    
            request.setAttribute("editView", false);
            request.setAttribute("addView", true);

        }
            
        try {

            List<Users> users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        
        try {
                List<Categories> categories = cs.getAll();
                request.setAttribute("categories", categories); 
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

    }

}
