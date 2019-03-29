package User;

import java.util.ArrayList;

import Entity.Projet.Project;
import javafx.collections.ObservableList;

public class Utilisateur {
	private int id;
	private ArrayList<Project> projets;

	public Utilisateur(){
		this.id = 0;
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
}
