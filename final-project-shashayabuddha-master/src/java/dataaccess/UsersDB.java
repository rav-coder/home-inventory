/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.Users;

/**
 *
 * @author srvad
 */
public class UsersDB {
    
    public List<Users> getAll() throws Exception {
        List<Users> users;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            return users;
        }finally{
            em.close();
        }
    }
    
    public Users get(String username) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Users user = em.find(Users.class, username);
            // System.out.println("first name: " + note.getOwner().getFirstName());
            // get all notes of the same owner as that note
            // List<Note> notes = note.getOwner().getNoteList();
            return user;
        } finally { 
            em.close();
        }
    }
    
    public Users getByUUID(String uuid) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
//        try{
//            Users user = em.find(Users.class, uuid);
//            return user;
//        }finally{
//            em.close();
//        }

        
//        try{
//            Query q = em.createNamedQuery("Users.findByResetPasswordUUID", Users.class);
//            q.setParameter(0, uuid);
//            return (Users) q.getSingleResult();
//        }finally{
//            em.close();
//        }
        
        try{
            Query q = em.createNamedQuery("Users.findByResetPasswordUUID");
            q.setParameter("resetPasswordUUID", uuid);
            return (Users) q.getSingleResult();
        }finally{
            em.close();
        }
        
        
    }
    
    public void insert(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            if (!em.contains(user)) {
                user = em.merge(user);
            }
            em.persist(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
   public void delete(Users user, String username, String lastName) throws Exception {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            if (!em.contains(user)) {
                user = em.merge(user);
            }
            em.remove(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
        
        
    }
   
   public void update(Users user, String username, String lastName) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
}
