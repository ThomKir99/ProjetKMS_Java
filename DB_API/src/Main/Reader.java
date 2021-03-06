package Main;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("/main")
public class Reader {

	private MySqlCon mySqlCon;

	public Reader(){
		mySqlCon = new MySqlCon();
	}

  @Path("/getUser/{user}/{password}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getUser(@PathParam("user") String username,@PathParam("password") String password) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_utilisateur WHERE nom = \'" + username + "\' AND mots_de_passe = \'" + password + "\'");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
    	while (result.next()){
    		JsonObject obj = new JsonObject();

    		obj.addProperty("ID", result.getInt(1));
    		obj.addProperty("Name", result.getString(2));
    		obj.addProperty("Password", result.getString(3));

    		jsonArr.add(obj);
    	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return null;
  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/createUser")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void createUser(UtilisateurModel user) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("INSERT INTO tbl_utilisateur(nom,mots_de_passe) VALUES (\'" + user.getName() + "\', \'" + user.getPassword() + "\')");
  	mySqlCon.closeConnection();
  }

  @Path("/getProjectWithPermission/{userID}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getProjectWithPermission(@PathParam("userID") String userID) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT id_projet,permission,id_utilisateur FROM tbl_permission WHERE id_utilisateur = \'" + userID + "\'");
  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
    	while (result.next()){
      	ResultSet result1 = mySqlCon.getQueryResult("SELECT * FROM tbl_projet WHERE id_projet = \'" + result.getString(1) + "\' ORDER BY date_projet_ouvert DESC");
				JsonObject obj = new JsonObject();

		  	if (result1.isBeforeFirst()){
		    	while (result1.next()){
	  				obj.addProperty("projectID", result1.getInt(1));
	  				obj.addProperty("projectName", result1.getString(2));
	  				obj.addProperty("color_project", result1.getString(4));
	  				obj.addProperty("date", result1.getString(5));
	  				obj.addProperty("permission", result.getString(2));
		    	}
		    }
				jsonArr.add(obj);
    	}
  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }


