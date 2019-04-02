package com.vogella.jersey.first;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.DriverManager;
import java.beans.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rest")
public class JSONTest {    
    /*
    ... Other calls
    */
    private String toJson(Object entity) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz")
                .setPrettyPrinting()
                .create();
        String result = gson.toJson(entity);
        return result.replace("\\\"", "");
    }

    @GET
    @Path("/GSON")
    @Produces(MediaType.APPLICATION_JSON)
    public Entity getEntity() {
        
        String result = callProcedure();
       
        Entity entity;
			entity = new Entity(result,1);	
        return entity;
    }
    
    private  String callProcedure() {
    	ResultSet rs = MysqlCon.main();
    	/*ResultSet rs =null;
    	
        String jdbcUrl = "mysql://C:/Windows/CBA/MySQLServer/data/sys";
        String username = "root";
        String password = "";
        String query = "{CALL getEntity()}";
        String resultat ="";
        String[] test = null;
        test[0] = "";
        try{  
        		
        	}catch(Exception e)
        	{ 
        		System.out.println(e);
        	}*/
    	
		return rs.toString();
     }
}