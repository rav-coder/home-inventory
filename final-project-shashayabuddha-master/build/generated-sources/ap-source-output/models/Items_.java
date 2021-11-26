package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Categories;
import models.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-26T14:11:20")
@StaticMetamodel(Items.class)
public class Items_ { 

    public static volatile SingularAttribute<Items, Users> owner;
    public static volatile SingularAttribute<Items, Integer> itemID;
    public static volatile SingularAttribute<Items, String> itemName;
    public static volatile SingularAttribute<Items, Double> price;
    public static volatile SingularAttribute<Items, Categories> category;

}