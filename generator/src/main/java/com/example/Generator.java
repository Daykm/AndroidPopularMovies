package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.PropertyType;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.example.daykm.popmovies.greendao");

        Entity entity = schema.addEntity("FavoriteMovie");
        entity.addIdProperty();
        Index index = new Index();
        Property property = entity.addIntProperty("movieid").getProperty();
        index.addProperty(property);
        index.makeUnique();
        entity.addIndex(index);

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }
}
