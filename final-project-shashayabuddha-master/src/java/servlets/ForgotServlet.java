/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
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
public class ForgotServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("uuid") == null){
            getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
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
        
        AccountService as = new AccountService();
        String path = getServletContext().getRealPath("/WEB-INF");
        UserService us = new UserService();
        Users user = new Users();
        String uuidC = "";
        HttpSession session = request.getSession();

        //String urlHere = request.getRequestURL().toString();
        
        if(request.getParameter("uuid") != ""){
            uuidC = request.getParameter("uuid");
            String password = request.getParameter("nPassword");
            
            as.changePassword(uuidC, password);
            response.sendRedirect("login");
            return;
        }


            String url = request.getRequestURL().toString();
        
            String uuid = UUID.randomUUID().toString();
            session.setAttribute("uuid", uuid);

            String link = url + "?uuid=" + uuid;

            String username = request.getParameter("fEmail");

            try {
                user = us.get(username);
            } catch (Exception ex) {
                Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            user.setResetPasswordUUID(uuid);
            try {
                us.update(user, "", "");
            } catch (Exception ex) {
                Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("User could not be updated.");
            }

            String fEmail = "";
            fEmail = request.getParameter("fEmail");

            try {
                if(as.resetPassword(fEmail, path, link)){
                    request.setAttribute("emailConfirm", true);
                    response.sendRedirect("login");
                    return;
                }
                else{
                    request.setAttribute("emailCheck", true);
                    getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("There is a problem with resetting password.");
            }


     
    }

}

