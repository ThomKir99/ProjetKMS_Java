package Main;

import java.io.IOException;
import java.util.ArrayList;

import Scene3D.MainView3DController;
import API.ApiConnector;
import Entity.Projet.Project;
import User.Utilisateur;

public class Main {
	public static ApiConnector apiConnector;
	public static Utilisateur userContext;

	public static void main(String[] args) throws IOException {
		fillUser();
		FXMLLoder.launch(FXMLLoder.class);

	}

	public static void fillUser() throws IOException{
		apiConnector = new ApiConnector();
		userContext = apiConnector.getUser("Antoine","123456");
		ArrayList<Project> projectList = apiConnector.projectList(userContext.getId());

		if (projectList != null){
			userContext.addAll(projectList);
		}
	}



}
