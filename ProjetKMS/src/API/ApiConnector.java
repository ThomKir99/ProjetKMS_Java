package API;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Entity.Group.Group;
import Entity.Projet.Project;
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
    userName = userName.replace("\"", "");

    return new Utilisateur(userID,userName);
	}



	public ArrayList<Project> projectList(int userID) throws IOException{
    String sURL = this.baseURL +"getProjects/" + userID;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    JsonParser jp = new JsonParser();
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    JsonArray  rootarray = root.getAsJsonArray();
    ArrayList<Project> projectList =  new ArrayList<Project>();

    if (rootarray.size() > 0){
      for (JsonElement obj : rootarray){

        int projectID = Integer.valueOf(obj.getAsJsonObject().get("projectID").toString());
        String userName = obj.getAsJsonObject().get("projectName").toString();
        userName = userName.replace("\"", "");
        projectList.add(new Project(projectID,userName));
      }
    }

		return projectList;
	}

	public ArrayList<Group> groupList(int projectID) throws IOException{
    String sURL = this.baseURL +"getGroups/" + projectID;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    JsonParser jp = new JsonParser();
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    JsonArray  rootarray = root.getAsJsonArray();
    ArrayList<Group> groupList =  new ArrayList<Group>();

    if (rootarray.size() > 0){
      for (JsonElement obj : rootarray){

        int groupID = Integer.valueOf(obj.getAsJsonObject().get("groupId").toString());
        String groupName = obj.getAsJsonObject().get("groupName").toString();
        groupName = groupName.replace("\"", "");
        groupList.add(new Group(groupID,groupName));
      }
    }

		return groupList;
	}

}
