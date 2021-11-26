/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.ItemsDB;
import java.util.List;
import models.Items;

/**
 *
 * @author srvad
 */
public class InventoryService {
    public List<Items> getAll(String username) throws Exception {
        ItemsDB itemList = new ItemsDB();
        List<Items> items = itemList.getAll(username);
        return items;
    }
    
    public Items get(Integer ItemID) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        Items item = itemDB.get(ItemID);
        return item;
    }
    
    public void delete(Integer ItemID) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        
        Items item = itemDB.get(ItemID);
        itemDB.delete(item);
    }
    
    public void update(Items item) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        itemDB.update(item);
    }
    
    public void insert(Items item) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        itemDB.insert(item);
    }
    
    public Integer totalRows() throws Exception {
        ItemsDB itemDB = new ItemsDB();
        return itemDB.totalRows();
    }
}
