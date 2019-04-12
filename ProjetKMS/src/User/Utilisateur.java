package User;

import java.util.ArrayList;
import Entity.Projet.Project;
import javafx.collections.ObservableList;

public class Utilisateur {
	private int id;
	private ArrayList<Project> projets;
	private String name;

	public Utilisateur(){
		this.id = 0;
		this.projets = new ArrayList<Project>();
		this.name = "";
	}

	public Utilisateur(int id,String name){
		this.id = id;
		this.projets = new ArrayList<Project>();
		this.name = name;
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
