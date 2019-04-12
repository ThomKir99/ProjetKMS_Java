package User;

import java.util.ArrayList;
import API.ApiConnector;
import Entity.Projet.Project;
import javafx.collections.ObservableList;

public class Utilisateur {
	private int id;
	private String nom;
	private ArrayList<Project> projets;
	private String name;

	public Utilisateur(){
		this.id = 0;
		this.nom = "Anonymous";
		this.projets = new ArrayList<Project>();
		this.name = "";
	}

	public Utilisateur(int id,String name){
		this.id = id;
		this.nom = "Anonymous";
		this.projets = new ArrayList<Project>();
	}

	public Utilisateur(int id,String nom){
		this.id = id;
		this.nom = nom;
		this.projets = new ArrayList<Project>();
		this.name = name;
	}

	public ArrayList<Project> getProjets(){	return this.projets;}

	public void setProjets(ArrayList<Project> lesProjets){this.projets = lesProjets;}

	public int getId() { return this.id;}

	public void addProjet(Project projet){
		this.projets.add(projet);
	}

	public void addAll(ArrayList<Project> lesProjets){
		projets.addAll(lesProjets);
	}

	public void addAll(ObservableList<Project> lesProjets) {
		projets.addAll(lesProjets);
	}


}
