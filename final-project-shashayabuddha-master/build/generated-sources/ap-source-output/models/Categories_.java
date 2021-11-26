package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Items;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-26T14:11:20")
@StaticMetamodel(Categories.class)
public class Categories_ { 

    public static volatile ListAttribute<Categories, Items> itemsList;
    public static volatile SingularAttribute<Categories, String> categoryName;
    public static volatile SingularAttribute<Categories, Integer> categoryID;

}