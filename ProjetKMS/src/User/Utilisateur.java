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

	public Utilisateur(int id,String name){
		this.id = id;
		this.nom = name;
		this.projets = new ArrayList<Project>();
	}

	public ArrayList<Project> getProjets(){	return this.projets;}

	public void setProjets(ArrayList<Project> lesProjets){this.projets = lesProjets;}

	public int getId() { return this.id;}

	public String getNom(){return this.nom;}

	public void addProjet(Project projet){
		ArrayList<Project> newProjectList = new ArrayList<Project>();
		newProjectList.add(projet);
		newProjectList.addAll(this.projets);
		this.projets.clear();
		this.projets.addAll(newProjectList);
	}

	public void addAll(ArrayList<Project> lesProjets){
		projets.addAll(lesProjets);
	}

	public void addAll(ObservableList<Project> lesProjets) {
		projets.addAll(lesProjets);
	}


}
