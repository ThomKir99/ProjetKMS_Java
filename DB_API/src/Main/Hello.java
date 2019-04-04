package Main;

import javax.ws.rs.Path;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.*;

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

  @Path("/json")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  //@Parameters()
  public JsonObject sayJSONHello(String yo) {
  	JsonObject model = Json.createObjectBuilder()
  		   .add("firstName", yo)
  		   .add("lastName", "Java")
  		   .add("age", 18)
  		   .add("streetAddress", "100 Internet Dr")
  		   .add("city", "JavaTown")
  		   .add("state", "JA")
  		   .add("postalCode", "12345")
  		   .add("phoneNumbers", Json.createArrayBuilder()
  		      .add(Json.createObjectBuilder()
  		         .add("type", "mobile")
  		         .add("number", "111-111-1111"))
  		      .add(Json.createObjectBuilder()
  		         .add("type", "home")
  		         .add("number", "222-222-2222")))
  		   .build();
    return model;
  }
}
