/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.AccountService;
import services.UserService;

/**
 *
 * @author srvad
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("uuid") == null){
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
        else{           
            //String uuidH = (String) session.getAttribute("uuid");
            String uuidH = (String) request.getParameter("uuid");
            request.setAttribute("uuid", uuidH);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserService us = new UserService();
        Users userOne = null;
        String uuidC = "";
        String action = (String) request.getParameter("action");
        AccountService as = new AccountService();
        
        if(request.getParameter("uuid") != ""){
            uuidC = request.getParameter("uuid");
            String password = request.getParameter("nPassword");
            
            as.changePassword(uuidC, password);
            response.sendRedirect("login");
            return;
        }
        
        if(action != null && action.equals("Register")){
            
            String emailAdd = (String) request.getParameter("emailAdd");
            request.setAttribute("emailAdd", emailAdd);
            
            String userNameAdd = (String) request.getParameter("userNameAdd");
            request.setAttribute("userNameAdd", userNameAdd);
            
            String firstNameAdd = (String) request.getParameter("firstNameAdd");
            request.setAttribute("firstNameAdd", firstNameAdd);
            
            String lastNameAdd = (String) request.getParameter("lastNameAdd");
            request.setAttribute("lastNameAdd", lastNameAdd);
            
//            String passwordAdd = (String) request.getParameter("passwordAdd");
//            request.setAttribute("passwordAdd", passwordAdd);


            if(emailAdd == null || emailAdd.equals("") || userNameAdd == null || userNameAdd.equals("") ||
                    firstNameAdd == null || firstNameAdd.equals("") ||
                    lastNameAdd == null || lastNameAdd.equals("")){
                request.setAttribute("someNull", true);

            }
            
            try {
                if(us.get(userNameAdd) != null){
                    request.setAttribute("messageOut", "Username already exists. Please try a new username.");
                    getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("COuld not find a user with that username.");
            }
            
            
            try {
                userOne = new Users(userNameAdd,  Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX)
                , emailAdd, firstNameAdd, lastNameAdd, true, false);
                us.insert(userOne);
                
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not insert that user.");
            }
            
            {
                    String path = getServletContext().getRealPath("/WEB-INF");
                    
                    Users user = new Users();

                        String url = request.getRequestURL().toString();

                        String uuid = UUID.randomUUID().toString();
                        session.setAttribute("uuid", uuid);

                        String link = url + "?uuid=" + uuid;

//                        String username = request.getParameter("fEmail");
//
//                        try {
//                            user = us.get(username);
//                        } catch (Exception ex) {
//                            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
//                        }

                        userOne.setResetPasswordUUID(uuid);
                        try {
                            us.update(userOne, "", "");
                        } catch (Exception ex) {
                            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Could not update that user.");
                        }
                        
                try {
                    as.resetPassword(userNameAdd, path, link);
                } catch (Exception ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Password could not be reset.");
                }
                }
                
                session.setAttribute("adminCheck", "Please check your email.");
                //getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                response.sendRedirect("login");
                return;
            
        } else if(action != null && action.equals("Cancel")){
            response.sendRedirect("login");
            return;
        }
           
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        
    }

}
