package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGen {
    private Schema schema;
    private static final int VERSION = 25;

    public static void main(String[] args) {
        new DaoGen().start();
    }

    public void start() {
        schema = new Schema(VERSION, "com.idzodev.pokemonapp.domain.entities");
        schema.enableKeepSectionsByDefault();
        generateEntity();

        try {
            new DaoGenerator().generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entity generateEntity(){
        Entity entity = schema.addEntity("MainEntity");
        entity.addIdProperty();
        entity.addStringProperty("name");
        entity.addIntProperty("icon");
        entity.addIntProperty("power");
        entity.addIntProperty("type");
        entity.addDoubleProperty("latitude");
        entity.addDoubleProperty("longitude");
        return entity;
    }

}
