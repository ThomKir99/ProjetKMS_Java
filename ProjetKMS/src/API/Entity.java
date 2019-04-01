package com.vogella.jersey.first;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Entity {

  
    private String name;

    private int number;

    

    public Entity() {
    	this.name = "new entity";
    }
    
    public Entity(String name, int number) {
    	this.name=name;
    	this.number = number;
    }

    /*
    ... 
    ... Gets and Sets
    ...
    */

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this, Entity.class);
        return json;
    }
    public String getName() {
 
    	return  this.name;
    }
    public int getNumber() {
    	 
    	return  this.number;
    }
}