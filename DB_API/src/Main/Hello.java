package Main;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/main")
public class Hello {

	private MySqlCon mySqlCon;

	public Hello(){
		mySqlCon = new MySqlCon();
	}

  @Path("/testQuery")
  @GET
  @Produces("text/plain")
  public String sayPlainTextHello() {
  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_projet");

  	try{
  		if (result.isBeforeFirst()){
  			while (result.next()){
  				System.out.println("Élève " + result.getInt(1) + "   Nom: " + result.getString(2) + "   Mot de Passe: " + result.getString(3));
  			}
  		}
  		else{
  			System.out.println("Aucun résultat");
  		}
		}
  	catch (SQLException e){
			e.printStackTrace();
		}

	  return "Hello Jersey Plain";
  }

  @Path("/getUser/{user}/{password}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getUser(@PathParam("user") String username,@PathParam("password") String password) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_utilisateur WHERE nom = \'" + username + "\' AND mots_de_passe = \'" + password + "\'");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	while (result.next()){
  		JsonObject obj = new JsonObject();

  		obj.addProperty("ID", result.getInt(1));
  		obj.addProperty("Name", result.getString(2));
  		obj.addProperty("Password", result.getString(3));

  		jsonArr.add(obj);
  	}

  	return gson.toJson(jsonArr);
  }


}
