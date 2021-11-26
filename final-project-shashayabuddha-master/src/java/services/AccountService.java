package services;

import dataaccess.UsersDB;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import models.Users;

public class AccountService {
    
    public boolean resetPassword(String email, String path, String url) throws Exception{
        UsersDB userDB = new UsersDB();
        
        if(userDB.get(email) == null){
            return false;
        }
        
        Users user = userDB.get(email);
        
        Context env = (Context) new InitialContext().lookup("java:comp/env");
        
            String to = (String) env.lookup("webmail-username");
            //String to =  user.getEmail();
                String subject = "HomenVentory App";
                String template = path + "/emailtemplates/accountinfo.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("username", user.getUsername());
                tags.put("link", url);
                
        try {       
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public boolean changePassword(String uuid, String password) {
        UserService us = new UserService();
        UsersDB userDB = new UsersDB();
        
        try {
            Users user = us.getByUUID(uuid);
            user.setPassword(password);
            user.setResetPasswordUUID(null);
            UsersDB ur = new UsersDB ();
            ur.update(user, "", "");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    
    public boolean forgotPassword(String email, String path) throws Exception{
        UsersDB userDB = new UsersDB();
        
        if(userDB.get(email) == null){
            return false;
        }
        
        Users user = userDB.get(email);
        
        Context env = (Context) new InitialContext().lookup("java:comp/env");
        
            String to = (String) env.lookup("webmail-username");
            //String to =  user.getEmail();
                String subject = "HomenVentory App";
                String template = path + "/emailtemplates/accountinfo.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("username", user.getUsername());
                tags.put("password", user.getPassword());
                
        try {       
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return true;
        
//        User user = userDB.get(email);
//        
//        List <User> users = new ArrayList<>();
//        
//        try {
//            users = UserDB.getAll();
//        } catch (Exception ex) {
//            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        for(User e: users){
//            if(user.equals(e))
//                return true;
//        }
//
//        return false;
    }
    
    
    public Users login(String email, String password, String path) {
        UsersDB userDB = new UsersDB();

        try {
            Users user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", email);
                
//                
//                String body = "Successful login by " + user.getFirstName() + " on " + (new java.util.Date()).toString();
//                GmailService.sendMail(email, "Successful Login", body, false);
                
                
                /*
                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = path + "/emailtemplates/login.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                
                GmailService.sendMail(to, subject, template, tags);
                */
                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
}