  @Path("/getAllUser")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getAllUser() throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT id_utilisateur,nom FROM tbl_utilisateur");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
    	while (result.next()){
    		JsonObject obj = new JsonObject();

    		obj.addProperty("ID", result.getInt(1));
    		obj.addProperty("Name", result.getString(2));

    		jsonArr.add(obj);
    	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return null;
  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getUserWithName")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String getUserWithName(UsernameModel username) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_utilisateur WHERE nom = \'" + username.getUsername() + "\'");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
    	while (result.next()){
    		JsonObject obj = new JsonObject();
    		obj.addProperty("username", result.getString(2));
    		jsonArr.add(obj);
    	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return null;
  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getProjects/{userId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getProjects(@PathParam("userId") String userId) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_projet WHERE id_utilisateur = \'" + userId + "\' ORDER BY date_projet_ouvert DESC");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
			while (result.next()){
				JsonObject obj = new JsonObject();

				obj.addProperty("projectID", result.getInt(1));
				obj.addProperty("projectName", result.getString(2));
				obj.addProperty("color_project", result.getString(4));
				obj.addProperty("date", result.getString(5));

				jsonArr.add(obj);
			}
  	}
  	else {
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getGroups/{projectId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getGroups(@PathParam("projectId") String projectId) throws Exception {
  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_groupe WHERE id_projet = \'" + projectId + "\' order by order_in_project");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
	  	while (result.next()){

	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("groupId", result.getInt(1));
	  		obj.addProperty("groupName", result.getString(2));
	  		obj.addProperty("order_in_project", result.getInt(4));
	  		obj.addProperty("completion", result.getBoolean(5));
	  		jsonArr.add(obj);

	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getCartes/{groupId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getCarte(@PathParam("groupId") String groupId) throws Exception {
  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_carte WHERE id_groupe = \'" + groupId + "\' order by ordre_de_priorite");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
	  	while (result.next()){

	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("carteId", result.getInt(1));
	  		obj.addProperty("carteName", result.getString(2));
	  		obj.addProperty("carteDesc", result.getString(3));
	  		obj.addProperty("carteOrder", result.getInt(4));
	  		obj.addProperty("carteComplete", result.getBoolean(5));
	  		obj.addProperty("id_groupe", result.getInt(6));
	  		jsonArr.add(obj);
	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/deleteProject/{projectID}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public void deleteProject(@PathParam("projectID") String projectID) throws Exception {
	  mySqlCon.openLocalConnection();
		mySqlCon.executeNonQuery("DELETE tbl_depandance FROM tbl_depandance inner join tbl_carte on id_carte_depandante = id_carte "
				+ "inner join tbl_groupe on tbl_carte.id_groupe =tbl_groupe.id_groupe where tbl_groupe.id_projet =\'"+ projectID + "\'");
		mySqlCon.closeConnection();
		mySqlCon.openLocalConnection();
		mySqlCon.executeNonQuery("DELETE tbl_depandance FROM tbl_depandance inner join tbl_carte on id_carte_de_depandance = id_carte "
				+ "inner join tbl_groupe on tbl_carte.id_groupe =tbl_groupe.id_groupe where tbl_groupe.id_projet =\'"+ projectID + "\'");
		mySqlCon.closeConnection();
		mySqlCon.openLocalConnection();
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("DELETE FROM tbl_projet WHERE id_projet = \'" + projectID + "\'");
  	mySqlCon.closeConnection();
  }

  @Path("/deleteGroup/{groupID}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public void deleteGroup(@PathParam("groupID") String groupID) throws Exception {

	mySqlCon.openLocalConnection();
	mySqlCon.executeNonQuery("DELETE tbl_depandance FROM tbl_depandance inner join tbl_carte on id_carte_depandante = id_carte inner join tbl_groupe where tbl_carte.id_groupe =\'"+ groupID +"\'");
	mySqlCon.closeConnection();
	mySqlCon.openLocalConnection();
	mySqlCon.executeNonQuery("DELETE tbl_depandance FROM tbl_depandance inner join tbl_carte on id_carte_de_depandance = id_carte inner join tbl_groupe where tbl_carte.id_groupe =\'"+ groupID +"\'");
	mySqlCon.closeConnection();
	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("DELETE FROM tbl_groupe WHERE id_groupe = \'" + groupID + "\'");
  	mySqlCon.closeConnection();
  }

  @Path("/deleteCarte/{carteID}")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String deleteCarte(@PathParam("carteID") String carteID) throws Exception {
	 Gson gson = new Gson();
	 int size = 0;
	 JsonArray jsonArr = new JsonArray();
	 JsonObject obj = new JsonObject();
	 ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_depandance WHERE id_carte_depandante =\'" + carteID +"\' OR id_carte_de_depandance =\'" + carteID +"\'" );
	 if(result != null){
		  result.last();
		  size = result.getRow();
	  }
	 if(size <= 0 ){
		 mySqlCon.openLocalConnection();
  		 mySqlCon.executeNonQuery("DELETE FROM tbl_carte WHERE id_carte = \'" + carteID + "\'");
  		 mySqlCon.closeConnection();
  		 mySqlCon.openLocalConnection();
  		 mySqlCon.executeNonQuery("DELETE FROM tbl_depandance WHERE id_carte_depandante = \'" + carteID + "\' OR id_carte_de_depandance = \'" + carteID +"\'");
  		 mySqlCon.closeConnection();
  		 obj.addProperty("successful", true);
  		 jsonArr.add(obj);
	 }else{
		 obj.addProperty("successful", false);
  		 jsonArr.add(obj);
	 }

	 mySqlCon.closeConnection();
		return gson.toJson(jsonArr);
  }

  @Path("/getSingleProject/{projectID}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSingleProject(@PathParam("projectID") String projectID) throws Exception {
  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_projet WHERE id_projet = \'" + projectID + "\'");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
	  	while (result.next()){

	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("projectID", result.getInt(1));
	  		obj.addProperty("projectName", result.getString(2));
	  		obj.addProperty("color_project", result.getString(4));
	  		jsonArr.add(obj);
	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getSingleGroup/{groudId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSingleGroup(@PathParam("groudId") String groudId) throws Exception {
  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_groupe WHERE id_groupe = \'" + groudId + "\'");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
	  	while (result.next()){

	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("groupID", result.getInt(1));
	  		obj.addProperty("groupName", result.getString(2));
	  		obj.addProperty("order_in_project", result.getInt(4));
	  		obj.addProperty("completion", result.getBoolean(5));
	  		jsonArr.add(obj);
	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getSingleCarte/{carteId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSingleCarte(@PathParam("carteId") String carteId) throws Exception {
  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_carte WHERE id_carte = \'" + carteId + "\'");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
	  	while (result.next()){

	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("carteID", result.getInt(1));
	  		obj.addProperty("carteName", result.getString(2));
	  		jsonArr.add(obj);
	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/insertPermission")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void insertPermission(PermissionModel permission) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("INSERT INTO tbl_permission (id_projet,id_utilisateur,permission) VALUES("+ permission.getId_projet() +"," + permission.getId_user() + ",\'" + permission.getPermission() + "\');");
  	mySqlCon.closeConnection();
  }

  @Path("/deletePermission")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void deletePermission(PermissionModel permission) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("DELETE FROM tbl_permission WHERE id_projet = \'" + permission.getId_projet() + "\' AND id_utilisateur = \'" + permission.getId_user() + "\'");
  	mySqlCon.closeConnection();
  }

  @Path("/updateProject")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateProject(ProjectModel project) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("UPDATE tbl_projet SET nom_projet = \'"+ project.getName() +"\' WHERE id_projet = \'" + project.getID() + "\'");
  	mySqlCon.closeConnection();
  }

  @Path("/updateGroup")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateGroup(GroupModel group) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("UPDATE tbl_groupe SET nom_groupe = \'"+ group.getName() +"\' WHERE id_groupe = \'" + group.getID() + "\'");
  	mySqlCon.closeConnection();
  }

  @Path("/saveCompletionGroup")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void saveCompletionGroup(GroupModel group) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("UPDATE tbl_groupe SET completion = "+ group.getIsGroupOfCompletion() +" WHERE id_groupe = \'" + group.getID() + "\'");
  	mySqlCon.closeConnection();
  }

  @Path("/updateCarte")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateCarte(CarteModel carte) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("UPDATE tbl_carte SET nom = \'"+ carte.getName() +"\' WHERE id_carte = \'" + carte.getID() + "\'");
  	mySqlCon.closeConnection();
  }
  @Path("/updateDescription")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateDescription(CarteModel carte) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("UPDATE tbl_carte SET description = \'"+ carte.getDescription() +"\' WHERE id_carte = \'" + carte.getID() + "\'");
  	mySqlCon.closeConnection();
  }


  @Path("/newProject/{userId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String newProject(@PathParam("userId") String userId) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("INSERT INTO tbl_projet(nom_projet,id_utilisateur,color_project,date_projet_ouvert) VALUES (\"Insert a name\","+ userId
  			+ ",\'#FFFFFF\'"+ " , NOW())" );
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_projet WHERE id_projet = LAST_INSERT_ID()");
  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){

	  	while (result.next()){

	  		JsonObject obj = new JsonObject();

	  		obj.addProperty("projectID", result.getInt(1));
	  		obj.addProperty("projectName", result.getString(2));
	  		obj.addProperty("color_project", result.getString(4));

	  		jsonArr.add(obj);
	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/newGroup/{projectId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String newGroup(@PathParam("projectId") String projectId) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("INSERT INTO tbl_groupe(nom_groupe,id_projet,order_in_project,completion) VALUES (\"Insert a name\","+ projectId + ",999, false)" );

  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_groupe WHERE id_groupe = LAST_INSERT_ID()");
  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){

	  	while (result.next()){

	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("groupID", result.getInt(1));
	  		obj.addProperty("groupName", result.getString(2));
	  		obj.addProperty("order_in_project", result.getInt(4));
	  		obj.addProperty("completion", result.getBoolean(5));

	  		jsonArr.add(obj);
	  	}
  	}
  	else{

  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/newCarte/{groupId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String newCarte(@PathParam("groupId") String groupId) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("INSERT INTO tbl_carte(nom,description,id_groupe,ordre_de_priorite,complete) VALUES (\"Insert a name\", \"\","+ groupId + ",0,0)" );

  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_carte WHERE id_carte = LAST_INSERT_ID()");
  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){

	  	while (result.next()){

	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("carteID", result.getInt(1));
	  		obj.addProperty("carteName", result.getString(2));
	  		jsonArr.add(obj);
	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getPermission/{projectID}/{userID}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getAllPermission(@PathParam("projectID") String projectID,@PathParam("userID") String userID) throws Exception {
  	mySqlCon.openLocalConnection();

  	ResultSet result = mySqlCon.getQueryResult("SELECT permission FROM tbl_permission WHERE id_projet = \'" + projectID + "\' AND id_utilisateur = \'" + userID + "\'");
  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
	  	while (result.next()){
	  		JsonObject obj = new JsonObject();
	  		obj.addProperty("permission", result.getString(1));
	  		jsonArr.add(obj);
	  	}
  	}
  	else{
  		mySqlCon.closeConnection();
  		return gson.toJson(new JsonArray());
  	}
  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/updateProjectDate")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void setDateOpenProject(ProjectModel project) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("UPDATE tbl_projet SET date_projet_ouvert = NOW() WHERE id_projet = \'" + project.getID() + "\'");
  	mySqlCon.closeConnection();
  }


@Path("/createDependance")
@POST
	@Consumes(MediaType.APPLICATION_JSON)
public void createDependance(DependnaceModel dependance) throws Exception {
		mySqlCon.openLocalConnection();
	mySqlCon.executeNonQuery("INSERT INTO tbl_depandance(id_carte_depandante,id_carte_de_depandance,terminer) VALUES ("+dependance.getIdCarteDeDependance() +","+ dependance.getIdCarteDependante() +","+false + ")");
		mySqlCon.closeConnection();
	}

  @Path("/saveCarteOrder")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void saveCarteOrder(ArrayList<CarteModel> cartes) throws Exception {
  	mySqlCon.openLocalConnection();

  	for(CarteModel carte : cartes ){
  		mySqlCon.executeNonQuery("update tbl_carte set ordre_de_priorite =\'"+ carte.getOrdre_de_priorite() +"\' where id_carte =\'"+carte.getID()+"\'");
  	}

  	mySqlCon.closeConnection();
  }

  @Path("/changeCarteGroupId")
  @POST
  @Produces (MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String changeCarteGroupId(CarteModel carte) throws Exception {
	  Gson gson = new Gson();
	  int size = 0;
	  JsonArray jsonArr = new JsonArray();
	  JsonObject obj = new JsonObject();
	  APIResponse response;
	  response =  new APIResponse();
	  mySqlCon.openLocalConnection();

	  ResultSet group = mySqlCon.getQueryResult("SELECT completion FROM tbl_groupe WHERE id_groupe =\'" +  carte.getGroupId()+"\'" );
	  ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_depandance WHERE id_carte_depandante =\'" +  carte.getID()+"\'  AND terminer = false;" );

	  if(result != null){
		  result.last();
		  size = result.getRow();
	  }

	  while(group.next()){
	  	if(group.getBoolean(1) ){
			  if(size <= 0 ){
				 mySqlCon.executeNonQuery("update tbl_carte set id_groupe =\'"+ carte.getGroupId() +"\' where id_carte =\'"+carte.getID()+"\'");
				 mySqlCon.executeNonQuery("update tbl_carte set complete =\'"+ 1 +"\' where id_carte =\'"+carte.getID()+"\'");
				 mySqlCon.executeNonQuery("update tbl_depandance set terminer =\'"+ 1 +"\' where id_carte_de_depandance =\'"+carte.getID()+"\'");
				 obj.addProperty("successful", true);
				 jsonArr.add(obj);
			  }else{
				  response.successful = false;
				  obj.addProperty("successful", false);
				  jsonArr.add(obj);
			  }
		}else{
	  		mySqlCon.executeNonQuery("update tbl_carte set id_groupe =\'"+ carte.getGroupId() +"\' where id_carte =\'"+carte.getID()+"\'");
			obj.addProperty("successful", true);
			jsonArr.add(obj);
			mySqlCon.executeNonQuery("update tbl_carte set complete =\'"+ 0 +"\' where id_carte =\'"+carte.getID()+"\'");
			mySqlCon.executeNonQuery("update tbl_depandance set terminer =\'"+ 0 +"\' where id_carte_de_depandance =\'"+carte.getID()+"\'");

		}
 }
		mySqlCon.closeConnection();
		return gson.toJson(jsonArr);
  }

  @Path("/saveGroupeOrder")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void saveGroupOrder(ArrayList<GroupModel> groupes) throws Exception {
  	mySqlCon.openLocalConnection();
  	for(GroupModel groupe : groupes ){
  		mySqlCon.executeNonQuery("update tbl_groupe set order_in_project =\'"+ groupe.getOrderInProject() +"\' where id_groupe =\'"+groupe.getID()+"\'");
  	}
  	mySqlCon.closeConnection();
  }

  @Path("/changerColorProject")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void changerColorProject(ProjectModel project) throws Exception {
  	mySqlCon.openLocalConnection();
  	mySqlCon.executeNonQuery("update tbl_projet set color_project =\'"+ project.getHexColor()+"\' where id_projet =\'"+project.getID()+"\'");
  	mySqlCon.closeConnection();
  }

  @Path("/getDepandance")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getDepandance() throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_depandance");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
			while (result.next()){
				JsonObject obj = new JsonObject();
				obj.addProperty("id_carte_depandante", result.getInt(1));
				obj.addProperty("id_carte_de_depandance", result.getInt(2));
				jsonArr.add(obj);

			}

  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getDepandanceForACarte/{idCarte}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getDepandanceFromACarte(@PathParam("projectID") int idCarte) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_depandance where id_carte_depandante = \'" + idCarte + "\'");

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
			while (result.next()){
				JsonObject obj = new JsonObject();
				obj.addProperty("id_carte_depandante", result.getInt(1));
				obj.addProperty("id_carte_de_depandance", result.getInt(2));

				jsonArr.add(obj);

			}

  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/saveCarteCompletion")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void saveCarteCompletion(ArrayList<CarteModel> cartes) throws Exception {
  	mySqlCon.openLocalConnection();

  	for(CarteModel carteModel : cartes ){
  		mySqlCon.executeNonQuery("UPDATE tbl_carte SET complete ="+ carteModel.getIfComplete() +" WHERE id_carte =\'"+carteModel.getID()+"\'");
  	}

  	mySqlCon.closeConnection();
  }


  @Path("/getDepandanceCarteEnfant/{idCarte}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getDepandanceCarte(@PathParam("idCarte") String idCarte) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("SELECT * FROM tbl_depandance WHERE id_carte_depandante =\'" +  idCarte+"\'" );

  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
			while (result.next()){
				JsonObject obj = new JsonObject();
				obj.addProperty("id_carte_depandante", result.getInt(1));
				obj.addProperty("id_carte_de_depandance", result.getInt(2));
				jsonArr.add(obj);

			}

  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getAllCarteDependanteOfThisCarte/{idCarte}")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String getAllCarteDependanteOfThisCarte(@PathParam("idCarte") String idCarte) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("select"
  			+ " id_carte_depandante,"
  			+ " id_carte_de_depandance,"
  			+ " tbl_carte.nom,complete,"
  			+ " tbl_groupe.id_groupe,"
  			+ " tbl_groupe.nom_groupe,"
  			+ " tbl_projet.id_projet,"
  			+ " nom_projet"
  			+ " from tbl_depandance"
  			+ " inner join tbl_carte"
  			+ " on id_carte = tbl_depandance.id_carte_depandante"
  			+ " inner join tbl_groupe"
  			+ " on tbl_carte.id_groupe = tbl_groupe.id_groupe"
  			+ " inner join tbl_projet"
  			+ " on tbl_groupe.id_projet = tbl_projet.id_projet"
  			+ " where id_carte_de_depandance =\'" + idCarte +"\'");
  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
			while (result.next()){
				JsonObject obj = new JsonObject();
				obj.addProperty("id_carte_depandante", result.getInt(1));
				obj.addProperty("id_carte_de_depandance", result.getInt(2));
				obj.addProperty("tbl_carte.nom", result.getString(3));
				obj.addProperty("complete", result.getBoolean(4));
				obj.addProperty("tbl_groupe.id_groupe", result.getInt(5));
				obj.addProperty("tbl_groupe.nom_groupe", result.getString(6));
				obj.addProperty("tbl_projet.id_projet", result.getInt(7));
				obj.addProperty("nom_projet", result.getString(8));
				jsonArr.add(obj);
			}

  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/getAllCarteThatThisCarteIsDependante/{idCarte}")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String getAllCarteThatThisCarteIsDependante(@PathParam("idCarte") String idCarte) throws Exception {

  	mySqlCon.openLocalConnection();
  	ResultSet result = mySqlCon.getQueryResult("select id_carte_depandante,id_carte_de_depandance,tbl_carte.nom,complete,tbl_groupe.id_groupe,tbl_groupe.nom_groupe,tbl_projet.id_projet,nom_projet"+
  " from tbl_depandance inner join tbl_carte"
 +" on id_carte =tbl_depandance.id_carte_de_depandance"
 +" inner join tbl_groupe"
 +" on tbl_carte.id_groupe = tbl_groupe.id_groupe"
 +" inner join tbl_projet"
 +" on tbl_groupe.id_projet = tbl_projet.id_projet"
 +" where id_carte_depandante =\'" +idCarte +"\'");
  	Gson gson = new Gson();
  	JsonArray jsonArr = new JsonArray();

  	if (result.isBeforeFirst()){
			while (result.next()){
				JsonObject obj = new JsonObject();
				obj.addProperty("id_carte_depandante", result.getInt(1));
				obj.addProperty("id_carte_de_depandance", result.getInt(2));
				obj.addProperty("tbl_carte.nom", result.getString(3));
				obj.addProperty("complete", result.getBoolean(4));
				obj.addProperty("tbl_groupe.id_groupe", result.getInt(5));
				obj.addProperty("tbl_groupe.nom_groupe", result.getString(6));
				obj.addProperty("tbl_projet.id_projet", result.getInt(7));
				obj.addProperty("nom_projet", result.getString(8));
				jsonArr.add(obj);
			}

  	}

  	mySqlCon.closeConnection();
  	return gson.toJson(jsonArr);
  }

  @Path("/removeDependance/{carteDependante}/{carteDeDependance}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void saveCarteCompletion(@PathParam("carteDependante") int carteDependante,@PathParam("carteDeDependance") String carteDeDependance) throws Exception {
  	mySqlCon.openLocalConnection();
    mySqlCon.executeNonQuery("DELETE FROM tbl_depandance where id_carte_depandante = \'" + carteDependante + "\' AND id_carte_de_depandance = \'" + carteDeDependance + "\'");
  	mySqlCon.closeConnection();
  }

}
