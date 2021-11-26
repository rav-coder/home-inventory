/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.Items;

/**
 *
 * @author srvad
 */
public class ItemsDB {

    public List<Items> getAll(String username) throws Exception {
        List<Items> items;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            //items = em.createQuery("select i from Items i where i.owner LIKE :username").setParameter("username", username).getResultList(); 
            items = em.createNamedQuery("Items.findAll", Items.class).getResultList();
            return items;
        }finally{
            em.close();
        }
    }
    
    public Items get(Integer ItemID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Items item = em.find(Items.class, ItemID);
            // System.out.println("first name: " + note.getOwner().getFirstName());
            // get all notes of the same owner as that note
            // List<Note> notes = note.getOwner().getNoteList();
            return item;
        } finally { 
            em.close();
        }
    }
    
    public void insert(Items item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            if (!em.contains(item)) {
                item = em.merge(item);
            }
            em.persist(item);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Items item) throws Exception {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            if (!em.contains(item)) {
                item = em.merge(item);
            }
            em.remove(item);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Items item) throws Exception {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(item);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
   
    public Integer totalRows() throws Exception {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
       Integer totalRows = 0;
       
       Query query = em.createQuery("SELECT COUNT(i.itemID) FROM Items i ");
              
       totalRows = (int) ((long) query.getSingleResult());
       
       return totalRows;
   }
    
}
