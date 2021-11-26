/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.UserService;

/**
 *
 * @author srvad
 */
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        session.invalidate();

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        UserService us = new UserService();

        String userNameL = (String) request.getParameter("userNameL");
        session.setAttribute("userNameL", userNameL);
        
        String passWordL = (String) request.getParameter("passWordL");
        session.setAttribute("passWordL", passWordL);
        
        List<Users> users = new ArrayList();
        
        boolean validUser = false;
        boolean validAdmin = false;
        boolean activeUser = false;
        
        try {
            users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not get the users from the database.");
        }
        
        for(Users e: users){
            if(e.getUsername().equals(userNameL) && e.getPassword().equals(passWordL) ){
                if(e.getIsAdmin()){
                    validAdmin = true;
                }
                else if(!e.getIsAdmin()){
                    validUser = true;
                    if(e.getActive())
                        activeUser = true;
                }
            }
        }
        
        if(validAdmin){
            request.setAttribute("addView", true);
            response.sendRedirect("admin");
            return;
        }
        
        if(validUser){
            if(activeUser){
                response.sendRedirect("inventory");
                return;
            }
            request.setAttribute("nonActive", true);
            session.invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        if(!validAdmin && !validUser){
            request.setAttribute("invalidLogin", true);
            session.invalidate();
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
    }

}
