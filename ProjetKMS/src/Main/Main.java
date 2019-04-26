package Main;

import java.io.IOException;
import java.util.ArrayList;

import Scene3D.MainView3DController;
import API.ApiConnector;
import Entity.Projet.Project;
import User.Utilisateur;

public class Main {
	public static Utilisateur userContext;

	public static void main(String[] args) throws IOException {
		FXMLLoder.launch(FXMLLoder.class);
	}

	public static void setUser(Utilisateur user){
		userContext = user;
	}



}
