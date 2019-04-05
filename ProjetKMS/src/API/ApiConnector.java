package API;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import User.Utilisateur;


public class ApiConnector {
	private String baseURL;

	public ApiConnector() {

		this.baseURL = "http://localhost:8080/DB_API/rest/main/";

	}

	public Utilisateur getUser(String nom,String password) throws IOException{
    String sURL = this.baseURL +"getUser/" + nom + "/" + password;

    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    JsonParser jp = new JsonParser();
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

    JsonArray  rootarray = root.getAsJsonArray();
    JsonObject rootobj = rootarray.get(0).getAsJsonObject();
    int userID = Integer.valueOf(rootobj.get("ID").toString());
    String userName = rootobj.get("Name").toString();

    return new Utilisateur(userID,userName);
	}

}
