package User;

import java.util.ArrayList;
import API.ApiConnector;
import Entity.Projet.Project;
import javafx.collections.ObservableList;

public class Utilisateur {
	private int id;
	private String nom;
	private ArrayList<Project> projets;

	public Utilisateur(){
		this.id = 0;
		this.nom = "Anonymous";
		this.projets = new ArrayList<Project>();
	}

	public Utilisateur(int id){
		this.id = id;
		this.nom = "Anonymous";
		this.projets = new ArrayList<Project>();
	}

	public Utilisateur(int id,String nom){
		this.id = id;
		this.nom = nom;
		this.projets = new ArrayList<Project>();
	}

	public ArrayList<Project> getProjets(){	return this.projets;}

	public void setProjets(ArrayList<Project> lesProjets){this.projets = lesProjets;}


	public void addProjet(Project projet){
		this.projets.add(projet);
	}

	public void addAll(ObservableList<Project> lesProjets) {
		projets.addAll(lesProjets);
	}

	private void getAllUserProject(){

	}

}
