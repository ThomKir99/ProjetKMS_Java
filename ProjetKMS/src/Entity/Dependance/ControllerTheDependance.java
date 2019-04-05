package Entity.Dependance;

import Entity.Group.Group;
import Entity.Projet.Project;
import Entity.ProjetMenu.ControllerMenuProjetCell;
import javafx.collections.ObservableList;

public class ControllerTheDependance {

	public ObservableList<Group> groupObservableList;
	public Project leProjet;
	public ControllerMenuProjetCell menuProjetCellController;

	public ControllerTheDependance(){
		leProjet = new Project();

	}
	
	public void setProject(Project unProjet){
		this.leProjet = unProjet;
		//txt_projectName.setText(leProjet.getName());
		//refreshGroupList();
	}
}
