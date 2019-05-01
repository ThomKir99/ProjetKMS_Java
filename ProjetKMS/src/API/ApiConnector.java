package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.*;
import com.sun.javafx.webkit.ThemeClientImpl;

import Entity.Carte.Carte;
import Entity.Carte.Dependance;
import Entity.Group.Group;
import Entity.Projet.Project;
import Entity.User.Permission;
import User.Utilisateur;
import javafx.scene.paint.Color;


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
    InputStream stream = request.getInputStream();
    JsonElement root = jp.parse(new InputStreamReader(stream));
    if (!root.isJsonNull()){
      JsonArray  rootarray = root.getAsJsonArray();
      JsonObject rootobj = rootarray.get(0).getAsJsonObject();
      int userID = Integer.valueOf(rootobj.get("ID").toString());
      String userName = rootobj.get("Name").toString();
      userName = userName.replace("\"", "");
      Utilisateur user = new Utilisateur(userID,userName);

      return user;
    }
    return null;
	}

	public ArrayList<Utilisateur> getAllUser() throws IOException{
    String sURL = this.baseURL + "getAllUser";
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    JsonParser jp = new JsonParser();
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    JsonArray  rootarray = root.getAsJsonArray();
    ArrayList<Utilisateur> userList =  new ArrayList<Utilisateur>();

    if (rootarray.size() > 0){
      for (JsonElement obj : rootarray){
        int userID = Integer.valueOf(obj.getAsJsonObject().get("ID").toString());
        String userName = obj.getAsJsonObject().get("Name").toString();

        userName = removeQuote(userName);
        userList.add(new Utilisateur(userID,userName));
      }
    }
    else{
    	userList = null;
    }
		return userList;
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
        String colorProject = obj.getAsJsonObject().get("color_project").toString();
        userName = removeQuote(userName);
        colorProject  =removeQuote(colorProject);
        projectList.add(new Project(projectID,userName,colorProject));
      }
    }
    else{
    	projectList = null;
    }

		return projectList;
	}

	public ArrayList<Group> groupList(int projectID) throws IOException{

    String sURL = this.baseURL +"getGroups/" + projectID;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){
      ArrayList<Group> groupList =  new ArrayList<Group>();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){
        int groupID = Integer.valueOf(obj.getAsJsonObject().get("groupId").toString());
        String groupName = obj.getAsJsonObject().get("groupName").toString();
        int groupOrder = Integer.valueOf(obj.getAsJsonObject().get("order_in_project").toString());
        boolean isGroupOfCompletion = Boolean.valueOf(obj.getAsJsonObject().get("completion").toString());
        groupName = removeQuote(groupName);
        groupList.add(new Group(groupID,groupName,groupOrder,isGroupOfCompletion));

      }
  		return groupList;
    }
    else{
    	return null;
    }
	}

	public ArrayList<Carte> carteList(int groupId)  throws IOException{
    String sURL = this.baseURL +"getCartes/" + groupId;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){
      ArrayList<Carte> carteList =  new ArrayList<Carte>();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){
        int carteID = Integer.valueOf(obj.getAsJsonObject().get("carteId").toString());
        int carteOrder = Integer.valueOf(obj.getAsJsonObject().get("carteOrder").toString());
        String carteName = obj.getAsJsonObject().get("carteName").toString();
        String carteDesc = obj.getAsJsonObject().get("carteDesc").toString();
        boolean carteComplete = Boolean.parseBoolean(obj.getAsJsonObject().get("carteComplete").toString());
        int carteGroupId= Integer.valueOf(obj.getAsJsonObject().get("id_groupe").toString());
        carteName = removeQuote(carteName);
        carteDesc = removeQuote(carteDesc);

        carteList.add(new Carte(carteID,carteName,carteDesc,carteOrder,
        		carteComplete,carteGroupId));
      }
  		return carteList;
    }
    else{
    	return null;
    }
	}

  public void deleteProject(int projectID) throws IOException{
    String sURL = this.baseURL +"deleteProject/" + projectID;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.setDoOutput(false);
    request.connect();
    request.getInputStream();
  }

  public void deleteGroup(int groupID) throws IOException{
    String sURL = this.baseURL +"deleteGroup/" + groupID;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.setDoOutput(false);
    request.connect();
    request.getInputStream();
  }

  public void deleteCarte(int carteID) throws IOException{
    String sURL = this.baseURL +"deleteCarte/" + carteID;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.setDoOutput(false);
    request.connect();
    request.getInputStream();
  }

  public void modifyProject(Project currentProject) throws IOException{
  	Project dbProject = new Project();
    String sURL = this.baseURL +"getSingleProject/" + currentProject.getId();
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

    	int projectID = -1;
    	String projectName = "Something went wrong";
    	String projectColor = "#FFFFFF";
      for (JsonElement obj : rootarray){
        projectID = Integer.valueOf(obj.getAsJsonObject().get("projectID").toString());;
        projectName = obj.getAsJsonObject().get("projectName").toString();
        projectColor= obj.getAsJsonObject().get("color_project").toString();

        projectName = removeQuote(projectName);
        projectColor = removeQuote(projectColor);

      }
      dbProject = new Project(projectID,projectName,projectColor);
    }

    if (!currentProject.isEqualTo(dbProject)){
    	//Project As Been Modified
    	updateMenuProject(currentProject);
    }

  }

  public Carte getSingleCarte(int carteId) throws IOException{
    String sURL = this.baseURL +"getSingleCarte/" + carteId;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){

      Carte laCarte =  new Carte();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){

        int carteID = Integer.valueOf(obj.getAsJsonObject().get("carteID").toString());
        String carteName = obj.getAsJsonObject().get("carteName").toString();
        carteName = removeQuote(carteName);
        laCarte = new Carte(carteID,carteName);

      }
  		return laCarte;
    }
    else{
    	return null;
    }
  }

  public Group getSingleGroup(int groupId) throws IOException{
    String sURL = this.baseURL +"getSingleGroup/" + groupId;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){

      Group leGroup =  new Group();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){

        int groupID = Integer.valueOf(obj.getAsJsonObject().get("groupID").toString());
        String groupName = obj.getAsJsonObject().get("groupName").toString();
        int groupOrder =  Integer.valueOf(obj.getAsJsonObject().get("order_in_project").toString());
        boolean isGroupOfCompletion = Boolean.valueOf(obj.getAsJsonObject().get("completion").toString());
        groupName = removeQuote(groupName);
        leGroup = new Group(groupID,groupName,groupOrder,isGroupOfCompletion);

      }
  		return leGroup;
    }
    else{
    	return null;
    }
  }

  public Project getSingleProject(int projectId) throws IOException{
    String sURL = this.baseURL +"getSingleProject/" + projectId;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){

      Project leProjet =  new Project();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){

        int projectID = Integer.valueOf(obj.getAsJsonObject().get("projectID").toString());
        String projectName = obj.getAsJsonObject().get("projectName").toString();
        String colorProject = obj.getAsJsonObject().get("color_project").toString();
        projectName = removeQuote(projectName);
        colorProject = removeQuote(colorProject);
        leProjet = new Project(projectID,projectName,colorProject);

      }
  		return leProjet;
    }
    else{
    	return null;
    }
  }


  public void modifyGroup(Group currentGroup) throws IOException{
  	Group dbGroup = new Group();
    String sURL = this.baseURL +"getSingleGroup/" + currentGroup.getId();
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

    	int groupID = -1;
    	String groupName = "Something went wrong";
    	int groupOrder = -1;
    	boolean isGroupOfCompletion=false;
      for (JsonElement obj : rootarray){
        groupID = Integer.valueOf(obj.getAsJsonObject().get("groupID").toString());
        groupName = obj.getAsJsonObject().get("groupName").toString();
        groupOrder = Integer.valueOf(obj.getAsJsonObject().get("order_in_project").toString());
        isGroupOfCompletion = Boolean.valueOf(obj.getAsJsonObject().get("completion").toString());
        groupName = removeQuote(groupName);

      }
      dbGroup = new Group(groupID,groupName,groupOrder,isGroupOfCompletion);
    }

    if (!currentGroup.isEqualTo(dbGroup)){
    	//Project As Been Modified
    	updateGroup(currentGroup);
    }

  }

  public void modifyCarte(Carte currentCarte) throws IOException{
  	Carte dbCarte = new Carte();
    String sURL = this.baseURL +"getSingleCarte/" + currentCarte.getId();
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

    	int carteID = -1;
    	String carteName = "Something went wrong";
      for (JsonElement obj : rootarray){
      	carteID = Integer.valueOf(obj.getAsJsonObject().get("carteID").toString());
      	carteName = obj.getAsJsonObject().get("carteName").toString();

      	carteName = removeQuote(carteName);

      }
      dbCarte = new Carte(carteID,carteName);
    }

    if (!currentCarte.isEqualTo(dbCarte)){
    	//Project As Been Modified
    	updateCarte(currentCarte);
    }

  }


  private void updateMenuProject(Project currentProject) throws IOException{
  	Gson gson = new Gson();
  	String projectJson = gson.toJson(currentProject);
    String sURL = this.baseURL +"updateProject";
    URL url = new URL(sURL);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.setRequestProperty("Content-Type", "application/json");
    request.setRequestMethod("POST");
    request.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
    wr.write(projectJson);
    wr.flush();
    wr.close();
    request.connect();
    request.getInputStream();
  }

  private void updateCarte(Carte currentCarte)throws IOException{
  	Gson gson = new Gson();
  	String projectJson = gson.toJson(currentCarte);
    String sURL = this.baseURL +"updateCarte";
    URL url = new URL(sURL);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.setRequestProperty("Content-Type", "application/json");
    request.setRequestMethod("POST");
    request.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
    wr.write(projectJson);
    wr.flush();
    wr.close();
    request.connect();
    request.getInputStream();
  }

  private void updateGroup(Group currentGroup) throws IOException{
  	Gson gson = new Gson();
  	String groupJson = gson.toJson(currentGroup);
    String sURL = this.baseURL +"updateGroup";
    URL url = new URL(sURL);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.setRequestProperty("Content-Type", "application/json");
    request.setRequestMethod("POST");
    request.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
    wr.write(groupJson);
    wr.flush();
    wr.close();

    request.connect();
    request.getInputStream();
  }
  public void saveCompletionGroup(Group currentGroup)throws IOException{
	  Gson gson = new Gson();
	  	String groupJson = gson.toJson(currentGroup);
	    String sURL = this.baseURL +"saveCompletionGroup";
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setRequestProperty("Content-Type", "application/json");
	    request.setRequestMethod("POST");
	    request.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
	    wr.write(groupJson);
	    wr.flush();
	    wr.close();

	    request.connect();
	    request.getInputStream();
  }


  private String removeQuote(String theString){
  	theString = theString.substring(0, theString.length() -1);
  	theString = theString.substring(1, theString.length());
  	return theString;
  }

  public Project createProject(int userId) throws IOException{
    return getProjectInfo(userId);
  }

  public Group createGroup(int projectId) throws IOException, InterruptedException{
    return getGroupInfo(projectId);
  }

  public Carte createCarte(int groudId) throws IOException, InterruptedException{
    return getCarteInfo(groudId);
  }

  private Carte getCarteInfo(int groudId) throws IOException, InterruptedException{
    String sURL = this.baseURL + "newCarte/" + groudId ;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();

    request.connect();

    if (request.getContent() != null){

      Carte leCarte =  new Carte();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){

        int carteId = Integer.valueOf(obj.getAsJsonObject().get("carteID").toString());
        String carteName = obj.getAsJsonObject().get("carteName").toString();


        carteName = removeQuote(carteName);
        leCarte = new Carte(carteId,carteName);

      }
  		return leCarte;
    }
    else{
    	return null;
    }
  }

  private Group getGroupInfo(int projectId) throws IOException, InterruptedException{
    String sURL = this.baseURL + "newGroup/" + projectId ;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){

      Group leGroup =  new Group();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){

        int groupId = Integer.valueOf(obj.getAsJsonObject().get("groupID").toString());
        String groupName = obj.getAsJsonObject().get("groupName").toString();
        int groupOrder = Integer.valueOf(obj.getAsJsonObject().get("order_in_project").toString());
        boolean isGroupOfCompletion = Boolean.valueOf(obj.getAsJsonObject().get("completion").toString());
        groupName = removeQuote(groupName);
        leGroup = new Group(groupId,groupName,groupOrder,isGroupOfCompletion);

      }
  		return leGroup;
    }
    else{
    	return null;
    }
  }

  private Project getProjectInfo(int userId) throws IOException{
    String sURL = this.baseURL + "newProject/" + userId ;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){
    	Project leProjet =  new Project();
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();

      for (JsonElement obj : rootarray){
        int projectId = Integer.valueOf(obj.getAsJsonObject().get("projectID").toString());
        String projectName = obj.getAsJsonObject().get("projectName").toString();
        String colorProject = obj.getAsJsonObject().get("color_project").toString();
        projectName = removeQuote(projectName);
        leProjet = new Project(projectId,projectName,colorProject);

      }
  		return leProjet;
    }
    else{
    	return null;
    }
  }

  public void saveCarteOrder(Group currentGroup) throws IOException{
		Gson gson = new Gson();
	  	String projectJson = gson.toJson(currentGroup.getCartes());
	    String sURL = this.baseURL +"saveCarteOrder";
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setRequestProperty("Content-Type", "application/json");
	    request.setRequestMethod("POST");
	    request.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
	    wr.write(projectJson);
	    wr.flush();
	    wr.close();
	    request.connect();
	    request.getInputStream();
  }

  public void saveGroupeOrder(Project currentProject) throws IOException{
		Gson gson = new Gson();
	  	String projectJson = gson.toJson(currentProject.getGroups());
	    String sURL = this.baseURL +"saveGroupeOrder";
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setRequestProperty("Content-Type", "application/json");
	    request.setRequestMethod("POST");
	    request.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
	    wr.write(projectJson);
	    wr.flush();
	    wr.close();
	    request.connect();
	    request.getInputStream();
}

  public void changeCarteGroupId(Carte carte) throws IOException{
		Gson gson = new Gson();
	  	String projectJson = gson.toJson(carte);
	    String sURL = this.baseURL +"changeCarteGroupId";
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setRequestProperty("Content-Type", "application/json");
	    request.setRequestMethod("POST");
	    request.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
	    wr.write(projectJson);
	    wr.flush();
	    wr.close();
	    request.connect();
	    request.getInputStream();
}
  public void changeProjectColor(Project project) throws IOException{
		Gson gson = new Gson();
	  	String projectJson = gson.toJson(project);
	    String sURL = this.baseURL +"changerColorProject";
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setRequestProperty("Content-Type", "application/json");
	    request.setRequestMethod("POST");
	    request.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
	    wr.write(projectJson);
	    wr.flush();
	    wr.close();
	    request.connect();
	    request.getInputStream();
  }
  private Project getProjectOpenedTime(int userId) throws IOException{
	    String sURL = this.baseURL + "OpenedProject/" + userId ;
	    URL url = new URL(sURL);
	    URLConnection request = url.openConnection();
	    request.connect();

	    if (request.getContent() != null){
	        Project leProjet =  new Project();
	      	JsonParser jp = new JsonParser();
	      	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
	      	JsonArray rootarray = root.getAsJsonArray();

	     	 for (JsonElement obj : rootarray){
	             int projectId = Integer.valueOf(obj.getAsJsonObject().get("projectID").toString());
	             String projectName = obj.getAsJsonObject().get("projectName").toString();
	             Date projectDateOpened = Date.valueOf(obj.getAsJsonObject().get("date_projet_ouvert").toString());
	             leProjet = new Project(projectId,projectName,projectDateOpened);
	     	 }
	     	return leProjet;
	    }
	    else{
	    	return null;
	    }
  }

  public void setDateOpenProject(Project currentProject) throws IOException{
	  	Gson gson = new Gson();
	  	String projectJson = gson.toJson(currentProject);
	    String sURL = this.baseURL +"updateProjectDate";

	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setRequestProperty("Content-Type", "application/json");
	    request.setRequestMethod("PUT");
	    request.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
	    wr.write(projectJson);
	    wr.flush();
	    wr.close();
	    request.connect();
	    request.getInputStream();
	  }

  public String getPermission(int projectID,int userID) throws IOException{
    String sURL = this.baseURL + "getPermission/" + projectID + "/" + userID;
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();
    request.connect();

    if (request.getContent() != null){

    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonArray rootarray = root.getAsJsonArray();
    	String permission = "";

      for (JsonElement obj : rootarray){
        permission = obj.getAsJsonObject().get("permission").toString();
        permission = removeQuote(permission);
      }
  		return permission;
    }
    else{
    	return null;
    }
  }

	public void createDependance(Dependance dependance) throws IOException{
		Gson gson = new Gson();
	  	String theDependance = gson.toJson(dependance);
	    String sURL = this.baseURL +"createDependance";
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setRequestProperty("Content-Type", "application/json");
	    request.setRequestMethod("POST");
	    request.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
	    wr.write(theDependance);
	    wr.flush();
	    wr.close();
	    request.connect();
	    request.getInputStream();
	}

  public void deletePermission(Permission permission) throws IOException{
  	Gson gson = new Gson();
  	String projectJson = gson.toJson(permission);
    String sURL = this.baseURL +"deletePermission";
    URL url = new URL(sURL);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.setRequestProperty("Content-Type", "application/json");
    request.setRequestMethod("POST");
    request.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
    wr.write(projectJson);
    wr.flush();
    wr.close();
    request.connect();
    request.getInputStream();
  }

  public void insertPermission(Permission permission) throws IOException{
  	Gson gson = new Gson();
  	String projectJson = gson.toJson(permission);
    String sURL = this.baseURL +"insertPermission";
    URL url = new URL(sURL);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.setRequestProperty("Content-Type", "application/json");
    request.setRequestMethod("POST");
    request.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
    wr.write(projectJson);
    wr.flush();
    wr.close();
    request.connect();
    request.getInputStream();
  }

  public ArrayList<Dependance> getDepandance() throws IOException{
      String sURL = this.baseURL +"getDepandance";
      URL url = new URL(sURL);
      URLConnection request = url.openConnection();
      request.connect();

      JsonParser jp = new JsonParser();
      JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
      JsonArray  rootarray = root.getAsJsonArray();
      ArrayList<Dependance> dependanceList =  new ArrayList<Dependance>();

      if (rootarray.size() > 0){
        for (JsonElement obj : rootarray){
          int idCarteDependante = Integer.valueOf(obj.getAsJsonObject().get("id_carte_depandante").toString());
          int idCarteDeDependance = Integer.valueOf(obj.getAsJsonObject().get("id_carte_de_depandance").toString());

          dependanceList.add(new Dependance(idCarteDependante, idCarteDeDependance));
        }
      }

          return dependanceList;
      }

public void saveCarteCompletion(List<Carte> list) throws IOException {
	Gson gson = new Gson();
  	String projectJson = gson.toJson(list);
    String sURL = this.baseURL +"saveCarteCompletion";
    URL url = new URL(sURL);
    HttpURLConnection request = (HttpURLConnection) url.openConnection();
    request.setRequestProperty("Content-Type", "application/json");
    request.setRequestMethod("POST");
    request.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(request.getOutputStream());
    wr.write(projectJson);
    wr.flush();
    wr.close();
    request.connect();
    request.getInputStream();
}

}
