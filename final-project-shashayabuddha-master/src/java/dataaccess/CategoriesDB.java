/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Categories;

/**
 *
 * @author srvad
 */
public class CategoriesDB {
    
    public List<Categories> getAll() throws Exception {
        List<Categories> categories;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            categories = em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
            return categories;
        }finally{
            em.close();
        }
    }
    
    public Categories get(Integer catID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Categories category = em.find(Categories.class, catID);
            return category;
        } finally { 
            em.close();
        }
    }
    
    public void insert(Categories category) throws Exception{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            if (!em.contains(category)) {
                category = em.merge(category);
            }
            em.persist(category);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Categories category) throws Exception{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            if (!em.contains(category)) {
                category = em.merge(category);
            }
            em.remove(category);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Categories category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(category);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
}
