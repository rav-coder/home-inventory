/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoriesDB;
import java.util.List;
import models.Categories;

/**
 *
 * @author srvad
 */
public class CategoryService {
    public List<Categories> getAll() throws Exception {
        CategoriesDB itemList = new CategoriesDB();
        List<Categories> categories = itemList.getAll();
        return categories;
    }
    
    public Categories get(Integer catID) throws Exception {
        CategoriesDB categoryDB = new CategoriesDB();
        Categories category = categoryDB.get(catID);
        return category;
    }
    
    public void insert(Categories category) throws Exception {
        CategoriesDB itemDB = new CategoriesDB();
        itemDB.insert(category);
    }
    
    public void delete(Categories category) throws Exception {
        CategoriesDB itemDB = new CategoriesDB();
        itemDB.delete(category);
    }
    
    public void update(Categories category) throws Exception {
        CategoriesDB itemDB = new CategoriesDB();
        itemDB.update(category);
    }
}
