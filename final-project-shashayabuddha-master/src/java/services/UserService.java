/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UsersDB;
import java.util.ArrayList;
import java.util.List;
import models.Items;
import models.Users;

/**
 *
 * @author srvad
 */
public class UserService {
    public List<Users> getAll() throws Exception {
        UsersDB userList = new UsersDB();
        List<Users> users = userList.getAll();
        return users;
    }
    
    public Users get(String username) throws Exception {
        UsersDB userDB = new UsersDB();
        Users user = userDB.get(username);
        return user;
    }
    
    public Users getByUUID(String uuid) throws Exception {
        UsersDB userDB = new UsersDB();
        Users user = userDB.getByUUID(uuid);
        return user;
    }
    
     public void deleteItemsUser(String username) throws Exception {
        UsersDB userDB = new UsersDB();
        Users user = userDB.get(username);
        InventoryService is = new InventoryService();
        List<Items> items = user.getItemsList();
        for(Items e: items){
            is.delete(e.getItemID());
        }
        
        user.setItemsList(new ArrayList());
     }
    
    public void delete(String username, String lastName) throws Exception {
        UsersDB userDB = new UsersDB();
        Users user = userDB.get(username);
        userDB.delete(user,username, lastName);
    }
    
    public void update(Users user, String username, String lastName) throws Exception {
        UsersDB userDB = new UsersDB();
        userDB.update(user, username, lastName);
    }
    
    public void insert(Users user) throws Exception {
        UsersDB userDB = new UsersDB();
        userDB.insert(user);
    }
}
